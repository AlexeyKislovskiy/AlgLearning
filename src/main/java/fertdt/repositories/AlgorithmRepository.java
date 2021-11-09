package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Algorithm;
import fertdt.models.Cuber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmRepository implements IAlgorithmRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public AlgorithmRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Algorithm returnAlgorithm(ResultSet resultSet) throws SQLException {
        return new Algorithm(resultSet.getInt("id"), resultSet.getString("text"),
                resultSet.getInt("situation_id"), resultSet.getInt("number_of_uses"),
                resultSet.getBoolean("verified"), resultSet.getInt("add_cuber_id"));
    }

    @Override
    public List<Algorithm> getAlgorithmsBySituationId(int situationId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from algorithm where situation_id=? " +
                "order by number_of_uses desc")) {
            prepareStatement.setInt(1, situationId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Algorithm> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnAlgorithm(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void setState(Algorithm algorithm, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cuber_uses_algorithm where cuber_id" +
                "=? and algorithm_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, algorithm.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    algorithm.setState(Algorithm.State.USING);
                } else algorithm.setState(Algorithm.State.NOT_USING);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void addUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into " +
                "cuber_uses_algorithm(cuber_id, algorithm_id) values (?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, algorithmId);
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("update algorithm set number_of_uses=? where id=?")) {
                Algorithm algorithm = getAlgorithmById(algorithmId);
                prepareStatement1.setInt(1, algorithm.getNumberOfUses() + 1);
                prepareStatement1.setInt(2, algorithmId);
                prepareStatement1.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void deleteUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from " +
                "cuber_uses_algorithm where cuber_id=? and algorithm_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, algorithmId);
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("update algorithm set number_of_uses=? where id=?")) {
                Algorithm algorithm = getAlgorithmById(algorithmId);
                prepareStatement1.setInt(1, algorithm.getNumberOfUses() - 1);
                prepareStatement1.setInt(2, algorithmId);
                prepareStatement1.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Algorithm getAlgorithmById(int algorithmId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from algorithm where id=?")) {
            prepareStatement.setInt(1, algorithmId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnAlgorithm(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void addAlgorithm(int situationId, String text, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into " +
                "algorithm(text,situation_id,number_of_uses,verified,add_cuber_id) values (?,?,?,?,?)")) {
            prepareStatement.setString(1, text);
            prepareStatement.setInt(2, situationId);
            prepareStatement.setInt(3, 0);
            prepareStatement.setBoolean(4, cuber.getStatus() != Cuber.Status.USER);
            prepareStatement.setInt(5, cuber.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public List<Algorithm> getNotVerifiedAlgorithms() throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("select algorithm.id as id, text," +
                "situation_id,number_of_uses,verified,add_cuber_id,s.name as sname,s.image as simage,m.name as mname from algorithm inner join" +
                " situation s on algorithm.situation_id = s.id inner join method m on s.method_id = m.id where verified=false")) {
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Algorithm> list = new ArrayList<>();
                while (resultSet.next()) {
                    Algorithm algorithm = returnAlgorithm(resultSet);
                    algorithm.setMethodName(resultSet.getString("mname"));
                    algorithm.setSituationName(resultSet.getString("sname"));
                    algorithm.setSituationImage(resultSet.getString("simage"));
                    list.add(algorithm);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void verify(int algorithmId, String text) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("update algorithm set verified=true, text=? where id=?")) {
            prepareStatement.setString(1, text);
            prepareStatement.setInt(2, algorithmId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void delete(int algorithmId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from algorithm where id=?")) {
            prepareStatement.setInt(1, algorithmId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
