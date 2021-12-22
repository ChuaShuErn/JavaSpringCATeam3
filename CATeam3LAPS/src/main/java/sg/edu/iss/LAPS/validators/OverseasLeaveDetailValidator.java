package sg.edu.iss.LAPS.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.iss.LAPS.model.OverseasLeaveDetails;

@Component
public class OverseasLeaveDetailValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return OverseasLeaveDetails.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OverseasLeaveDetails trip = (OverseasLeaveDetails) target;	
		if(trip.getOverseasLeaveId() != null) {
			if(trip.getCountry() == null) {
				errors.rejectValue("overseasTripContact", "overseasTripContact");
			}else if(trip.getCity() == null) {
				errors.rejectValue("overseasTripContact", "overseasTripContact");
			}else if(trip.getPhone() == null) {
				errors.rejectValue("overseasTripContact", "overseasTripContact");
			}
		}

	}

} // Finish

