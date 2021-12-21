package sg.edu.iss.LAPS.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.UserService;
import sg.edu.iss.LAPS.utility.StaffLogin;
import sg.edu.iss.LAPS.validators.StaffLoginValidator;

@Controller
public class MainController {
	@Autowired
	private UserService uService;

	@Autowired
	UserRepository urepo;
	
	@Autowired
	StaffLoginValidator staffloginvalidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(staffloginvalidator);
	}

	
	@RequestMapping("/stafflogin")
	public String stafflogin(Model model)
	{
		model.addAttribute("stafflogin", new StaffLogin());
		return "stafflogin";
	}
	
	//We assume that all Manager are also qualify as Staff
	//Please keep email address for all users unique
	@RequestMapping("/submit")
	public String submit(@ModelAttribute("stafflogin") @Valid StaffLogin stafflogin, BindingResult bindingResult, HttpSession session, Model model)
	{
		System.out.println(stafflogin);
		if (bindingResult.hasErrors()) {
			//model.addAttribute("stafflogin", new StaffLogin());
			//model.addAttribute("error","email");
			return "stafflogin";
		}
		User currentUser = urepo.checkIfStaffExistsbyEmail(stafflogin.getEmail());
		
		if(currentUser == urepo.checkIfStaffIsManagerbyEmail(currentUser.getEmail()))
		{
			session.setAttribute("role", "Manager");
			session.setAttribute("id", currentUser.getId());
			session.setAttribute("name", currentUser.getFirstName());
			return "managerlanding";
			
		}
		else
		{
			session.setAttribute("role", "Staff");
			session.setAttribute("id", currentUser.getId());
			session.setAttribute("name", currentUser.getFirstName());
			return "redirect:/stafflanding";
		}
	}
	@GetMapping("/stafflanding")
	public String stafflanding()
	{
		return "redirect:/leave/viewHistory";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
//
//	@RequestMapping(value = "/login/authenticate")
//	public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
//			HttpSession session) {
//		UserSession usession = new UserSession();
//		if (bindingResult.hasErrors()) {
//			return "login";
//		} else {
//			User u = uService.authenticate(user.getName(), user.getPassword()); // need to create userService methods
//			usession.setUser(u);
//
//			List<Role> rset = u.getRoles();
//			for (Iterator<Role> iterator = rset.iterator(); iterator.hasNext();) {
//				Role role = (Role) iterator.next();
//				System.out.println(role.toString());
//			}
//
//			usession.setUser(usession.getUser());
//			ArrayList<User> subordinates = uService.findSubordinates(usession.getUser()); //need to create userService methods
//			if (subordinates != null) {
//				usession.setSubordinates(subordinates);
//			}
//			session.setAttribute("usession", usession);
//			return "forward:/staff/history"; //
//		}
//
//	}
//
}