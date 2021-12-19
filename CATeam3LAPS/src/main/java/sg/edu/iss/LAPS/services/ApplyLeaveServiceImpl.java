package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.ApplyLeaveRepository;

@Service
public class ApplyLeaveServiceImpl implements ApplyLeaveService {
	
	@Autowired
	ApplyLeaveRepository alrepo;

	@Override
	public void createLeaveApplication(LeaveApplied leaveApplied) {
		alrepo.save(leaveApplied);	
	}

}
