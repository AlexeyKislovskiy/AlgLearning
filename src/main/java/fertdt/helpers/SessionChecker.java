package fertdt.helpers;

import fertdt.exceptions.DatabaseException;
import fertdt.services.ISessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionChecker {
    public static boolean check(HttpServletRequest req, HttpServletResponse res, ISessionService sessionService) throws DatabaseException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("AlgLearningSessionID")) {
                    if (sessionService.getBySession(c.getValue()) != null) {
                        boolean remember = sessionService.getRememberBySession(c.getValue());
                        if (remember) {
                            c.setMaxAge(604800);
                            c.setPath(req.getContextPath());
                            res.addCookie(c);
                        }
                        return true;
                    } else return false;
                }
            }
        }
        return false;
    }
}
