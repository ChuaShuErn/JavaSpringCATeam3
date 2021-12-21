package sg.edu.iss.LAPS.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.LAPS.model.LeaveApplied;

import java.util.List;

public interface LeaveAppliedRepository extends JpaRepository<LeaveApplied, Integer> {
    List<LeaveApplied> findByUserId(Long userId);

    Page<LeaveApplied> findByUserId(Long userId, Pageable pageable);
}
