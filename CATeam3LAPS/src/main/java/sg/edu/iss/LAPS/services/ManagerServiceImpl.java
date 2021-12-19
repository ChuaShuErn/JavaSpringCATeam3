package sg.edu.iss.LAPS.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	UserRepository urepo;
	
	@Override
	public List<User> getSubordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeaves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeavesByLeaveType() {
		// TODO Auto-generated method stub
		return null;
	}

}
