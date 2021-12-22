package sg.edu.iss.LAPS.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.LAPS.model.LeaveApplied;

public interface LeaveAppliedRepository extends JpaRepository<LeaveApplied, Integer> {
    List<LeaveApplied> findByUserId(Long userId);

    Page<LeaveApplied> findByUserId(Long userId, Pageable pageable);
    
    @Query("SELECT le FROM LeaveApplied le where MONTH(le.leaveStartDate)=:month AND approvalStatus='APPROVED'")
    List<LeaveApplied> findLeaveAppliedByMonth(@Param("month") Integer month);
}
