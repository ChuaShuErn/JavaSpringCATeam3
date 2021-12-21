package sg.edu.iss.LAPS.controller;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.OverseasLeaveDetails;
import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.*;
import sg.edu.iss.LAPS.utility.DateTools;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Controller
public class ApplyLeaveController {
    @Autowired
    LeaveAppliedService leaveAppliedService;

    @Autowired
    ApplyLeaveService applyLeaveService;

    @Autowired
    UserService userService;

    @Autowired
    LeaveTypeService leaveTypeService;

    @Autowired
    PublicHolidayService publicHolidayService;


    @RequestMapping(value="/applyleave")
	public String applyForm(Model model) {
		model.addAttribute("leaveapplication", new LeaveApplied());
		List<LeaveType> leaveTypeList = leaveTypeService.getAllLeaveType();
		model.addAttribute("leaveTypeList", leaveTypeList);
		model.addAttribute("overseasTrip", new OverseasLeaveDetails());
		return "applyLeave";
	}


    @RequestMapping(value = "/applyleave/submit")
    public String createLeaveApplication(@ModelAttribute("leaveapplication") @Valid LeaveApplied application, BindingResult bindingResult, HttpSession session, Model model) {

        User currUser = userService.findUserById((Long) session.getAttribute("id"));
        LeaveType leaveType = leaveTypeService.findLeaveTypeByleaveTypeId(application.getLeaveType()
                .getLeaveTypeId());
        application.setUser(currUser);
        application.setLeaveType(leaveType);
        Calendar appliedStartDate = DateTools.dateToCalendar(application.getLeaveStartDate());
        Calendar appliedEndDate = DateTools.dateToCalendar(application.getLeaveEndDate());
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
                    .after(application.getLeaveStartDate()) && holiday.getHolidayEndDate()
                    .before(application.getLeaveEndDate())) {
                holidaysAffectLeave1.add(holiday);
            }
        }

        float WeekdaysPublicHoliday2 = 0;
        for (PublicHoliday holiday : publicHolidaysList) {
            if (holiday.getHolidayStartDate()
                    .before(application.getLeaveStartDate()) && holiday.getHolidayEndDate()
                    .after(application.getLeaveStartDate())) {
                holidaysAffectLeave2.add(holiday);
            }
        }

        float WeekdaysPublicHoliday3 = 0;
        for (PublicHoliday holiday : publicHolidaysList) {
            if (holiday.getHolidayStartDate()
                    .before(application.getLeaveEndDate()) && holiday.getHolidayEndDate()
                    .after(application.getLeaveEndDate())) {
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
        daysPeriod = daysPeriod - (WeekdaysPublicHoliday1 + WeekdaysPublicHoliday2 + WeekdaysPublicHoliday3);
        application.setNoOfDays(daysPeriod);
        application.setApprovalStatus(LeaveStatus.APPLIED);
        applyLeaveService.createLeaveApplication(application);

        return "redirect:/"; // lalaaa

    }


}
