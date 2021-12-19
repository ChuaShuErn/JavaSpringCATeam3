package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveType;
import sg.edu.iss.LAPS.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();
    void saveRole(Role role);
    Role getRoleById(Integer id);
    void deleteRoleById(Integer id);
}
