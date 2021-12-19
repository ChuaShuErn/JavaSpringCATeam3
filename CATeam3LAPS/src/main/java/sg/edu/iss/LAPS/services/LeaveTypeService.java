package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.User;

import java.util.List;

public interface LeaveTypeService {
    List<LeaveType> getAllLeaveType();
    void saveLeaveType(LeaveType leaveType);
    LeaveType getLeaveTypeById(Integer id);
    void deleteLeaveTypeById(Integer id);
    
    LeaveType findLeaveTypeByleaveTypeId(Integer leaveTypeId);
}
