package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cuber;
import fertdt.models.Method;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MethodRepository implements IMethodRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public MethodRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Method returnMethod(ResultSet resultSet) throws SQLException {
        return new Method(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getInt("cube_id"), resultSet.getInt("number_of_situations"), resultSet.getString
                ("description"), resultSet.getInt("learning"), resultSet.getInt
                ("learned"), resultSet.getString("image"));
    }

    @Override
    public List<Method> getMethodsByCubeId(int cubeId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from method where cube_id=? " +
                "order by id")) {
            prepareStatement.setInt(1, cubeId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Method> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnMethod(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public List<Method> getAllMethods() throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from method where id<>-1 order by id")) {
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Method> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnMethod(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void setState(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cuber_learning_method where cuber_id" +
                "=? and method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    method.setState(Method.State.LEARNING);
                    return;
                }
            }
            try (PreparedStatement prepareStatement1 = connection.prepareStatement(" select * from cuber_learned_method where cuber_id" +
                    "=? and method_id=?")) {
                prepareStatement1.setInt(1, cuber.getId());
                prepareStatement1.setInt(2, method.getId());
                prepareStatement1.execute();
                try (ResultSet resultSet = prepareStatement1.getResultSet()) {
                    if (resultSet.next()) method.setState(Method.State.LEARNED);
                    else method.setState(Method.State.UNLEARNED);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Method getMethodByName(String name) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from method where name=?")) {
            prepareStatement.setString(1, name);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnMethod(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    public void setNumOfLearning(int methodId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select count(*) from cuber_learning_method where method_id=?")) {
            prepareStatement.setInt(1, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    int num = resultSet.getInt("count");
                    try (PreparedStatement prepareStatement1 = connection.prepareStatement("update method set learning=? where id=?")) {
                        prepareStatement1.setInt(1, num);
                        prepareStatement1.setInt(2, methodId);
                        prepareStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    public void setNumOfLearned(int methodId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select count(*) from cuber_learned_method where method_id=?")) {
            prepareStatement.setInt(1, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    int num = resultSet.getInt("count");
                    try (PreparedStatement prepareStatement1 = connection.prepareStatement("update method set learned=? where id=?")) {
                        prepareStatement1.setInt(1, num);
                        prepareStatement1.setInt(2, methodId);
                        prepareStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void checkAll(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        deleteFromCuberLearningMethod(method, cuber);
        deleteFromCuberLearnedMethod(method, cuber);
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_learned_method(cuber_id, method_id) values " +
                "(?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("select id from situation where method_id=?")) {
                prepareStatement1.setInt(1, method.getId());
                prepareStatement1.execute();
                try (ResultSet resultSet = prepareStatement1.getResultSet()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        PreparedStatement ps = connection.prepareStatement("insert into cuber_learned_situation" +
                                "(cuber_id, situation_id) values (?,?)");
                        ps.setInt(1, cuber.getId());
                        ps.setInt(2, id);
                        ps.execute();
                    }
                }
            }
            setNumOfLearning(method.getId());
            setNumOfLearned(method.getId());
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void plusAll(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        deleteFromCuberLearningMethodOnlyMethod(method, cuber);
        deleteFromCuberLearnedMethod(method, cuber);
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_learning_method(cuber_id, method_id) values " +
                "(?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("select id from situation where method_id=?")) {
                prepareStatement1.setInt(1, method.getId());
                prepareStatement1.execute();
                try (ResultSet resultSet = prepareStatement1.getResultSet()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        PreparedStatement psCheck = connection.prepareStatement("select * from cuber_learning_situation where cuber_id=? and situation_id=?");
                        psCheck.setInt(1, cuber.getId());
                        psCheck.setInt(2, id);
                        psCheck.execute();
                        try (ResultSet rs = psCheck.getResultSet()) {
                            if (!rs.next()) {
                                PreparedStatement ps = connection.prepareStatement("insert into cuber_learning_situation(cuber_id, situation_id, last_interval, next_repeat) values (?,?,?,?)");
                                ps.setInt(1, cuber.getId());
                                ps.setInt(2, id);
                                ps.setInt(3, 1);
                                Date date = new Date(new java.util.Date().getTime() + 24 * 60 * 60 * 1000);
                                ps.setDate(4, date);
                                ps.execute();
                            }
                        }
                    }
                }
            }
            setNumOfLearning(method.getId());
            setNumOfLearned(method.getId());
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void deleteAll(Method method, Cuber cuber) throws DatabaseException {
        deleteFromCuberLearningMethod(method, cuber);
        deleteFromCuberLearnedMethod(method, cuber);
        setNumOfLearning(method.getId());
        setNumOfLearned(method.getId());
    }

    private void deleteFromCuberLearningMethod(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        deleteFromCuberLearningMethodOnlyMethod(method, cuber);
        try (PreparedStatement prepareStatement1 = connection.prepareStatement("delete from cuber_learning_situation" +
                " where cuber_id = ? and situation_id in (select id from situation where method_id = ?)")) {
            prepareStatement1.setInt(1, cuber.getId());
            prepareStatement1.setInt(2, method.getId());
            prepareStatement1.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void deleteFromCuberLearningMethodOnlyMethod(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_learning_method " +
                "where cuber_id=? and method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void deleteFromCuberLearnedMethod(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_learned_method " +
                "where cuber_id=? and method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("delete from cuber_learned_situation" +
                    " where cuber_id = ? and situation_id in (select id from situation where method_id = ?)")) {
                prepareStatement1.setInt(1, cuber.getId());
                prepareStatement1.setInt(2, method.getId());
                prepareStatement1.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void recalculateState(Method method, Cuber cuber, ISituationRepository situationRepository) throws DatabaseException {
        deleteOldValue(method, cuber);
        List<Integer> list = situationRepository.getNumberOfAllTypes(method.getId(), cuber);
        if (list.get(2) == method.getNumberOfSituation()) setLearned(method.getId(), cuber);
        else if (list.get(0) != method.getNumberOfSituation()) setLearning(method.getId(), cuber);
    }

    private void deleteOldValue(Method method, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_learning_method " +
                "where cuber_id=? and method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, method.getId());
            prepareStatement.execute();
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("delete from cuber_learned_method " +
                    "where cuber_id=? and method_id=?")) {
                prepareStatement1.setInt(1, cuber.getId());
                prepareStatement1.setInt(2, method.getId());
                prepareStatement1.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setLearned(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_learned_method" +
                "(cuber_id, method_id) values (?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setLearning(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into cuber_learning_method" +
                "(cuber_id, method_id) values (?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
