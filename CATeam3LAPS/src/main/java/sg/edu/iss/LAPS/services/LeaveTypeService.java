package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.User;

import java.util.List;

public interface LeaveTypeService {
    List<LeaveType> getAllLeaveType();
    void saveLeaveType(LeaveType leaveType);
    LeaveType getLeaveTypeById(long id);
    void deleteLeaveTypeById(long id);
}
