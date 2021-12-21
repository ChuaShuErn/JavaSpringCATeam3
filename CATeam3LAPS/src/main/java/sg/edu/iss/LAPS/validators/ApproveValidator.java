package sg.edu.iss.LAPS.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.LAPS.model.LeaveApplied;

@Component
public class ApproveValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> arg0) { 
			return LeaveApplied.class.isAssignableFrom(arg0);
			}
	@Override
	public void validate(Object obj, Errors e) {
		LeaveApplied leaveApplied = (LeaveApplied) obj;
		if(leaveApplied.getApprovalStatus().toString()=="REJECTED") {
			ValidationUtils.rejectIfEmptyOrWhitespace(e, "emptyComment", "error.emptyComment", "Comment is required for rejected leave");
		}
	
	}
}