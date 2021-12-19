package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.LAPS.model.LeaveApplied;

@Repository
public interface ApplyLeaveRepository extends JpaRepository<LeaveApplied, Integer> {
	
	
}
