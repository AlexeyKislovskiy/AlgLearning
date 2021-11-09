package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;

public interface ISessionRepository {
    void saveSession(int cuberId, String SessionId, boolean remember) throws DatabaseException;

    Cuber getBySession(String sessionId) throws DatabaseException;

    boolean getRememberBySession(String sessionId) throws DatabaseException;
}
