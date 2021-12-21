package sg.edu.iss.LAPS.repo;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.LAPS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE u.email = :email AND r.id = 3 OR u.email = :email AND r.id = 2")
	public User checkIfStaffExistsbyEmail(@Param("email") String email);
	
	//check if Staff is Manager
	@Query("SELECT u FROM User u Join u.roles r WHERE u.email = :email AND r.id = 2")
	public User checkIfStaffIsManagerbyEmail(String email);
	
	//for admin login
	@Query("SELECT u FROM User u Join u.roles r WHERE u.email = :email AND r.id = 1")
	public User checkIfUserIsAdminbyEmail(@Param("email")String email);
	
	@Query("SELECT DISTINCT u2 FROM User u1, User u2 WHERE u1.id = u2.ReportsTo AND u1.email = :u1email")
	public ArrayList<User> findSubordinates(@Param("u1email") String u1email);
	
	@Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE CONCAT(u.FirstName,' ',u.LastName,' ',u.id,' ',u.email) LIKE %?1% OR r.name LIKE %?1% ORDER BY u.id")
	public Page<User> findALL(String keyword, Pageable pageable);
}
