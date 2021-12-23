package sg.edu.iss.LAPS.services;

import java.util.List;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.utility.ClaimStatus;
import sg.edu.iss.LAPS.utility.LeaveStatus;

public interface ManagerService {
	List<User> getAllSubordinates(String mgrEmail);
	User getThisSubordinate(String mgrEmail, Long subid);
	List<LeaveApplied> getAllSubordinatesLeaves(String mgrEmail);
	List<LeaveApplied> getSubordinateLeavesByLeaveStatus(String mgrEmail, LeaveStatus status);
	List<LeaveApplied> getSubordinateLeavesByLeaveType(String mgrEmail, Integer leavetypeid);
	List<LeaveApplied> getThisSubordinateLeaves(String mgrEmail, Long subid);
	List<LeaveApplied> getSubordinateLeavesByPending(String mgrEmail);
	List<User> getAllSubordinatesByKeyword(String mgrEmail, String keyword);
	//Claim Compensation Retrieval
	List<ClaimCompensation> getAllSubordinatesCompensations(String mgrEmail);
	List<ClaimCompensation> getSubordinateCompensationsByClaimStatus(String mgrEmail, ClaimStatus status);
	Float increaseThisSubordinateLeaveEntitled(String mgrEmail, Long subid, Float increaseBy);

	
	
}
