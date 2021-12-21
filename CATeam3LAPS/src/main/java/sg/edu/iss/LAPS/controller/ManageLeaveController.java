package sg.edu.iss.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.services.LeaveAppliedService;
import sg.edu.iss.LAPS.services.LeaveTypeService;
import sg.edu.iss.LAPS.validators.MockValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/leave")
public class ManageLeaveController {
    @Autowired
    LeaveAppliedService service;

    @Autowired
    LeaveTypeService leaveTypeService;

    @Autowired
    MockValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/viewHistory")
    public String viewMyLeaveHistory(Model model, HttpSession session) {
        long userId = (long) session.getAttribute("id");
        model.addAttribute("leaveAppliedList", service.findByUserId(userId));
        return "viewHistory";
    }

    @RequestMapping(value = "/detail/{id}")
    public String viewLeaveDetail(@PathVariable("id") Integer id, Model model) {
        Optional<LeaveApplied> optLeaveApplied = service.findById(id);
        if (optLeaveApplied.isEmpty()) {
            // Not found
            return "";
        }
        LeaveApplied leaveApplied = optLeaveApplied.get();
        model.addAttribute("leaveApplied", leaveApplied);

        return "detailLeave";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editLeave(@PathVariable("id") Integer id, Model model) {
        Optional<LeaveApplied> optLeaveApplied = service.findById(id);
        if (optLeaveApplied.isEmpty()) {
            // Not found
            return "";
        }
        LeaveApplied leaveApplied = optLeaveApplied.get();
        model.addAttribute("leaveApplied", leaveApplied);

        List<LeaveType> leaveTypeList = leaveTypeService.getAllLeaveType();
        model.addAttribute("leaveTypeList", leaveTypeList);

        return "editLeave";
    }

    @RequestMapping(value = "updateLeaveApplied")
    public String update(@ModelAttribute("leaveApplied") @Valid LeaveApplied leaveApplied) {
        service.update(leaveApplied);
        return "redirect:/leave/viewHistory";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/leave/viewHistory";
    }

}
