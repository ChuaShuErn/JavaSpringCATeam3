package sg.edu.iss.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.services.LeaveAppliedService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/leave")
public class ManageLeaveController {
    @Autowired
    LeaveAppliedService service;

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
    public String viewCurrentLeaves(@RequestParam(name = "status") String status, Model model, HttpSession session) {
        Long userId = 1L;
        model.addAttribute("leaveAppliedList", service.findByUserId(userId, ApprovalStatus.valueOf(status)));
        return "currentLeaves";
    }

}
