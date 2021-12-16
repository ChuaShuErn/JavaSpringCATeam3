package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.User;
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
    public LeaveType getLeaveTypeById(long id) {
        return (LeaveType) leaveTypeRepository.findById(id).get();
    }

    @Override
    public void deleteLeaveTypeById(long id) {
        leaveTypeRepository.delete(leaveTypeRepository.findById(id));
    }
}
