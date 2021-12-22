package sg.edu.iss.LAPS.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
	
	private JavaMailSender javaMailSender;
	
	//TODO:add one more method for updating/deleting leave application 
	
	
	@Override
	public void sendLeaveCreationSucessful(User user, LeaveApplied leave) {
		SimpleMailMessage mail= new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Your leave application was created sucessfully!");
		mail.setText("Dear" + user.getLastName() + user.getLastName() + "," 
					+ "\r\n" + "\r\n" + "Your Leave application ID:" 
					+ leave.getLeaveAppliedId() + "for" + leave.getLeaveType().getDescription() 
					+ "was created sucessfully!");
		javaMailSender.send(mail);
	}

}
