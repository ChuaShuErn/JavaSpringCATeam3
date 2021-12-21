package sg.edu.iss.LAPS.services;

import org.springframework.data.domain.Page;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.utility.LeaveStatus;

import java.util.List;
import java.util.Optional;

public interface LeaveAppliedService {
    Optional<LeaveApplied> findById(int id);

    List<LeaveApplied> findByUserId(Long userID);

    List<LeaveApplied> findByUserId(Long userID, LeaveStatus status);

    Page<LeaveApplied> findByUserId(Long userID, int pageNo, int pageSize);

    void update(LeaveApplied leaveApplied);

    void delete(int id);
}
