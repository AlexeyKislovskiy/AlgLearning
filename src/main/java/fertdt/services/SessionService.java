package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.helpers.ISessionIDGenerator;
import fertdt.models.Cuber;
import fertdt.repositories.ISessionRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionService implements ISessionService {
    private final ISessionRepository sessionRepository;
    private final ISessionIDGenerator sessionIDGenerator;

    public SessionService(ISessionRepository sessionRepository, ISessionIDGenerator sessionIDGenerator) {
        this.sessionRepository = sessionRepository;
        this.sessionIDGenerator = sessionIDGenerator;
    }


    @Override
    public void saveSession(Cuber cuber, String remember, HttpServletResponse resp) throws DatabaseException {
        String sessionId = sessionIDGenerator.generate();
        Cookie cookie = new Cookie("AlgLearningSessionID", sessionId);
        boolean r;
        if (remember == null) {
            cookie.setMaxAge(-1);
            r = false;
        } else {
            cookie.setMaxAge(604800);
            r = true;
        }
        resp.addCookie(cookie);
        sessionRepository.saveSession(cuber.getId(), sessionId, r);
    }

    @Override
    public Cuber getBySession(String sessionId) throws DatabaseException {
        return sessionRepository.getBySession(sessionId);
    }

    @Override
    public Cuber getByCurrentSession(HttpServletRequest req) throws DatabaseException, SessionException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("AlgLearningSessionID")) {
                    return sessionRepository.getBySession(c.getValue());
                }
            }
        }
        throw new SessionException("Can't find session");
    }

    @Override
    public boolean getRememberBySession(String sessionId) throws DatabaseException {
        return sessionRepository.getRememberBySession(sessionId);
    }

    @Override
    public void exit(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("AlgLearningSessionID")) {
                c.setMaxAge(1);
                res.addCookie(c);
                return;
            }
        }
    }
}
