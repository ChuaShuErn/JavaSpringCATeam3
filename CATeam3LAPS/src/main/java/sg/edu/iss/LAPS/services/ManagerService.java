package sg.edu.iss.LAPS.services;

import java.util.List;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;

public interface ManagerService {
	List<User> getAllSubordinates(String mgrEmail);
	User getThisSubordinate(String mgrEmail, Long subid);
	List<LeaveApplied> getAllSubordinatesLeaves(String mgrEmail);
	List<LeaveApplied> getSubordinateLeavesByLeaveType(String mgrEmail, String status);
	List<LeaveApplied> getSubordinateLeavesByLeaveStatus(String mgrEmail, String leavetype);
	List<LeaveApplied> getThisSubordinateLeaves(String mgrEmail, Long subid);
	List<LeaveApplied> getSubordinateLeavesByPending(String mgrEmail);
}
