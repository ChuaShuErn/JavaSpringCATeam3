package sg.edu.iss.LAPS.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;
import sg.edu.iss.LAPS.utility.StaffLogin;

@Component
public class StaffLoginValidator implements Validator {
	
		
		@Autowired
		UserRepository urepo;

		@Override
		public boolean supports(Class<?> clazz) {
			// TODO Auto-generated method stub
			return StaffLogin.class.equals(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			StaffLogin user = (StaffLogin) target;
			System.out.println(user);
			String email = user.getEmail();
			User checkuser = urepo.checkIfStaffExistsbyEmail(email);
			if(checkuser == null)
			{
				errors.rejectValue("email", "email.error", "Email ID does not exist");
			}
			if (checkuser != null) {
				if (!(checkuser.getPassword().equals(user.getPassword()))) {
					errors.rejectValue("email", "email.error", "Invalid Email ID or Password");
					errors.rejectValue("password", "password.error","Invalid Email ID or Password");
					}
				}
			
		}
		

	}
