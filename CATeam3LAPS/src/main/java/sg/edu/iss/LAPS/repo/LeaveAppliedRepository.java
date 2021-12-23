package sg.edu.iss.LAPS.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.utility.LeaveStatus;

public interface LeaveAppliedRepository extends JpaRepository<LeaveApplied, Integer> {
    List<LeaveApplied> findByUserId(Long userId);

    Page<LeaveApplied> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT DISTINCT(l) FROM LeaveApplied l where approvalStatus='APPROVED' AND MONTH(l.leaveStartDate)=:month AND YEAR(l.leaveStartDate)=:year")
    List<LeaveApplied> findByMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT sum(l.noOfDays) FROM LeaveApplied l where l.user.id = :userId AND l.leaveType.leaveTypeId = :leaveId AND l.approvalStatus = :status")
    public Integer countLeavesByStatus(@Param("userId") Long userId, @Param("leaveId") Integer leaveId, @Param("status") LeaveStatus status);
}
