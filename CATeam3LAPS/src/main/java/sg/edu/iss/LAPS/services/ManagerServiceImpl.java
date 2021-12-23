package sg.edu.iss.LAPS.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.ClaimCompensationRepository;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;
import sg.edu.iss.LAPS.repo.LeaveEntitledRepository;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.utility.ClaimStatus;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	UserRepository urepo;
	
	@Autowired
	LeaveAppliedRepository larepo;
	
	@Autowired
	ClaimCompensationRepository ccrepo;
	
	@Autowired
	LeaveEntitledRepository lerepo;
	
	@Override
	public List<User> getAllSubordinates(String mgrEmail) {
		return urepo.findSubordinates(mgrEmail);
	}
	
	@Override
	public List<LeaveApplied> getSubordinateLeavesByPending(String mgrEmail) {
		List<LeaveApplied> subPending = (ArrayList) this.getSubordinateLeavesByLeaveStatus(
				mgrEmail, LeaveStatus.APPLIED);
		List<LeaveApplied> subUpdated = (ArrayList) this.getSubordinateLeavesByLeaveStatus(
				mgrEmail, LeaveStatus.UPDATED);
		subPending.addAll(subUpdated);
		subPending = subPending.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(
				Collectors.toList());
		return subPending;
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
		subleaves = (ArrayList) subleaves.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(
				Collectors.toList());;
		return subleaves;
	}
	
	@Override
	public List<LeaveApplied> getThisSubordinateLeaves(String mgrEmail, Long subid) {
		User thisSubordinate = this.getThisSubordinate(mgrEmail, subid); //done to check if the subordinate is actually a valid staff & a subordinate
		ArrayList<LeaveApplied> thisSubLeaves = (ArrayList) larepo.findByUserId(thisSubordinate.getId());
		thisSubLeaves = (ArrayList) thisSubLeaves.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(Collectors.toList());
		return thisSubLeaves;
	}
	
	@Override
	public List<LeaveApplied> getThisSubordinateLeavesByHistory(String mgrEmail, Long subid) {
		List<LeaveApplied> thisSubLeaves = this.getThisSubordinateLeaves(mgrEmail, subid);
		List<LeaveApplied> filtered_subleave = new ArrayList<LeaveApplied>();
		for (LeaveApplied leave : thisSubLeaves)
		{
			if(leave.getApprovalStatus() == LeaveStatus.APPROVED || 
					leave.getApprovalStatus() == LeaveStatus.REJECTED ||
					leave.getApprovalStatus() == LeaveStatus.CANCELLED)
				filtered_subleave.add(leave);
		}
		filtered_subleave = (ArrayList) filtered_subleave.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(Collectors.toList());

		return filtered_subleave;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeavesByLeaveStatus(String mgrEmail, LeaveStatus status) {
		ArrayList<LeaveApplied> subleaves = (ArrayList) this.getAllSubordinatesLeaves(mgrEmail);
		String statusStr = status.toString().trim();
		ArrayList<LeaveApplied> filtered_subleave = new ArrayList<LeaveApplied>();
		for (LeaveApplied leave : subleaves)
		{
			if(leave.getApprovalStatus() == status)
				filtered_subleave.add(leave);
		}
		filtered_subleave = (ArrayList) filtered_subleave.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(Collectors.toList());
		return filtered_subleave;
	}

	@Override
	public List<LeaveApplied> getSubordinateLeavesByLeaveType(String mgrEmail, Integer leavetypeid) {
		ArrayList<LeaveApplied> subleaves = (ArrayList) this.getAllSubordinatesLeaves(mgrEmail);
		ArrayList<LeaveApplied> filtered_subleave = new ArrayList<LeaveApplied>();
		for (LeaveApplied leave : subleaves)
		{
			if(leave.getLeaveType().getLeaveTypeId() == leavetypeid)
				filtered_subleave.add(leave);
		}
		filtered_subleave = (ArrayList) filtered_subleave.stream().sorted(Comparator.comparing(LeaveApplied::getAppliedDate)).collect(Collectors.toList());

		//subleaves.stream().filter(x -> x.getLeaveType().getLeaveTypeId() == leavetypeid).
			//sorted(Comparator.comparing(LeaveApplied::getAppliedDate));
		return filtered_subleave;
	}

	
	@Override
	public List<User> getAllSubordinatesByKeyword(String mgrEmail, String keyword) {
		List<User> sublist = this.getAllSubordinates(mgrEmail);
		ArrayList<User> filtered_sublist = new ArrayList<User>();
		for (User user : sublist)
		{
			if(user.getFirstName().contains(keyword) || user.getLastName().contains(keyword))
				filtered_sublist.add(user);
		}
		return filtered_sublist;
	}

	@Override
	public List<ClaimCompensation> getAllSubordinatesCompensations(String mgrEmail) {
		List<User> sublist = this.getAllSubordinates(mgrEmail);
		ArrayList<ClaimCompensation> subComp= new ArrayList<ClaimCompensation>();
		for (User u : sublist)
			subComp.addAll(ccrepo.findByUserId(u.getId()));
		subComp = (ArrayList) subComp.stream().sorted(Comparator.comparing(ClaimCompensation::getClaimDate)).collect(Collectors.toList());
		return subComp;
	}

	@Override
	public List<ClaimCompensation> getSubordinateCompensationsByClaimStatus(String mgrEmail, ClaimStatus status) {
		List<ClaimCompensation> subComp = this.getAllSubordinatesCompensations(mgrEmail);
		List<ClaimCompensation> filtered_subComp = subComp.stream().filter(x -> x.getClaimStatus() == status)
				.sorted(Comparator.comparing(ClaimCompensation::getClaimDate)).collect(Collectors.toList());
		return filtered_subComp;
	}
	
	@Override
	public Float increaseThisSubordinateLeaveEntitled(String mgrEmail, Long subid, Float increaseBy) {
		LeaveEntitled subLE = lerepo.findCompensationLeaveByUserId(subid);
		subLE.setTotalLeave(subLE.getTotalLeave() + increaseBy);
		lerepo.saveAndFlush(subLE);
		return (subLE.getTotalLeave() + increaseBy);
	}
	
}


