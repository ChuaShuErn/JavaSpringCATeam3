package sg.edu.iss.LAPS.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.PublicHoliday;
import sg.edu.iss.LAPS.repo.PublicHolidayRepository;


@Service
public class PublicHolidayServiceImpl implements PublicHolidayService {
	
	@Autowired
	PublicHolidayRepository publicHolidayRepository;
	
	@Override
	public List<PublicHoliday> findAll() {
		return publicHolidayRepository.findAll();
	}

}
