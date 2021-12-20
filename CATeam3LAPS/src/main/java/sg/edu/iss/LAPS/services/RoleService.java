package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.Role;

import java.util.List;

import org.springframework.data.domain.Page;

public interface RoleService {
    List<Role> getAllRole();
    void saveRole(Role role);
    Role getRoleById(Long id);
    void deleteRoleById(Long id);
    
    Page<Role> findPaginated(int pageNo,int pageSize);
}
