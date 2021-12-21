package sg.edu.iss.service;

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
import sg.edu.iss.LAPS.services.ManagerService;
import sg.edu.iss.LAPS.utility.LeaveStatus;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LAPSApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class TestManagerService {
	
	@Autowired
	ManagerService mservice;
	
	
	@Test
	@Order(1)
	public void testGetAllSubordinates() {
		ArrayList<LeaveApplied> myTeam = (ArrayList) mservice.getAllSubordinates("Gab@u.nus.edu");
		assertEquals(myTeam.size(), 4);
	}
	
	
	@Test
	@Order(2)
	public void testgetAllSubordinatesLeaves() {
		ArrayList<LeaveApplied> myTeamLeaves = (ArrayList) mservice.getAllSubordinatesLeaves("Gab@u.nus.edu");
		assertEquals(myTeamLeaves.size(), 4);
	}
	
	@Test
	@Order(3)
	public void testGetThisSubordinate() {
		Long subid = Long.valueOf(5);
		User myTeamMember = mservice.getThisSubordinate("Gab@u.nus.edu", subid);
		assertEquals(myTeamMember.getFirstName(), "Manny");
	}
	
	@Test
	@Order(4)
	public void testGetThisSubordinateLeaves() {
		Long subid = Long.valueOf(5);
		ArrayList<LeaveApplied> myTeamMemberLeaves = (ArrayList) mservice.getThisSubordinateLeaves("Gab@u.nus.edu", subid);
		assertEquals(myTeamMemberLeaves.size(), 2);
	}
	
	@Test
	@Order(5)
	public void testGetSubordinateLeavesByLeaveStatus() {
		ArrayList<LeaveApplied> AppliedOnlyLeaves = (ArrayList) mservice.getSubordinateLeavesByLeaveStatus(
				"Gab@u.nus.edu", LeaveStatus.APPLIED);
		assertEquals(AppliedOnlyLeaves.size(), 1);
	}
	

	@Test
	@Order(6)
	public void testLeaveStatus() {
		assertEquals(LeaveStatus.APPLIED.toString(), "APPLIED");
	}
	
	@Test
	@Order(7)
	public void testGetSubordinateLeavesByLeaveType() {
		ArrayList<LeaveApplied> AppliedOnlyLeaves = (ArrayList) mservice.getSubordinateLeavesByLeaveType(
				"Gab@u.nus.edu", 1); //get all the annual leaves
		assertEquals(AppliedOnlyLeaves.size(), 3);
	}
	
	
}
