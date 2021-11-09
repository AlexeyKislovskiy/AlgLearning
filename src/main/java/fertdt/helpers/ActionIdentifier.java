package fertdt.helpers;

import fertdt.exceptions.IncorrectRequestException;

import javax.servlet.http.HttpServletRequest;

public class ActionIdentifier {

    public static Action identify(HttpServletRequest req) throws IncorrectRequestException {
        if (req.getParameter("mail") != null && req.getParameter("mail").length() > 0)
            return Action.REGISTRATION_WITH_EMAIL;
        else if (req.getParameter("nickname") != null) return Action.REGISTRATION;
        else if (req.getParameter("nickname-login") != null) return Action.LOGIN;
        else if (req.getParameter("forgot-mail") != null) return Action.RECOVERY;
        else if (req.getParameter("confirm-mail") != null) return Action.EMAIL_CONFIRMATION;
        else if (req.getParameter("confirm-mail2") != null) return Action.RECOVERY_CONFIRMATION;
        else throw new IncorrectRequestException("Incorrect request");
    }
}
