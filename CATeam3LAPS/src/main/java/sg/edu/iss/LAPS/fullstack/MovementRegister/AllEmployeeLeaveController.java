package sg.edu.iss.LAPS.fullstack.MovementRegister;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.repo.LeaveAppliedRepository;
import sg.edu.iss.LAPS.repo.RoleRepository;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path="/api")
public class AllEmployeeLeaveController {
	@Autowired
	LeaveAppliedRepository leaveAppliedRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/leaveApplied")
	public List<LeaveApplied> getLeaveApplied(){
		LocalDate currentdate = LocalDate.now();
		int currentMonth=currentdate.getMonthValue();
		int currentYear=currentdate.getYear();
		List<LeaveApplied> leaveList=leaveAppliedRepository.findByMonth(currentMonth,currentYear);
		leaveList.stream().forEach(x->{
			x.getUser().setLeaveAppliedList(null);
			x.getUser().setLeaveEntitledList(null);});
		return leaveList;
	}
	
	@PostMapping("/leaveApplied/update/{month}")
	public ResponseEntity<?> updateMonth(@PathVariable("month") int month) {
		LocalDate currentdate = LocalDate.now();
		int yearToUpdate=currentdate.getYear();
		if (month>12)
		{
			yearToUpdate++;
			month-=12;
		}
		List<LeaveApplied> newList = leaveAppliedRepository.findByMonth(month,yearToUpdate);
		newList.stream().forEach(x->{
			x.getUser().setLeaveAppliedList(null);
			x.getUser().setLeaveEntitledList(null);});
		if (!newList.isEmpty()) {
			return new ResponseEntity<>(newList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
