package sg.edu.iss.LAPS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.AdminService;
import sg.edu.iss.LAPS.services.LeaveTypeService;
import sg.edu.iss.LAPS.utility.Constants;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService aservice;

	@Autowired
	LeaveTypeService leaveTypeService;

	/*Admin staff mappings start here*/
	
	@GetMapping("/")
	public String viewUserList(Model model)
	{
		return showUserList(1,model);
	}
	
	@GetMapping("/staff/list/{pageNo}")
	public String showUserList(@PathVariable(value="pageNo") int pageNo,Model model )
	{
		int pageSize= Constants.ADMIN_STAFF_PAGE_SIZE;
		Page<User> page=aservice.findPaginated(pageNo,pageSize);
		List<User> userList=page.getContent();
		
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("userList",userList);
		return "adminUserList";
	}

	/*Admin leave type mappings start here*/

	@RequestMapping("/admin/leave-type/list")
	public String showLeaveTypeList(Model model){
		model.addAttribute("leaveTypes",leaveTypeService.getAllLeaveType());
		return "adminLeaveTypeList";
	}

	@RequestMapping("/admin/leave-type/add")
	public String addLeaveType(Model model){
		model.addAttribute("leaveType",new LeaveType());
		return "addLeaveTypeForm";
	}

	@RequestMapping("/admin/leave-type/edit/{id}")
	public String editLeaveType(@PathVariable("id") Integer id, Model model){
		model.addAttribute("leaveType",leaveTypeService.getLeaveTypeById(id));
		return "addLeaveTypeForm";
	}

	@PostMapping("/admin/leave-type/save")
	public String saveLeaveType(@ModelAttribute("leaveType") LeaveType leaveType,Model model){
		leaveTypeService.saveLeaveType(leaveType);
		return "forward:/admin/leave-type/list";
	}

	@RequestMapping("/admin/leave-type/delete/{id}")
	public String deleteLeaveType(@PathVariable("id") Integer id){
		leaveTypeService.deleteLeaveTypeById(id);
		return "forward:/admin/leave-type/list";
	}
}
