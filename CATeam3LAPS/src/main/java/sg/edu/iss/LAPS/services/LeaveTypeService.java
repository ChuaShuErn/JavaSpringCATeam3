package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveType;

import java.util.List;

import org.springframework.data.domain.Page;

public interface LeaveTypeService {
    List<LeaveType> getAllLeaveType();
    void saveLeaveType(LeaveType leaveType);
    LeaveType getLeaveTypeById(Integer id);
    void deleteLeaveTypeById(Integer id);
    
    LeaveType findLeaveTypeByleaveTypeId(Integer leaveTypeId);
    Page<LeaveType> findPaginated(int pageNo,int pageSize);
	LeaveType findLeaveTypeByDescription(String description);
}
