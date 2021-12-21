package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.OverseasLeaveDetails;

public interface OverseasLeaveService {
	
	OverseasLeaveDetails findOverseasLeaveDetailsByoverseasLeaveId(Integer overseasLeaveId);
	
}
