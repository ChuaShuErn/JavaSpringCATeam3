package sg.edu.iss.LAPS.services;

import java.util.Date;

import sg.edu.iss.LAPS.model.LeaveApplied;

public interface ApplyLeaveService {
	
	public LeaveApplied createLeaveApplication(LeaveApplied leaveApplied); // To add a new leave application for user.

	@Deprecated
	float countNumberOfDays(Date startDate, Date endDate);

	float countNumberOfDaysV2(Date startDate, Date endDate);
	
}
