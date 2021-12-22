package sg.edu.iss.LAPS.fullstack.MovementRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path="/api")
public class AllEmployeeLeaveController {
	@Autowired
	LeaveAppliedRepository leaveAppliedRepository;
	
	
	@GetMapping("/LeaveApplied")
	public List<LeaveApplied> getLeaveApplied(){
		LocalDate currentdate = LocalDate.now();
		int currentMonth=currentdate.getMonthValue();
		return leaveAppliedRepository.findLeaveAppliedByMonth(currentMonth);
	}
	
	
	
	
}
