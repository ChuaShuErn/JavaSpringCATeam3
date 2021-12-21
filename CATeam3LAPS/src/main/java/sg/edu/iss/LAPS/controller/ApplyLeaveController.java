package sg.edu.iss.LAPS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.OverseasLeaveDetails;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.*;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Controller
public class ApplyLeaveController {
    @Autowired
    LeaveAppliedService leaveAppliedService;

    @Autowired
    ApplyLeaveService applyLeaveService;

    @Autowired
    UserService userService;

    @Autowired
    LeaveTypeService leaveTypeService;

    @Autowired
    PublicHolidayService publicHolidayService;


    @RequestMapping(value="/applyleave")
	public String applyForm(Model model) {
		model.addAttribute("leaveapplication", new LeaveApplied());
		List<LeaveType> leaveTypeList = leaveTypeService.getAllLeaveType();
		model.addAttribute("leaveTypeList", leaveTypeList);
		model.addAttribute("overseasTrip", new OverseasLeaveDetails());
		return "applyLeave";
	}


    @RequestMapping(value = "/applyleave/submit")
    public String createLeaveApplication(@ModelAttribute("leaveapplication") @Valid LeaveApplied application, BindingResult bindingResult, HttpSession session, Model model) {

        User currUser = userService.findUserById((Long) session.getAttribute("id"));
        LeaveType leaveType = leaveTypeService.findLeaveTypeByleaveTypeId(application.getLeaveType()
                .getLeaveTypeId());
        application.setUser(currUser);
        application.setLeaveType(leaveType);
        application.setNoOfDays(applyLeaveService.countNumberOfDays(application.getLeaveStartDate(), application.getLeaveEndDate()));
        application.setApprovalStatus(LeaveStatus.APPLIED);
        applyLeaveService.createLeaveApplication(application);

        return "redirect:/"; // lalaaa

    }


}
