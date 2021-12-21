package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.OverseasLeaveDetails;
import sg.edu.iss.LAPS.repo.OverseasLeaveRepository;


@Service
public class OverseasLeaveServiceImpl implements OverseasLeaveService {
	
	@Autowired
	OverseasLeaveRepository overseasLeaveRepository;
	
	@Override
	public OverseasLeaveDetails findOverseasLeaveDetailsByoverseasLeaveId(Integer overseasLeaveId) {
		return overseasLeaveRepository.findById(overseasLeaveId).get();
	}

}
