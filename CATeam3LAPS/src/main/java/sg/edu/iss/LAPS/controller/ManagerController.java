package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.ClaimCompensationRepository;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.ClaimCompensationService;
import sg.edu.iss.LAPS.services.LeaveAppliedService;
import sg.edu.iss.LAPS.services.ManagerService;
import sg.edu.iss.LAPS.utility.Approve;
import sg.edu.iss.LAPS.utility.ClaimStatus;
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
	ClaimCompensationService cService;
	@Autowired
	ApproveValidator aValidator;
	
	@InitBinder("approve")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(aValidator);
	}
	
	@Autowired
	UserRepository urepo;
	@Autowired
	LeaveAppliedRepository laRepo;
	@Autowired
	ClaimCompensationRepository cRepo;
	
	//List down all pending leaves (on landing page, or on main use case page)
	@RequestMapping(value="/landing")
    public String landing(HttpSession session, Model model) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return "login";
		}
		List<LeaveApplied> subApplied = (ArrayList) mservice.getSubordinateLeavesByPending(
				manager.getEmail());
		model.addAttribute("manager", manager);
		model.addAttribute("pendingLeaves", subApplied);
		return "managerlanding";
	}
	
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
	
	//List down all compensation claims
	@RequestMapping(value="/compensation")
    public ModelAndView compensationList(HttpSession session) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return new ModelAndView("login");
		}
		ModelAndView mav = new ModelAndView("managerCompensationList");
		List<LeaveApplied> compList = (ArrayList) mservice.getSubordinateLeavesByLeaveType(
				manager.getEmail(), 3);
		mav.addObject("compLeaves", compList);
		return mav;
	}
	
	//List down all the team members, so can navigate to their employee leave histories
	@RequestMapping(value="/team")
    public String teamList(HttpSession session, @RequestParam(value = "keyword", required = false) String keyword, Model model) 
	{
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return "login";
		}
		//ModelAndView mav = new ModelAndView("managerTeamMemList");
		if (keyword == null)
			keyword = "";
		List<User> myTeamList = (ArrayList) mservice.getAllSubordinatesByKeyword(manager.getEmail(), keyword);
		model.addAttribute("teamList", myTeamList);
		model.addAttribute("keyword", keyword);
		return "managerTeamMemList";
	}
	
	//List down the employee leave history of the selected team member, by id
	@RequestMapping(value="/team/{id}")
    public ModelAndView teamMemberList(@PathVariable(value="id") Long subid, HttpSession session) {
		User manager = urepo.getById((long) session.getAttribute("id"));
		if (manager == null){
			return new ModelAndView("login");
		}
		ModelAndView mav = new ModelAndView("managerTeamMemLeaveList");
		ArrayList<LeaveApplied> thisSubLeave = (ArrayList) mservice.getThisSubordinateLeaves(manager.getEmail(), subid);
		User thisSub = mservice.getThisSubordinate(manager.getEmail(), subid);
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
	public ModelAndView approveOrRejectLeave(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
			@PathVariable Integer id, HttpSession session) {
		if (result.hasErrors()) {
			LeaveApplied leave = laService.findById(id).get();// 
			ModelAndView mav = new ModelAndView("managerLeaveDetail", "leaveApplied", leave);
			mav.addObject("approve", approve);
			return mav;
		}
			
		LeaveApplied leave = laService.findById(id).get();

		if (approve.getDecision().trim().equalsIgnoreCase(LeaveStatus.APPROVED.toString())) {
			leave.setApprovalStatus(LeaveStatus.APPROVED);
			laRepo.saveAndFlush(leave);
		} else {
			leave.setApprovalStatus(LeaveStatus.REJECTED);
			laRepo.saveAndFlush(leave);
		}
		leave.setManagerComments(approve.getComment());
		laRepo.saveAndFlush(leave);

		ModelAndView mav = new ModelAndView("forward:/manager/pending");
		String message = "Leave was successfully updated.";
		System.out.println(message);
		return mav;
	}

	//Show specific Leave Compensation application details, to be approved
	@RequestMapping(value = "/compensation/display/{id}", method = RequestMethod.GET)
	public ModelAndView compensationDetailToApprove(@PathVariable long id) {
		ArrayList<ClaimCompensation> compensation = cService.findByUserId(id);// check the service again
		ModelAndView mav = new ModelAndView("managerCompensationDetail", "ClaimCompensation", compensation);
		mav.addObject("approve", new Approve());
		return mav;
	}
	
	//Approve/reject the Leave compensation application
//	@RequestMapping(value = "/compensation/edit/{id}", method = RequestMethod.POST)
//	public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
//			@PathVariable Integer id, HttpSession session) {
//		if (result.hasErrors()) {
//			ArrayList<ClaimCompensation> compensation = cService.findByUserId(id);// check the service again
//			ModelAndView mav = new ModelAndView("managerCompensationDetail", "ClaimCompensation", compensation);
//			mav.addObject("approve", approve);
//			return mav;
//		}
//			
//		ArrayList<ClaimCompensation> compensation = cService.findByUserId(id);// check the service again
//
//		if (approve.getDecision().trim().equalsIgnoreCase(ClaimStatus.APPROVED.toString())) {
//			compensation.setClaimStatus(ClaimStatus.APPROVED);
//			cRepo.saveAndFlush(compensation);
//		} else {
//			compensation.setClaimStatus(ClaimStatus.REJECTED);
//			cRepo.saveAndFlush(compensation);
//		}
//		compensation.setManagerComments(approve.getComment());//need comment?
//		cRepo.saveAndFlush(compensation);
//
//		ModelAndView mav = new ModelAndView("forward:/manager/compensation");
//		String message = "Compensation was successfully updated.";
//		System.out.println(message);
//		return mav;
//	}

}