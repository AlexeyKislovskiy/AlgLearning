package fertdt.helpers;

import fertdt.exceptions.SendEmailException;

import java.io.InputStream;

public class RecoveryMessageGenerator {
    public static String[] generateMessage() throws SendEmailException {
        InputStream is = RecoveryMessageGenerator.class.getResourceAsStream("/recoveryMessage.html");
        return MessageGenerator.generateMessage(is);
    }
}
