package sg.edu.iss.LAPS.services;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.LAPS.model.User;

public interface AdminService {
	List<User> getAllUser();
	void saveUser(User user);
	User getUserById(long id);
	void deleteUserById(long id);
	Page<User> findPaginated(int pageNo,int pageSize, String keyword);
}
