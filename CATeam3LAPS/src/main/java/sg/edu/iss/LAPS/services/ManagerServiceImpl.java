package sg.edu.iss.LAPS.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;
import sg.edu.iss.LAPS.repo.UserRepository;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	UserRepository urepo;
	
	@Autowired
	LeaveAppliedRepository larepo;
	
	@Override
	public List<User> getAllSubordinates(String mgrEmail) {
		return urepo.findSubordinates(mgrEmail);
	}
	
	@Override
	public User getThisSubordinate(String mgrEmail, Long subid) {
		List<User> sublist = this.getAllSubordinates(mgrEmail);
		User thisSubordinate = sublist.stream().filter(x -> x.getId() == subid).findAny().orElse(null);
		return thisSubordinate;
	}


	@Override
	public List<LeaveApplied> getAllSubordinatesLeaves(String mgrEmail) {
		List<User> sublist = this.getAllSubordinates(mgrEmail);
		ArrayList<LeaveApplied> subleaves= new ArrayList<LeaveApplied>();
		for (User u : sublist)
			subleaves.addAll(larepo.findByUserId(u.getId()));
		subleaves.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate));
		return subleaves;
	}
	
	@Override
	public List<LeaveApplied> getThisSubordinateLeaves(String mgrEmail, Long subid) {
		User thisSubordinate = this.getThisSubordinate(mgrEmail, subid); //done to check if the subordinate is actually a valid staff & a subordinate
		ArrayList<LeaveApplied> thisSubLeaves = (ArrayList) larepo.findByUserId(thisSubordinate.getId());
		thisSubLeaves.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate));
		return thisSubLeaves;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeavesByLeaveStatus(String mgrEmail, String status) {
		ArrayList<LeaveApplied> subleaves = (ArrayList) this.getAllSubordinatesLeaves(mgrEmail);
		subleaves.stream().filter(x -> x.getApprovalStatus().toString().equalsIgnoreCase(status)).
			sorted(Comparator.comparing(LeaveApplied::getAppliedDate));
		return subleaves;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeavesByLeaveType(String mgrEmail, String leavetype) {
		ArrayList<LeaveApplied> subleaves = (ArrayList) this.getAllSubordinatesLeaves(mgrEmail);
		subleaves.stream().filter(x -> x.getLeaveType().toString().equalsIgnoreCase(leavetype)).
			sorted(Comparator.comparing(LeaveApplied::getAppliedDate));
		return subleaves;
	}

}
