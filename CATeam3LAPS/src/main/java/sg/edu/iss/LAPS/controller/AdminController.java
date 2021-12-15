package sg.edu.iss.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.repo.UserRespository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	UserRespository urepo;
}
