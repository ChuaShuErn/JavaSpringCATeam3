package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveEntitled;

public interface LeaveEntitledService {
    public LeaveEntitled findLeaveEntitledByUserAndLeaveId(Long userId, Integer leaveId);
    public void saveLeaveEntitled(LeaveEntitled leaveEntitled);
}
