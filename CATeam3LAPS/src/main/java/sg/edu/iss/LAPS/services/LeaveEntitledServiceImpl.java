package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.repo.LeaveEntitledRepository;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Service
@Transactional
public class LeaveEntitledServiceImpl implements  LeaveEntitledService{
    @Autowired
    LeaveEntitledRepository leaveEntitledRepository;

    @Override
    public LeaveEntitled findLeaveEntitledByUserAndLeaveId(Long userId, Integer leaveId) {
        return leaveEntitledRepository.findLeaveEntitledByUserIdAndLeaveId(userId,leaveId);
    }

    @Override
    public void saveLeaveEntitled(LeaveEntitled leaveEntitled){
        leaveEntitledRepository.save(leaveEntitled);
    }

    @Override
    public Float totalAvailableLeave(Long userId, Integer leaveId) {
        return leaveEntitledRepository.totalAvailableLeave(userId,leaveId);
    }

}
