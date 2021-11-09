package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cuber;
import fertdt.models.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsRepository implements IStatisticsRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public StatisticsRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Statistics returnStatistics(ResultSet resultSet) throws SQLException {
        return new Statistics(resultSet.getDate("date"), resultSet.getInt("number_forgot"),
                resultSet.getInt("number_repeat"), resultSet.getInt("number_new"),
                resultSet.getInt("number_trained"));
    }

    @Override
    public void updateStatistics(Cuber cuber, String action) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_statistics where cuber_id=? and date=current_date")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                int number_forgot = 0, number_repeat = 0, number_new = 0, number_trained = 0;
                if (!resultSet.next()) insertNewStatistics(cuber);
                else {
                    number_forgot = resultSet.getInt("number_forgot");
                    number_repeat = resultSet.getInt("number_repeat");
                    number_new = resultSet.getInt("number_new");
                    number_trained = resultSet.getInt("number_trained");
                }
                switch (action) {
                    case "forgot" -> number_forgot++;
                    case "repeat" -> number_repeat++;
                    case "new" -> number_new++;
                    case "trained" -> number_trained++;
                }
                try (PreparedStatement prepareStatement1 = connection.prepareStatement("update cuber_statistics set " +
                        "number_forgot=?, number_repeat=?, number_new=?, number_trained=? where cuber_id=? and date=current_date")) {
                    prepareStatement1.setInt(1, number_forgot);
                    prepareStatement1.setInt(2, number_repeat);
                    prepareStatement1.setInt(3, number_new);
                    prepareStatement1.setInt(4, number_trained);
                    prepareStatement1.setInt(5, cuber.getId());
                    prepareStatement1.execute();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void insertNewStatistics(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_statistics(cuber_id) values (?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public List<Statistics> getAllStatistics(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_statistics where cuber_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Statistics> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnStatistics(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
