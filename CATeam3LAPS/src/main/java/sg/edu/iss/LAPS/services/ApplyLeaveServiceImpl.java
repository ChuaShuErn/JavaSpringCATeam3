package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.ApplyLeaveRepository;

public class ApplyLeaveServiceImpl implements ApplyLeaveService {
	
	@Autowired
	ApplyLeaveRepository alrepo;

	@Override
	public void createLeaveApplication(LeaveApplied leaveApplied) {
		alrepo.save(leaveApplied);	
	}

}
