package sg.edu.iss.LAPS.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.ClaimCompensationService;
import sg.edu.iss.LAPS.services.UserService;
import sg.edu.iss.LAPS.utility.ClaimStatus;

@Controller
@RequestMapping("/staff")
public class ClaimCompensationController {
	
	@Autowired
	UserService uservice;
	
	@Autowired
	ClaimCompensationService ccservice;
	
	@GetMapping("/claimcompensation")
	private String loadClaimCompensationForm(Model model, HttpSession session)
	{
		ClaimCompensation claimCompensation = new ClaimCompensation();
		//User currentUser = uservice.findUserById((Long)session.getAttribute("id"));
		
		model.addAttribute("claimcompensation", claimCompensation);
		
		return "claimCompensationForm";
	}
	
	
	 @RequestMapping(value = "/claimcompensation/submit")
	 public String submitClaimCompensationForm(@ModelAttribute("claimcompensation") @Valid ClaimCompensation claimcompensation , BindingResult bindingResult, HttpSession session, Model model) 
	 {
		 System.out.println(claimcompensation);
			if (bindingResult.hasErrors()) {
				return "/claimcompensation";
			}
			User currentUser = uservice.findUserById((Long)session.getAttribute("id"));
			//ArrayList<ClaimCompensation> claimCompensationLedger = (ArrayList<ClaimCompensation>) currentUser.getClaimCompensation();
			claimcompensation.setUser(currentUser);
			claimcompensation.setClaimStatus(ClaimStatus.PENDING);
			ccservice.createCompensationClaim(claimcompensation);
			System.out.println(claimcompensation.toString());
			
		 return "forward:/staff/viewLedger"; 
	 }
	 
	 //@GetMapping("claimcompensation/leder")
	// private String getCompensationLedger()
	 //{
	//	 loadClaimCompensationLedger();
	 //}
	 
	 @RequestMapping(value = "/viewLedger")
		private String loadClaimCompensationLedger(Model model, HttpSession session)
		{
		 
			model.addAttribute("compensationClaimList",ccservice.findByUserId((Long) session.getAttribute("id")));
			return "claimCompensationLedger";
		}

}
