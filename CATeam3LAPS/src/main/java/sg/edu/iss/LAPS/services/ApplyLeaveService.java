package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveApplied;

import java.util.Date;

public interface ApplyLeaveService {
	
	public void createLeaveApplication(LeaveApplied leaveApplied); // To add a new leave application for user.
	
	float countNumberOfDays(Date startDate, Date endDate);
	
}
