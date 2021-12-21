package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.LeaveAppliedService;
import sg.edu.iss.LAPS.services.ManagerService;
import sg.edu.iss.LAPS.utility.Approve;
import sg.edu.iss.LAPS.utility.LeaveStatus;
import sg.edu.iss.LAPS.validators.ApproveValidator;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
	
	@Autowired
	ManagerService mservice;
	
	@Autowired
	LeaveAppliedService laService;
	
	@Autowired
	ApproveValidator aValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(new ApproveValidator());
	}
	
	@Autowired
	UserRepository urepo;
	
	//List down all pending leaves (on landing page, or on main use case page)
	@RequestMapping(value="/pending")
    public ModelAndView pendingApproval(HttpSession session) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return new ModelAndView("login");
		}
		ModelAndView mav = new ModelAndView("managerLeavePending");
		List<LeaveApplied> subApplied = (ArrayList) mservice.getSubordinateLeavesByPending(
				manager.getEmail());
		mav.addObject("pendingLeaves", subApplied);
		return mav;
	}
	
	//List down all the team members, so can navigate to their employee leave histories
	@RequestMapping(value="/team")
    public ModelAndView teamList(HttpSession session) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return new ModelAndView("login");
		}
		ModelAndView mav = new ModelAndView("managerTeamMemList");
		List<User> myTeamList = (ArrayList) mservice.getAllSubordinates(manager.getEmail());
		mav.addObject("teamList", myTeamList);
		return mav;
	}
	
	//List down the employee leave history of the selected team member, by id
	@RequestMapping(value="/team/{id}")
    public ModelAndView teamList(@PathVariable(value="id") Long subid, HttpSession session) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return new ModelAndView("login");
		}
		ModelAndView mav = new ModelAndView("managerTeamMemLeaveList");
		ArrayList<LeaveApplied> thisSubLeave = (ArrayList) mservice.getThisSubordinateLeaves(manager.getEmail(), subid);
		mav.addObject("thisSubLeave", thisSubLeave);
		return mav;
	}
	
	//Show specific team member's individual leave application details, pending approval
	@RequestMapping(value = "/leave/display/{id}", method = RequestMethod.GET)
	public ModelAndView leaveDetailToApprove(@PathVariable int id) {
		LeaveApplied leave = laService.findById(id).get();// 
		ModelAndView mav = new ModelAndView("managerLeaveDetail", "leaveApplied", leave);
		mav.addObject("approve", new Approve());
		return mav;
	}
	
	//Approve/reject the leave application
		@RequestMapping(value = "/leave/edit/{id}", method = RequestMethod.POST)
		public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
				@PathVariable Integer id, HttpSession session) {
			if (result.hasErrors())
				return new ModelAndView("managerLeaveDetail");
			
			LeaveApplied leave = laService.findById(id).get();

			if (approve.getDecision().trim().equalsIgnoreCase(LeaveStatus.APPROVED.toString())) {
				leave.setApprovalStatus(LeaveStatus.APPROVED);
			} else {
				leave.setApprovalStatus(LeaveStatus.REJECTED);
			}

			ModelAndView mav = new ModelAndView("forward:/manager/pending");
			String message = "Leave was successfully updated.";
			System.out.println(message);
			return mav;
		}
	

}