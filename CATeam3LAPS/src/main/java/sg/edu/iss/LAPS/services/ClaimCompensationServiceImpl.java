package sg.edu.iss.LAPS.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.ApplyLeaveRepository;
import sg.edu.iss.LAPS.repo.ClaimCompensationRepository;

@Service
public class ClaimCompensationServiceImpl implements ClaimCompensationService{
	
	
	@Autowired
	ClaimCompensationRepository ccrepo;

	@Override
	public void createCompensationClaim(ClaimCompensation claimcompensation) {
		ccrepo.save(claimcompensation);	
	}
	
	//@Override
	//public ArrayList<ClaimCompensation> findClaimsListByUserId(Long userID)
	//{
		//return ccrepo.findClaimsListByUserId(userID);
	//}
	@Override
	public ArrayList<ClaimCompensation>findByUserId(Long userId)
	{
		return ccrepo.findByUserId(userId);
	}

	@Override
	public ClaimCompensation findByCompensationClaimId(Long id) {
		
		return ccrepo.findByCompensationClaimId(id);
	}

}

