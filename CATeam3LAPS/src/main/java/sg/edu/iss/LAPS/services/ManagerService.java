package sg.edu.iss.LAPS.services;

import java.util.List;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;

public interface ManagerService {
	List<User> getSubordinates();
	List<LeaveApplied> getSubordinateLeaves();
	List<LeaveApplied> getSubordinateLeavesByLeaveType();
}
