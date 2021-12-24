package sg.edu.iss.LAPS.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
	
	private JavaMailSender javaMailSender;

	public EmailNotificationServiceImpl(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	//TODO:add one more method for updating/deleting leave application 
	
	@Override
	public void sendLeaveCreationSucessful(User user, LeaveApplied leave) {
		SimpleMailMessage mail= new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Your leave application was created sucessfully!");
		mail.setText("Dear" + user.getFirstName() + user.getLastName() + "," 
					+ "\r\n" + "\r\n" + "Your Leave application ID: " 
					+ leave.getLeaveAppliedId() + " for " + leave.getLeaveType().getDescription() 
					+ "was created sucessfully!");
		javaMailSender.send(mail);
	}
	
	
	@Override
	public void sendLeaveCreationtoManager(User user, LeaveApplied leave, User manager) {
	SimpleMailMessage mail= new SimpleMailMessage();
	mail.setFrom("sa53team3@gmail.com"); //central administrative email to send automatic notification
	mail.setTo(manager.getEmail());
	mail.setSubject("Staff's leave application was created and pending approval!");
	mail.setText("Dear " + manager.getFirstName()  + " " + manager.getLastName() + ","
	+ "\r\n" + "\r\n" +user.getFirstName() + " " +user.getLastName() +"'s leave application for " 
			+ leave.getLeaveType().getDescription()+" Leave was created sucessfully and requires your action!"
	+ "\r\n" + "\r\n" + "Please login into the portal to review the application: http://localhost:8080/stafflogin/?");
	javaMailSender.send(mail);

	System.out.println("Mail is sent sucessfully!");
	}
	
	
	@Override
	public void sendLeaveApprovalOutcome(User user, LeaveApplied leave) {
		SimpleMailMessage mail= new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Your leave application was reviewed by your manager with an outcome");
		mail.setText("Dear" + user.getFirstName() + user.getLastName() + "," 
					+ "\r\n" + "\r\n" + "Your Leave application ID:" 
					+ leave.getLeaveAppliedId() + "for" + leave.getLeaveType().getDescription() 
					+ "was reviewed by your manager with the following outcome!" 
					+ "\r\n" + "\r\n" + "Your Leave Status:" + leave.getApprovalStatus().toString());
		javaMailSender.send(mail);
	}
	

}
