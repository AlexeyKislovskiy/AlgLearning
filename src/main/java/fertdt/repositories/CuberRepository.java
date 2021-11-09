package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.helpers.PasswordEncryptor;
import fertdt.models.Cuber;

import java.sql.*;
import java.time.LocalDate;

public class CuberRepository implements ICuberRepository {
    private final DBConnector dbConnector;
    private final PasswordEncryptor passwordEncryptor;
    private Connection connection;

    public CuberRepository(DBConnector dbConnector, PasswordEncryptor passwordEncryptor) {
        this.dbConnector = dbConnector;
        this.passwordEncryptor = passwordEncryptor;
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

    private Cuber findByUnique(String uniqueString, String value) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber where " + uniqueString + "=?")) {
            prepareStatement.setString(1, value);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnCuber(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void save(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber(nickname,email,password)" +
                " values (?,?,?)")) {
            prepareStatement.setString(1, cuber.getNickname());
            prepareStatement.setString(2, cuber.getEmail());
            String hashedPassword = passwordEncryptor.encryptPassword(cuber.getPassword());
            prepareStatement.setString(3, hashedPassword);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }

    }

    @Override
    public Cuber findByNickname(String nickname) throws DatabaseException {
        return findByUnique("nickname", nickname);
    }

    @Override
    public Cuber findByEmail(String email) throws DatabaseException {
        return findByUnique("email", email);
    }

    @Override
    public Cuber findById(int id) throws DatabaseException {
        return findByUnique("id", String.valueOf(id));
    }

    @Override
    public int numOfLearning(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select count(*) from " +
                "cuber_learning_situation where cuber_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                resultSet.next();
                return (resultSet.getInt("count"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public int numOfLearned(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select count(*) from " +
                "cuber_learned_situation where cuber_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                resultSet.next();
                return (resultSet.getInt("count"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void updateVisit(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        Date lastVisited = cuber.getLastVisited();
        if (lastVisited.compareTo(Date.valueOf(LocalDate.now())) < 0) {
            int visitedDays = cuber.getVisitedDays();
            int visitedDaysRow = cuber.getVisitedDaysRow();
            visitedDays++;
            if (lastVisited.compareTo(Date.valueOf(LocalDate.now().minusDays(1))) < 0) {
                visitedDaysRow = 1;
            } else visitedDaysRow++;
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("update cuber" +
                    " set last_visited=?, visited_days=?,visited_days_row=? where id=?")) {
                prepareStatement1.setDate(1, new Date(new java.util.Date().getTime()));
                prepareStatement1.setInt(2, visitedDays);
                prepareStatement1.setInt(3, visitedDaysRow);
                prepareStatement1.setInt(4, cuber.getId());
                prepareStatement1.execute();
            } catch (SQLException e) {
                throw new DatabaseException("Problem with database", e);
            }
        }
    }

    @Override
    public void delete(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber where id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
