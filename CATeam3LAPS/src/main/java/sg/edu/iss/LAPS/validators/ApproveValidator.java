package sg.edu.iss.LAPS.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.iss.LAPS.utility.Approve;

@Component
public class ApproveValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> arg0) { 
			return Approve.class.equals(arg0);
			}
	@Override
	public void validate(Object obj, Errors e) {
		Approve approve = (Approve) obj;

		if(approve.getDecision() == null) {
			e.rejectValue("decision", "decision.error", "Decision is required");
		}
		else if(approve.getDecision().equals("REJECTED")) {
			//e.reject("comment", "Comment is required for rejected leave");
			if(approve.getComment().isBlank()) {
				e.rejectValue("comment", "comment.error", "Comment is required for rejected leave");
			}
	
		}
			//ValidationUtils.rejectIfEmptyOrWhitespace(e, "comment", "comment.error", "Comment is required for rejected leave");
		
//		else {
//			e.reject("comment", "Comment is required for rejected leave");
//			e.rejectValue("comment", "error.emptyComment", "Comment is required for rejected leave");
//			ValidationUtils.rejectIfEmptyOrWhitespace(e, "comment", "error.emptyComment", "Comment is required for rejected leave");
//		}
		
		
	}
}
