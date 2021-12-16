package sg.edu.iss.LAPS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.services.AdminService;
import sg.edu.iss.LAPS.utility.Constants;

@Controller
public class AdminController {
	
	@Autowired
	AdminService aservice;
	
	@GetMapping("/staff")
	public String viewUserList(Model model)
	{
		return showUserList(1,model);
	}
	
	@GetMapping("/staff/list/{pageNo}")
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
	
}
