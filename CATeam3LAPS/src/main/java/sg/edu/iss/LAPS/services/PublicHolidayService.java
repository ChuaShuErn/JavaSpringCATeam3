package sg.edu.iss.LAPS.services;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.LAPS.model.PublicHoliday;

public interface PublicHolidayService {
	
	List<PublicHoliday> findAll();
	Page<PublicHoliday> findPaginated(int pageNo,int pageSize);
}
