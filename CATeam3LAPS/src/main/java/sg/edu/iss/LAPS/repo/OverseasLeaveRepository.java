package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.iss.LAPS.model.OverseasLeaveDetails;

@Repository
public interface OverseasLeaveRepository extends JpaRepository<OverseasLeaveDetails, Integer> {
}
