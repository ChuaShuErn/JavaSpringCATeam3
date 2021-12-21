package sg.edu.iss.LAPS.fullstack.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.repo.PublicHolidayRepository;
import sg.edu.iss.LAPS.services.PublicHolidayService;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping(path="/api/admin-dashboard/")
public class AdminDashboardController {
	@Autowired
	PublicHolidayService publicHolidayService;
	@Autowired
	PublicHolidayRepository pHrepo;
	
	@GetMapping("/admin/public-holiday")
	public List<PublicHoliday> getPublicHoliday(){
		return publicHolidayService.findAll();
	}
	
	@PostMapping("/admin/public-holiday")
	public ResponseEntity<PublicHoliday> createPublicHoliday(@RequestBody PublicHoliday publicHoliday)
	{
		try {
			PublicHoliday pH=publicHolidayService.savePH(new PublicHoliday(publicHoliday.getHolidayId(),
					publicHoliday.getHolidayName(),publicHoliday.getHolidayStartDate(),publicHoliday.getHolidayEndDate()));
			return new ResponseEntity<>(pH,HttpStatus.CREATED);
		}catch(Exception e)
		{
			return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/admin/public-holiday/{holidayId}")
	public ResponseEntity<PublicHoliday> getPublicHolidayById(@PathVariable("holidayId") Integer holidayId)
	{
		int i=holidayId;
		Optional<PublicHoliday> pHData=pHrepo.findById(holidayId);
		if (pHData.isPresent())
		{
			return new ResponseEntity<>(pHData.get(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

}
