package fertdt.helpers;

import fertdt.exceptions.SendEmailException;

import java.io.InputStream;

public class ConfirmationMessageGenerator {
    public static String[] generateMessage() throws SendEmailException {
        InputStream is = ConfirmationMessageGenerator.class.getResourceAsStream("/confirmationMessage.html");
        return MessageGenerator.generateMessage(is);
    }
}
