package sg.edu.iss.LAPS.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.LAPS.model.ClaimCompensation;

@Repository
public interface ClaimCompensationRepository extends JpaRepository<ClaimCompensation, Long>{

	
	//@Query("Select claim FROM ClaimCompensation claim JOIN claim.user u WHERE u.id=:userId")
	//public ArrayList<ClaimCompensation>findClaimsListByUserId(Long userId);
	
	
	public ArrayList<ClaimCompensation>findByUserId(Long userId);
	
	@Query("Select cc FROM ClaimCompensation cc WHERE cc.compensationClaimId = :id")
	public ClaimCompensation findByCompensationClaimId(@Param("id") Long id);
}
