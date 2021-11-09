package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Cuber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISessionService {
    void saveSession(Cuber cuber, String remember, HttpServletResponse resp) throws DatabaseException;

    Cuber getBySession(String sessionId) throws DatabaseException;

    Cuber getByCurrentSession(HttpServletRequest req) throws DatabaseException, SessionException;

    boolean getRememberBySession(String sessionId) throws DatabaseException;

    void exit(HttpServletRequest req, HttpServletResponse res);
}
