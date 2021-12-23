package sg.edu.iss.LAPS.validators;

import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.utility.Constants;
import sg.edu.iss.LAPS.utility.DateTools;

@Component
public class LeaveAppliedValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return LeaveApplied.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//TODO: compare start day and end day
		// Medical leave <= 60
		// TODO: check leave period
		// for manager, annual leave = 18 days
		// for employee, annual leave = 14 days
		// from and to dates must be working days
		LeaveApplied leave = (LeaveApplied) target;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		//boolean manager = leave.getUser().getRoles().contains("Manager");
		boolean manager = session.getAttribute("role").toString().equals(Constants.MANAGER_ROLE_NAME);
		
		if(leave.getLeaveType().getDescription().toString().equals("Annual")) {
			if(manager) {
				if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
					errors.rejectValue("appliedDate", "appliedDate");
				}
				else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("leaveStartDay", "leaveStartDay");
				}
				else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 18) {
					errors.rejectValue("leavePeriodForManager", "leavePeriodForManager");
				}
//				else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
//					errors.rejectValue("isWeekdays", "isWeekdays");}
				else if(leave.getIsOverseas()) {
					if(leave.getOverseasTrip().getCountry().isBlank()) {
						errors.rejectValue("overseasTrip.country","country.error" ,"Country should not be empty");}
					if(leave.getOverseasTrip().getCity().isBlank()) {
						errors.rejectValue("overseasTrip.city","city.error" ,"City should not be empty");}
					if(leave.getOverseasTrip().getPhone() == null) {
						errors.rejectValue("overseasTrip.phone","phone.error" ,"Phone should not be empty");
						}
					}
				}
			else { //not manager
				if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
					errors.rejectValue("appliedDate", "appliedDate");
				}else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("leaveStartDay", "leaveStartDay");
				}else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 14) {
					errors.rejectValue("leavePeriodForEmployee", "leavePeriodForEmployee");}
				//}else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					//errors.rejectValue("isWeekdays", "isWeekdays");
				else if(leave.getIsOverseas()) {
					if(leave.getOverseasTrip().getCountry().isBlank()) {
						errors.rejectValue("overseasTrip.country","country.error" ,"Country should not be empty");
					if(leave.getOverseasTrip().getCity().isBlank()) {
						errors.rejectValue("overseasTrip.city","city.error" ,"City should not be empty");}
					if(leave.getOverseasTrip().getPhone() == null) {
						errors.rejectValue("overseasTrip.phone","phone.error" ,"Phone should not be empty");
					}
				}
			}}}
			else if(leave.getLeaveType().getDescription() == "Medical Leave") {
			if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
				errors.rejectValue("appliedDate", "appliedDate");
			}else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
				errors.rejectValue("leaveStartDay", "leaveStartDay");
			}else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 60) {
				errors.rejectValue("leavePeriodForMedicalLeave", "leavePeriodForMedicalLeave");}
			//}else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
			//	errors.rejectValue("isWeekdays", "isWeekdays");}
			else if(leave.getIsOverseas()) {
				if(leave.getOverseasTrip().getCountry().isBlank()) {
					errors.rejectValue("overseasTrip.country","country.error" ,"Country should not be empty");}
				if(leave.getOverseasTrip().getCity().isBlank()) {
					errors.rejectValue("overseasTrip.city","city.error" ,"City should not be empty");}
				if(leave.getOverseasTrip().getPhone() == null) {
					errors.rejectValue("overseasTrip.phone","phone.error" ,"Phone should not be empty");}
			}
		}
	}
}



