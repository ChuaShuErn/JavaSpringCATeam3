//package sg.edu.iss.LAPS.controller;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import sg.edu.iss.LAPS.model.Role;
//import sg.edu.iss.LAPS.model.User;
//import sg.edu.iss.LAPS.services.UserService;
//
//@Controller
//public class MainController {
//	@Autowired
//	private UserService uService;
//
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
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
//}