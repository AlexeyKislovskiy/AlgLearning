package fertdt.helpers;

import fertdt.exceptions.SendEmailException;

public interface IEmailSender {
    void send(String subject, String content, String toEmail) throws SendEmailException;
}
