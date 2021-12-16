package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.LAPS.model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Integer> {
}
