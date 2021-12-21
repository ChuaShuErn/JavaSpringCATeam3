package sg.edu.iss.LAPS.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.LAPS.model.PublicHoliday;


@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Integer> {
}
