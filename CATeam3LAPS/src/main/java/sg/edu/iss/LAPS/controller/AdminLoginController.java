package sg.edu.iss.LAPS.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.utility.AdminLogin;
import sg.edu.iss.LAPS.validators.AdminLoginValidator;

@Controller
public class AdminLoginController {
	
	@Autowired
	UserRepository urepo;
	
	@Autowired
	AdminLoginValidator adminloginvalidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(adminloginvalidator);
	}
	
	@RequestMapping("/adminlogin")
	public String adminlogin(Model model)
	{
		model.addAttribute("adminlogin", new AdminLogin());
		return "adminlogin";
	}

	@RequestMapping("/adminsubmit")
	public String adminsubmit(@ModelAttribute("adminlogin") @Valid AdminLogin adminlogin, BindingResult bindingResult, HttpSession session, Model model)
	{
		System.out.println(adminlogin);
		if (bindingResult.hasErrors()) {
			//model.addAttribute("stafflogin", new StaffLogin());
			//model.addAttribute("error","email");
			return "adminlogin";
		}

		else
		{
			User adminuser = urepo.checkIfUserIsAdminbyEmail(adminlogin.getEmail());
			session.setAttribute("role", "Admin");
			session.setAttribute("id", adminuser.getId());
			session.setAttribute("name", adminuser.getFirstName());
			return "adminlanding";
		}
	}
	/* Admin Login stuff*/

}
