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
    
    @Query("SELECT DISTINCT(l) FROM LeaveApplied l where approvalStatus='APPROVED' AND MONTH(l.leaveStartDate)=:month AND YEAR(l.leaveStartDate)=:year")
    List<LeaveApplied> findByMonth(@Param("month") Integer month, @Param("year") Integer year);
}
