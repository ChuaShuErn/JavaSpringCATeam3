package sg.edu.iss.LAPS.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.edu.iss.LAPS.model.LeaveApplied;

@Component
public class MockValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LeaveApplied.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("Hi Hi");
    }
}
