package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.utility.LeaveStatus;

import java.util.List;
import java.util.Optional;

public interface LeaveAppliedService {
    Optional<LeaveApplied> findById(int id);

    List<LeaveApplied> findByUserId(Long userID);

    List<LeaveApplied> findByUserId(Long userID, LeaveStatus status);

    void update(LeaveApplied leaveApplied);

    void delete(int id);
}
