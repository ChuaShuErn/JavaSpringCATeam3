package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveAppliedServiceImpl implements LeaveAppliedService {
    @Autowired
    LeaveAppliedRepository repository;

    @Override
    public Optional<LeaveApplied> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<LeaveApplied> findByUserId(Long userID) {
        return repository.findByUserId(userID);
    }

    @Override
    public List<LeaveApplied> findByUserId(Long userID, ApprovalStatus status) {
        List<LeaveApplied> list = this.findByUserId(userID);
        return list.stream()
                .filter(item -> item.getApprovalStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public void update(LeaveApplied leaveApplied) {
        Optional<LeaveApplied> optLeaveApplied = this.findById(leaveApplied.getLeaveAppliedId());
        LeaveApplied savedLeaveApplied = leaveApplied;
        if (optLeaveApplied.isPresent()) {
            // TODO: override leave type
            savedLeaveApplied = optLeaveApplied.get();
            savedLeaveApplied.setLeaveStartDate(leaveApplied.getLeaveStartDate());
            savedLeaveApplied.setLeaveEndDate(leaveApplied.getLeaveEndDate());
            savedLeaveApplied.setIsOverseas(leaveApplied.getIsOverseas());
            savedLeaveApplied.setLeaveReason(leaveApplied.getLeaveReason());
        }
        repository.save(savedLeaveApplied);
    }
}
