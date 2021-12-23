package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.utility.LeaveStatus;

public interface LeaveEntitledService {
    public LeaveEntitled findLeaveEntitledByUserAndLeaveId(Long userId, Integer leaveId);
    public void saveLeaveEntitled(LeaveEntitled leaveEntitled);
    public Float totalAvailableLeave(Long userId, Integer leaveId);
}
