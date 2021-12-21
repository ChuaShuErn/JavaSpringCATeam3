package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.repo.ApplyLeaveRepository;
import sg.edu.iss.LAPS.utility.DateTools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplyLeaveServiceImpl implements ApplyLeaveService {

    @Autowired
    ApplyLeaveRepository alrepo;

    @Autowired
    PublicHolidayService publicHolidayService;

    @Override
    public void createLeaveApplication(LeaveApplied leaveApplied) {
        alrepo.save(leaveApplied);
    }

    @Override
    public float countNumberOfDays(Date startDate, Date endDate) {
        Calendar appliedStartDate = DateTools.dateToCalendar(startDate);
        Calendar appliedEndDate = DateTools.dateToCalendar(endDate);
        float daysPeriod = ChronoUnit.DAYS.between(appliedStartDate.toInstant(), appliedEndDate.toInstant()) + 1;
// Get a list of public holidays
        List<PublicHoliday> publicHolidaysList = publicHolidayService.findAll();
        List<PublicHoliday> holidaysAffectLeave1 = new ArrayList<>();
        List<PublicHoliday> holidaysAffectLeave2 = new ArrayList<>();
        List<PublicHoliday> holidaysAffectLeave3 = new ArrayList<>();
        // use for each to traverse the public holiday collection

        // TODO:clean code
        float WeekdaysPublicHoliday1 = 0;
        for (PublicHoliday holiday : publicHolidaysList) {
            Calendar calPublicHolidayStart = DateTools.dateToCalendar(holiday.getHolidayStartDate());
            Calendar calPublicHolidayEnd = DateTools.dateToCalendar(holiday.getHolidayEndDate());

            // find the public holidays from the start day to the 14th days
            if (holiday.getHolidayStartDate()
                    .after(startDate) && holiday.getHolidayEndDate()
                    .before(endDate)) {
                holidaysAffectLeave1.add(holiday);
            }
        }

        float WeekdaysPublicHoliday2 = 0;
        for (PublicHoliday holiday : publicHolidaysList) {
            if (holiday.getHolidayStartDate()
                    .before(startDate) && holiday.getHolidayEndDate()
                    .after(startDate)) {
                holidaysAffectLeave2.add(holiday);
            }
        }

        float WeekdaysPublicHoliday3 = 0;
        for (PublicHoliday holiday : publicHolidaysList) {
            if (holiday.getHolidayStartDate()
                    .before(endDate) && holiday.getHolidayEndDate()
                    .after(endDate)) {
                holidaysAffectLeave3.add(holiday);
            }
        }

        // compute situation 1:
        for (PublicHoliday day : holidaysAffectLeave1) {
            WeekdaysPublicHoliday1 = DateTools.countWeekdaysPublicHoliday(DateTools.dateToCalendar(day.getHolidayStartDate()), DateTools.dateToCalendar(day.getHolidayEndDate()));
        }

        // compute situation 2:
        for (PublicHoliday day : holidaysAffectLeave2) {
            WeekdaysPublicHoliday2 = ChronoUnit.DAYS.between(appliedStartDate.toInstant(), day.getHolidayEndDate()
                    .toInstant()) + 1;
        }


        // compute situation 3:
        for (PublicHoliday day : holidaysAffectLeave3) {
            WeekdaysPublicHoliday3 = ChronoUnit.DAYS.between(day.getHolidayStartDate()
                    .toInstant(), appliedEndDate.toInstant()) + 1;
        }


        if (daysPeriod <= 14) {
            daysPeriod = DateTools.removeWeekends(appliedStartDate, appliedEndDate);
        }

        // minus the public holiday in weekdays.
        return daysPeriod - (WeekdaysPublicHoliday1 + WeekdaysPublicHoliday2 + WeekdaysPublicHoliday3);
    }

    @Override
    public float countNumberOfDaysV2(Date startDate, Date endDate) {
        Set<String> weekends = toDateString(DateTools.getWeekends(startDate, endDate));

        List<Date> phList = new ArrayList<>();
        for (PublicHoliday publicHoliday : publicHolidayService.findAll()) {
            List<Date> dateSet = DateTools.getDateList(publicHoliday.getHolidayStartDate(), publicHoliday.getHolidayEndDate());
            phList.addAll(dateSet);
        }
        Set<String> phSet = toDateString(phList);
        Set<String> leaveSet = toDateString(DateTools.getDateList(startDate, endDate));

        leaveSet.removeAll(weekends);
        leaveSet.removeAll(phSet);

        return leaveSet.size();
    }

    private Set<String> toDateString(List<Date> dateSet) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateSet.stream()
                .map(dateFormat::format)
                .collect(Collectors.toSet());
    }
}
