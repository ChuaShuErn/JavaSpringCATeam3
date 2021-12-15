package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.LAPS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
