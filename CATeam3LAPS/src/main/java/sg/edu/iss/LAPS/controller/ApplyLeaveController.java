package sg.edu.iss.LAPS.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.ApplyLeaveService;
import sg.edu.iss.LAPS.services.LeaveTypeService;
import sg.edu.iss.LAPS.services.PublicHolidayService;
import sg.edu.iss.LAPS.services.UserService;

@Controller
@RequestMapping("/applyleave")
public class ApplyLeaveController {
	
	@Autowired
	ApplyLeaveService applyLeaveService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	PublicHolidayService publicHolidayService;
	
	
	@GetMapping("/")
	public String applyForm() {
		return "applyLeave/html";
	}
	
	public String createLeaveApplication(@ModelAttribute("leaveapplication") @Valid LeaveApplied application, BindingResult bindingResult, HttpSession session, Model model) {
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("leaveapplication", application);
//			model.addAttribute("leavetypes", ltservice.findAllLeaveTypeNamesExCL());
//			return "applyLeave";
//		}
		
		User currUser = userService.findUserById((Long)session.getAttribute("id"));
		LeaveType leaveType = leaveTypeService.findLeaveTypeByleaveTypeId(application.getLeaveType().getLeaveTypeId());
		application.setUser(currUser);
		application.setLeaveType(leaveType);
		Date leaveStartDate = application.getLeaveStartDate();
		Date leaveEndDate = application.getLeaveEndDate();
	    
		
		// Get a list of public holidays
		List<PublicHoliday> publicHolidaysList = publicHolidayService.findAll();
		
		
		
		
		
	}
	
	
}
