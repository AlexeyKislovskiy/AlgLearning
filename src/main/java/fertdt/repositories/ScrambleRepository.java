package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScrambleRepository implements IScrambleRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public ScrambleRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public String getScramble(int situationId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select text from scramble where situation_id=?")) {
            prepareStatement.setInt(1, situationId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return resultSet.getString("text");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Can't save session in database", e);
        }
    }
}
