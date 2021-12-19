package sg.edu.iss.LAPS.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.LAPS.model.ApprovalStatus;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.services.LeaveAppliedService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class ManageLeaveController {
    @Autowired
    LeaveAppliedService service;

    @RequestMapping(value = "/viewHistory")
    public String viewMyLeaveHistory(@RequestParam(name = "status", required = false) String status, Model model, HttpSession session) {
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
        List<LeaveApplied> leaveAppliedList;
        if (!Strings.isBlank(status)) {
            leaveAppliedList = service.findByUserId(userId, ApprovalStatus.valueOf(status));
        } else {
            leaveAppliedList = service.findByUserId(userId);
        }

        model.addAttribute(("LeaveApplied"), leaveAppliedList);
        return "viewHistory";
    }

//    @RequestMapping(value = "updateLeaveApplied")
//    public void update(@ModelAttribute("leaveapplication") LeaveApplication leaveApp) {
//
//    }

}
