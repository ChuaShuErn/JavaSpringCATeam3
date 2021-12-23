package sg.edu.iss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import sg.edu.iss.LAPS.model.ClaimCompensation;
import sg.edu.iss.LAPS.services.ClaimCompensationService;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LAPSApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class TestCompensationService {
	
	@Autowired
	ClaimCompensationService cService;
	
	
	@Test
	@Order(1)
	public void testfindByID() {
		
		ClaimCompensation compensation = cService.findByCompensationClaimId((Long.valueOf(1)));
		assertEquals(compensation.getCompensationClaimId(), 1);
	}
}