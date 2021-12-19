package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.LAPS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE u.email = :email AND r.id = 3")
	public User checkIfStaffExistsbyEmail(@Param("email") String email);
	
	//check if Staff is Manager
	@Query("SELECT u FROM User u Join u.roles r WHERE u.email = :email AND r.id = 2")
	public User checkIfStaffIsManagerbyEmail(String email);

}
