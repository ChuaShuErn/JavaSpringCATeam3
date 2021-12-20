package sg.edu.iss.LAPS.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	@Autowired
	UserRepository urepo;
	
	@Override
	public List<User> getAllUser() {
		return urepo.findAll();
	}

	@Override
	public void saveUser(User user) {
		urepo.saveAndFlush(user);
	}

	@Override
	public User getUserById(long id)
	{
		return urepo.findById(id).get();
	}

	@Override
	@Modifying
	public void deleteUserById(long id) {
		User user=getUserById(id);
		user.getRoles().clear();
		user.getLeaveAppliedList().clear();
		user.getLeaveEntitledList().clear();
		urepo.deleteById(id);
	}

	@Override
	public Page<User> findPaginated(int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return urepo.findAll(pageable);
	}
}
