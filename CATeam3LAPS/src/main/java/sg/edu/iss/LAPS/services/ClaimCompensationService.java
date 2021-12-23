package sg.edu.iss.LAPS.services;

import java.util.ArrayList;
import java.util.List;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;


public interface ClaimCompensationService {
	
	public void createCompensationClaim(ClaimCompensation claimCompensation); 
	
	//ArrayList<ClaimCompensation> findClaimsListByUserId(Long userID);
	
	public ArrayList<ClaimCompensation>findByUserId(Long userId);

	public ClaimCompensation findByCompensationClaimId(Long id);

}