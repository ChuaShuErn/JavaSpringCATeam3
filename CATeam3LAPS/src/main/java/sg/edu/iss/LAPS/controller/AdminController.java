package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.model.Role;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.AdminService;
import sg.edu.iss.LAPS.services.LeaveTypeService;
import sg.edu.iss.LAPS.services.PublicHolidayService;
import sg.edu.iss.LAPS.services.RoleService;
import sg.edu.iss.LAPS.utility.Constants;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@Autowired
	LeaveTypeService leaveTypeService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	PublicHolidayService publicHolidayService;

	/* Staff mappings start here*/
	
	@GetMapping("/admin/staff/addStaff")
	public String loadStaffForm(Model model)
	{
		List<Role> newRoles=roleService.getAllRole();
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
	public String saveStaff(@Valid User user, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return "addStaffForm";
		}
		ArrayList<LeaveEntitled> leaveEntitleds = (ArrayList<LeaveEntitled>) user.getLeaveEntitledList();
		if(user.getId()==null) {

			//Take out the leave entitled list which has the user id as null
			//ArrayList<LeaveEntitled> leaveEntitleds = (ArrayList<LeaveEntitled>) user.getLeaveEntitledList();
			user.setLeaveEntitledList(null);

			//Save the user without leave entitled list to get the user id first
			adminService.saveUser(user);

			//For each leave entitled make the record point to the user which is saved in database
			for (LeaveEntitled leaveEntitled : leaveEntitleds) {
				leaveEntitled.setUser(user);
			}
			//Put back the leave entitled list
			user.setLeaveEntitledList(leaveEntitleds);
			adminService.saveUser(user);
		}
		else{
			User oldUser = adminService.getUserById(user.getId());
			oldUser.getLeaveEntitledList().clear();
			oldUser.getLeaveEntitledList().addAll(leaveEntitleds);
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setUsername(user.getUsername());
			oldUser.setPassword(user.getPassword());
			oldUser.setEmail(user.getEmail());
			oldUser.setReportsTo(user.getReportsTo());
			adminService.saveUser(oldUser);

		}
		return "redirect:/admin/staff";
	}
	
	@GetMapping("/admin/staff")
	public String viewUserList(Model model)
	{
		return showUserList(1,model,"");
	}
	
	@GetMapping("/admin/staff/list/{pageNo}")
	public String showUserList(@PathVariable(value="pageNo") int pageNo,Model model,@RequestParam(value = "keyword", required = false) String keyword)
	{
		int pageSize= Constants.ADMIN_PAGE_SIZE;
		Page<User> page=adminService.findPaginated(pageNo,pageSize,keyword);
		List<User> userList=page.getContent();	
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("userList",userList);
		model.addAttribute("keyword",keyword);
		return "adminUserList";
	}

	@GetMapping("/admin/staff/edit/{id}")
	public String editStaff(@PathVariable(value="id") Long id, Model model)
	{
		User user=adminService.getUserById(id);
		model.addAttribute("user",user);
		return "addStaffForm";
	}
	
	@GetMapping("/admin/staff/delete/{id}/{currPage}")
	public String deleteStaff(@PathVariable(value="id") Long id, 
			@PathVariable(value="currPage") Integer currPage)
	{
		adminService.deleteUserById(id);
		return "forward:/admin/staff/list/"+currPage;
	}
	

	/*Admin's leave type mappings start here*/

	@RequestMapping("/admin/leave-type/list/{pageNo}")
	public String showLeaveTypeList(@PathVariable(value="pageNo") int pageNo, Model model){
		int pageSize= Constants.ADMIN_PAGE_SIZE;
		Page<LeaveType> page=leaveTypeService.findPaginated(pageNo,pageSize);
		List<LeaveType> leaveTypeList=page.getContent();
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("leaveTypes",leaveTypeList);
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
	public String saveLeaveType(@Valid LeaveType leaveType, BindingResult bindingResult){
		if (bindingResult.hasErrors())
		{
			return "adminLeaveTypeForm";
		}
		leaveTypeService.saveLeaveType(leaveType);
		return "forward:/admin/leave-type/list/1";
	}
		

	@RequestMapping("/admin/leave-type/delete/{id}")
	public String deleteLeaveType(@PathVariable("id") Integer id){
		leaveTypeService.deleteLeaveTypeById(id);
		return "forward:/admin/leave-type/list/1";
	}

	/*Admin's role mappings start here*/

	@RequestMapping("/admin/role/list/{pageNo}")
	public String showRoleList(@PathVariable(value="pageNo") int pageNo, Model model){
		int pageSize= Constants.ADMIN_PAGE_SIZE;
		Page<Role> page=roleService.findPaginated(pageNo,pageSize);
		List<Role> roleList=page.getContent();
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("roles",roleList);
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
	public String saveLeaveType(@Valid Role role, BindingResult bindingResult){
		if (bindingResult.hasErrors())
		{
			return "adminRoleForm";
		}
		roleService.saveRole(role);
		return "forward:/admin/role/list/1";
	}

	@RequestMapping("/admin/role/delete/{id}")
	public String deleteRole(@PathVariable("id") Long id){
		roleService.deleteRoleById(id);
		return "forward:/admin/role/list/1";
	}
	/*Admin's Manage Holiday starts here*/
	
	@RequestMapping("/admin/holiday/list/{pageNo}")
	public String manageHoliday(@PathVariable(value="pageNo") int pageNo, Model model){
		int pageSize= Constants.ADMIN_PAGE_SIZE; 
		Page<PublicHoliday> page=publicHolidayService.findPaginated(pageNo,pageSize);
		List<PublicHoliday> publicHolidaysList=page.getContent();
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("publicHoliday",publicHolidaysList);
		 
		return "adminManageHoliday";
	}
	@GetMapping("/admin/holiday/addHoliday")
	public String addHoliday(Model model){
		  PublicHoliday pH=new PublicHoliday();
		  model.addAttribute("publicHoliday",pH);
		  return "adminHolidayForm";
	}
	
	@GetMapping("/admin/holiday/editHoliday/{holidayId}")
	public String editHoliday(@PathVariable("holidayId") int holidayId, Model model){
		  PublicHoliday pH=publicHolidayService.getById(holidayId);
		  model.addAttribute("publicHoliday",pH);
		  return "adminHolidayForm";
	}
	@PostMapping("/admin/holiday/saveHoliday")
	public String saveHoliday(@Valid PublicHoliday publicHoliday, 
			BindingResult bindingResult){ 
		if (bindingResult.hasErrors())
		{
			return "adminHolidayForm";
		}
		publicHolidayService.savePublicHoliday(publicHoliday);
		return "redirect:/admin/holiday/list/1";
	}
	@GetMapping("/admin/holiday/deleteHoliday/{holidayId}/{currPage}")
	public String deleteHoliday(@PathVariable(value="holidayId") Integer holidayId, 
			@PathVariable(value="currPage") Integer currPage)
	{
		publicHolidayService.deleteHolidayById(holidayId);
		return "redirect:/admin/holiday/list/"+currPage;
	}
}
