package sg.edu.iss.LAPS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.Role;
import sg.edu.iss.LAPS.repo.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        return  (ArrayList<Role>) roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public void deleteRoleById(Integer id) {
    roleRepository.delete(roleRepository.findById(id).get());
    }
}
