package sg.edu.iss.LAPS.validators;

import java.time.temporal.ChronoUnit;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.utility.DateTools;

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
		boolean manager = leave.getUser().getRoles().contains("Manager");
		
		if(leave.getLeaveType().getDescription() == "Annual Leave") {
			if(manager) {
				if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
					errors.rejectValue("appliedDate", "appliedDate");
				}else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("leaveStartDay", "leaveStartDay");
				}else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 18) {
					errors.rejectValue("leavePeriodForManager", "leavePeriodForManager");
				}else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("isWeekdays", "isWeekdays");
				}else if(leave.getIsOverseas()) {
					if(leave.getOverseasTrip().getCountry() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}else if(leave.getOverseasTrip().getCity() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}else if(leave.getOverseasTrip().getPhone() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}
				}
			}else {
				if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
					errors.rejectValue("appliedDate", "appliedDate");
				}else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("leaveStartDay", "leaveStartDay");
				}else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 14) {
					errors.rejectValue("leavePeriodForEmployee", "leavePeriodForEmployee");
				}else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
					errors.rejectValue("isWeekdays", "isWeekdays");
				}else if(leave.getIsOverseas()) {
					if(leave.getOverseasTrip().getCountry() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}else if(leave.getOverseasTrip().getCity() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}else if(leave.getOverseasTrip().getPhone() == null) {
						errors.rejectValue("overseasTripContact", "overseasTripContact");
					}
				}
			}
		}else if(leave.getLeaveType().getDescription() == "Medical Leave") {
			if(DateTools.dateToCalendar(leave.getAppliedDate()).after(leave.getLeaveStartDate())) {
				errors.rejectValue("appliedDate", "appliedDate");
			}else if(!(DateTools.startDateBeforeEndDate(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
				errors.rejectValue("leaveStartDay", "leaveStartDay");
			}else if((ChronoUnit.DAYS.between(DateTools.dateToCalendar(leave.getLeaveStartDate()).toInstant(), DateTools.dateToCalendar(leave.getLeaveEndDate()).toInstant()) + 1) > 60) {
				errors.rejectValue("leavePeriodForMedicalLeave", "leavePeriodForMedicalLeave");
			}else if(!(DateTools.isWeekdays(DateTools.dateToCalendar(leave.getLeaveStartDate()), DateTools.dateToCalendar(leave.getLeaveEndDate())))) {
				errors.rejectValue("isWeekdays", "isWeekdays");
			}else if(leave.getIsOverseas()) {
				if(leave.getOverseasTrip().getCountry() == null) {
					errors.rejectValue("overseasTripContact", "overseasTripContact");
				}else if(leave.getOverseasTrip().getCity() == null) {
					errors.rejectValue("overseasTripContact", "overseasTripContact");
				}else if(leave.getOverseasTrip().getPhone() == null) {
					errors.rejectValue("overseasTripContact", "overseasTripContact");
				}
			}
		}
	}

}

