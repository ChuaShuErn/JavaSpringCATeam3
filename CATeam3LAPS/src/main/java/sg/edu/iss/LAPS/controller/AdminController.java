package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.model.Role;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.LeaveEntitledRepository;
import sg.edu.iss.LAPS.repo.RoleRepository;
import sg.edu.iss.LAPS.services.AdminService;
import sg.edu.iss.LAPS.services.LeaveTypeService;
import sg.edu.iss.LAPS.services.PublicHolidayService;
import sg.edu.iss.LAPS.services.RoleService;
import sg.edu.iss.LAPS.utility.Constants;

@Controller
public class AdminController {
	
	@Autowired
	AdminService aservice;
	
	@Autowired
	RoleRepository rrepo;

	@Autowired
	LeaveTypeService leaveTypeService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	LeaveEntitledRepository lErepo;
	
	@Autowired
	PublicHolidayService publicHolidayService;

	/* staff mappings start here*/
	
	@GetMapping("/admin/staff/addStaff")
	public String loadStaffForm(Model model)
	{
		List<Role> newRoles=rrepo.findAll();
		User user = new User();
		user.setRoles(newRoles);
		
		List<LeaveType> allLeaveType=leaveTypeService.getAllLeaveType();
		List<LeaveEntitled> allLeaveEntitled=new ArrayList<>();
		for(int i=0;i<allLeaveType.size();i++)
		{
			LeaveEntitled newLeave=new LeaveEntitled();
			newLeave.setLeaveType(allLeaveType.get(i));
			allLeaveEntitled.add(i, newLeave);
		}
		user.setLeaveEntitledList(allLeaveEntitled);
		model.addAttribute("user",user);
		
		return "addStaffForm";
	}
	
	@PostMapping("/admin/saveStaff")
	public String saveStaff(@ModelAttribute("user") User user, Model model)
	{
		System.out.println(user.getRoles());

		//Take out the leave entitled list which has the user id as null
		ArrayList<LeaveEntitled> leaveEntitleds = (ArrayList<LeaveEntitled>) user.getLeaveEntitledList();
		user.setLeaveEntitledList(null);

		//Save the user without leave entitled list to get the user id first
		aservice.saveUser(user);

		//For each leave entitled make the record point to the user which is saved in database
		for(LeaveEntitled leaveEntitled : leaveEntitleds){
			leaveEntitled.setUser(user);
		}
		//Put back the leave entitled list
		user.setLeaveEntitledList(leaveEntitleds);

		//save the user again
		aservice.saveUser(user);
		return "redirect:/admin/staff/list/1 ";
	}
	
	@GetMapping("/admin/staff")
	public String viewUserList(Model model)
	{
		return showUserList(1,model);
	}
	
	@GetMapping("/admin/staff/list/{pageNo}")
	public String showUserList(@PathVariable(value="pageNo") int pageNo,Model model)
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
	//Edit and delete staff
	@GetMapping("/admin/staff/edit/{id}")
	public String editStaff(@PathVariable(value="id") Long id, Model model)
	{
		User user=aservice.getUserById(id);
		model.addAttribute("user",user);
		return "addStaffForm";
	}
	
	@GetMapping("/admin/staff/delete/{id}/{currPage}")
	public String deleteStaff(@PathVariable(value="id") Long id, 
			@PathVariable(value="currPage") Integer currPage)
	{
		aservice.deleteUserById(id);
		return "forward:/admin/staff/list/"+currPage;
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
		return "adminLeaveTypeForm";
	}

	@RequestMapping("/admin/leave-type/edit/{id}")
	public String editLeaveType(@PathVariable("id") Integer id, Model model){
		model.addAttribute("leaveType",leaveTypeService.getLeaveTypeById(id));
		return "adminLeaveTypeForm";
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

	/*Admin role mappings start here*/

	@RequestMapping("/admin/role/list")
	public String showRoleList(Model model){
		model.addAttribute("roles",roleService.getAllRole());
		return "adminRoleList";
	}

	@RequestMapping("/admin/role/add")
	public String addRole(Model model){
		model.addAttribute("role",new Role());
		return "adminRoleForm";
	}

	@RequestMapping("/admin/role/edit/{id}")
	public String editRole(@PathVariable("id") Long id, Model model){
		model.addAttribute("role",roleService.getRoleById(id));
		return "adminRoleForm";
	}

	@PostMapping("/admin/role/save")
	public String saveLeaveType(@ModelAttribute("role") Role role,Model model){
		roleService.saveRole(role);
		return "forward:/admin/role/list";
	}

	@RequestMapping("/admin/role/delete/{id}")
	public String deleteRole(@PathVariable("id") Long id){
		roleService.deleteRoleById(id);
		return "forward:/admin/role/list";
	}
	/* Manage Holiday */
	
	@RequestMapping("/admin/holiday/list/{pageNo}")
	public String manageHoliday(@PathVariable(value="pageNo") int pageNo, Model model){
		  
		  int pageSize= Constants.ADMIN_PUBLICHOLIDAY_PAGE_SIZE; 
		  Page<PublicHoliday> page=publicHolidayService.findPaginated(pageNo,pageSize);
		  List <PublicHoliday> ph=page.getContent(); 
		  model.addAttribute("currentPage",pageNo);
		  model.addAttribute("totalPages",page.getTotalPages());
		  model.addAttribute("totalItems",page.getTotalElements());
		  
		  model.addAttribute("publicHoliday",ph);
		 
		  return "adminManageHoliday";
	}
}
