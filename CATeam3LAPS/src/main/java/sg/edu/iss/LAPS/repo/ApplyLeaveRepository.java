package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.LAPS.model.LeaveApplied;

public interface ApplyLeaveRepository extends JpaRepository<LeaveApplied, Integer> {
	
	
}