package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.model.LeaveApplied;

import java.util.List;
import java.util.Optional;

public interface LeaveAppliedService {
    Optional<LeaveApplied> findById(int id);

    List<LeaveApplied> findByUserId(Long userID);

    List<LeaveApplied> findByUserId(Long userID, ApprovalStatus status);

    void update(LeaveApplied leaveApplied);

    void delete(int id);
}
