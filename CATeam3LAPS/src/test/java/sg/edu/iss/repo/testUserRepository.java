package sg.edu.iss.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.LAPS.LAPSApplication;
import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.RoleRepository;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.services.ManagerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LAPSApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class testUserRepository {

	@Autowired
	UserRepository urepo;
	
	@Autowired
	RoleRepository rrepo;
	
	@Autowired
	ManagerService mservice;
	
	@Test
	@Order(1)
	public void testCheckIfStaffExistsbyEmail() {
		User u00 = urepo.checkIfStaffExistsbyEmail("Diego@u.nus.edu");
		assertEquals(u00.getFirstName(), "Diego");
	}

	@Test
	@Order(2)
	public void testfindSubordinates() {
		ArrayList<User> myTeamList = (ArrayList) urepo.findSubordinates("Gab@u.nus.edu");		
		assertEquals(myTeamList.size(), 4);
	}
	
	@Test
	@Order(3)
	public void testgetAllSubordinatesLeaves() {
		ArrayList<LeaveApplied> myTeamLeaves = (ArrayList) mservice.getAllSubordinatesLeaves("Gab@u.nus.edu");
		assertEquals(myTeamLeaves.size(), 3);
	}
	
}
