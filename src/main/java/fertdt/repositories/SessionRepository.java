package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cuber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepository implements ISessionRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public SessionRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Cuber returnCuber(ResultSet resultSet) throws SQLException {
        Cuber cuber = new Cuber(resultSet.getInt("id"), resultSet.getString("nickname"),
                resultSet.getString("email"), resultSet.getString("password"), resultSet.getDate
                ("registration_date"), resultSet.getInt("visited_days"), resultSet.getInt
                ("visited_days_row"), resultSet.getDate("last_visited"));
        setStatus(cuber, resultSet.getString("status"));
        return cuber;
    }

    private void setStatus(Cuber cuber, String status) {
        if (status.equals("user")) cuber.setStatus(Cuber.Status.USER);
        else if (status.equals("moderator")) cuber.setStatus(Cuber.Status.MODERATOR);
        else cuber.setStatus(Cuber.Status.ADMIN);
    }

    @Override
    public void saveSession(int cuberId, String sessionId, boolean remember) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_session(session_id, cuber_id, remember)" +
                " values (?,?,?)")) {
            prepareStatement.setString(1, sessionId);
            prepareStatement.setInt(2, cuberId);
            prepareStatement.setBoolean(3, remember);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Can't save session in database", e);
        }
    }

    @Override
    public Cuber getBySession(String sessionId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_session " +
                "inner join cuber on cuber_session.cuber_id = cuber.id where session_id= ?")) {
            prepareStatement.setString(1, sessionId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnCuber(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Can't save session in database", e);
        }
    }

    @Override
    public boolean getRememberBySession(String sessionId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select remember from cuber_session " +
                "where session_id= ?")) {
            prepareStatement.setString(1, sessionId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return false;
                else return resultSet.getBoolean("remember");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Can't save session in database", e);
        }
    }
}
