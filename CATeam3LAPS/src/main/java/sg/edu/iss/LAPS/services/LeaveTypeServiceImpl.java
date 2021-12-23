package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.repo.LeaveTypeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public List<LeaveType> getAllLeaveType() {
        return (ArrayList<LeaveType>) leaveTypeRepository.findAll();
    }

    @Override
    public void saveLeaveType(LeaveType leaveType) {
        leaveTypeRepository.save(leaveType);
    }

    @Override
    public LeaveType getLeaveTypeById(Integer id) {
        return (LeaveType) leaveTypeRepository.findById(id).get();
    }

    @Override
    public void deleteLeaveTypeById(Integer id) {
        leaveTypeRepository.delete(leaveTypeRepository.findById(id).get());
    }

	@Override
	public LeaveType findLeaveTypeByleaveTypeId(Integer leaveTypeId) {
		return leaveTypeRepository.findById(leaveTypeId).get();
	}

	@Override
	public Page<LeaveType> findPaginated(int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return leaveTypeRepository.findAll(pageable);
	}

	@Override
	public LeaveType findLeaveTypeByDescription(String description) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
