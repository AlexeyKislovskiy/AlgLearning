package fertdt.helpers;

public class EmailValidator implements IEmailValidator {
    private final org.apache.commons.validator.routines.EmailValidator validator;

    public EmailValidator(org.apache.commons.validator.routines.EmailValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid(String email) {
        return validator.isValid(email);
    }
}
