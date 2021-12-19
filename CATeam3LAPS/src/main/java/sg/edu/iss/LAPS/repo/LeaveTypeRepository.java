package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.LAPS.model.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType,Integer> {
	
}
