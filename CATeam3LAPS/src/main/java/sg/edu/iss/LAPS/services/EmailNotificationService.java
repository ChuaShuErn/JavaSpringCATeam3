package sg.edu.iss.LAPS.services;

import sg.edu.iss.LAPS.model.LeaveApplied;
import sg.edu.iss.LAPS.model.User;

public interface EmailNotificationService {
	
	public void sendLeaveCreationSucessful(User user, LeaveApplied leave);
}
