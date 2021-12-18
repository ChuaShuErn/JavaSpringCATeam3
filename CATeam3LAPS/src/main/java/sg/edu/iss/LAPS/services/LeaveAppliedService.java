package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.model.LeaveApplied;

import java.util.List;

public interface LeaveAppliedService {
    List<LeaveApplied> findByUserId(Long userID);

    List<LeaveApplied> findByUserId(Long userID, ApprovalStatus status);
}
