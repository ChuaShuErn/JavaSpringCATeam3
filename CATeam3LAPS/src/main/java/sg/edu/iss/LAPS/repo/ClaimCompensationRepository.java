package sg.edu.iss.LAPS.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;

@Repository
public interface ClaimCompensationRepository extends JpaRepository<ClaimCompensation, Long>{

	
	//@Query("Select claim FROM ClaimCompensation claim JOIN claim.user u WHERE u.id=:userId")
	//public ArrayList<ClaimCompensation>findClaimsListByUserId(Long userId);
	
	
	public ArrayList<ClaimCompensation>findByUserId(Long userId);
}
