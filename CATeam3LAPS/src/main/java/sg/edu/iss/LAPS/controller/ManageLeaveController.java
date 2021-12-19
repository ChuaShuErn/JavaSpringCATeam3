package sg.edu.iss.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.services.LeaveAppliedService;
import sg.edu.iss.LAPS.services.LeaveTypeService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/leave")
public class ManageLeaveController {
    @Autowired
    LeaveAppliedService service;

    @Autowired
    LeaveTypeService leaveTypeService;

    @RequestMapping(value = "/viewHistory")
    public String viewMyLeaveHistory(Model model, HttpSession session) {
//        int id = (int) session.getAttribute("id");
//        List<LeaveApplication> leaves = lservice.findApplicationByStaffId(id);
//        model.addAttribute("leaves", leaves);
//        List<Boolean> status = new ArrayList<>();
//        for (LeaveApplication l : leaves) {
//            boolean s = lservice.validateforCancel(l);
//            status.add(s);
//        }
//        model.addAttribute("status", status);

//        return "viewHistory";
        Long userId = 1L;
        model.addAttribute("leaveAppliedList", service.findByUserId(userId));
        return "viewHistory";
    }

    @RequestMapping(value = "/currentLeaves")
    public String viewCurrentLeaves(Model model, HttpSession session) {
        Long userId = 1L;
        model.addAttribute("leaveAppliedList", service.findByUserId(userId, ApprovalStatus.Applied));
        return "currentLeaves";
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
    public String update(@ModelAttribute("leaveApplied") LeaveApplied leaveApplied) {
        service.update(leaveApplied);
        return "redirect:/leave/currentLeaves";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/leave/currentLeaves";
    }

}
