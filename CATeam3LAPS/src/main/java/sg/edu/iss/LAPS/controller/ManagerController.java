package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.ManagerService;
import sg.edu.iss.LAPS.utility.LeaveStatus;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
	
	@Autowired
	ManagerService mservice;
	
	@Autowired
	UserRepository urepo;
	
	@RequestMapping(value="/leave/pending")
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
	
	
	
	
//
//	@Autowired
//	//LeaveService creation needed
//	private LeaveService lService;
//
//	@InitBinder
//	private void initBinder(WebDataBinder binder) {
//
//	}
//
//	@RequestMapping(value="/pending")
//	public ModelAndView pendingApproval(HttpSession session) {
//
//		UserSession usession = (UserSession) session.getAttribute("usession");
//		//UserSessionController needed
//		HashMap<Employee, ArrayList<LeaveApplied>> hm = new HashMap<Employee, ArrayList<LeaveApplied>>();
//		System.out.println(usession.toString());
//		ModelAndView mav = new ModelAndView("login");
//		if (usession.getUser() != null) {
//			for (Employee employee : usession.getSubordinates()) {
//				ArrayList<Course> clist = cService.findPendingCoursesByEID(employee.getEmployeeId());
//				hm.put(employee, clist);
//			}
//			mav = new ModelAndView("manager-pending-course-history");
//			mav.addObject("pendinghistory", hm);
//			return mav;
//		}
//		return mav;
//
//	}
//	@RequestMapping(value="/leave/display/{id}", method = RequestMethod.GET)
//	public ModelAndView viewLeaveDetails(@PathVariable int id) {
//
//	}
//
//	@RequestMapping(value = "/course/display/{id}", method = RequestMethod.GET)
//	public ModelAndView newDepartmentPage(@PathVariable int id) {
//		Course course = cService.findCourse(id);
//		ModelAndView mav = new ModelAndView("manager-course-detail", "course", course);
//		mav.addObject("approve", new Approve());
//		return mav;

}