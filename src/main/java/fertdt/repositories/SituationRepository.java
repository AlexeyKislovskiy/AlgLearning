package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cuber;
import fertdt.models.Situation;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SituationRepository implements ISituationRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public SituationRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Situation returnSituation(ResultSet resultSet) throws SQLException {
        return new Situation(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getInt("method_id"), resultSet.getInt("mirror_id"),
                resultSet.getInt("reverse_id"), resultSet.getInt("mirror_reverse_id"),
                resultSet.getString("image"));
    }

    @Override
    public List<Situation> getSituationsByMethodId(int methodId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from situation where method_id=? " +
                "order by id")) {
            prepareStatement.setInt(1, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Situation> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnSituation(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void setState(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        setLearningState(situation, cuber);
        setTrainingState(situation, cuber);
        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cuber_learning_situation where cuber_id" +
                "=? and situation_id=?")) {
            setTrainingState(situation, cuber);
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    situation.setState(Situation.State.LEARNING);
                    return;
                }
            }
            try (PreparedStatement prepareStatement1 = connection.prepareStatement(" select * from cuber_learned_situation where cuber_id" +
                    "=? and situation_id=?")) {
                prepareStatement1.setInt(1, cuber.getId());
                prepareStatement1.setInt(2, situation.getId());
                prepareStatement1.execute();
                try (ResultSet resultSet = prepareStatement1.getResultSet()) {
                    if (resultSet.next()) situation.setState(Situation.State.LEARNED);
                    else situation.setState(Situation.State.UNLEARNED);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setTrainingState(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cuber_training_situation where cuber_id" +
                "=? and situation_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    situation.setTrainingState(Situation.TrainingState.TRAINING);
                } else situation.setTrainingState(Situation.TrainingState.NOT_TRAINING);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setLearningState(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cuber_learning_situation where cuber_id" +
                "=? and situation_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {

                    String status = resultSet.getString("status");
                    switch (status) {
                        case "new" -> situation.setLearningState(Situation.LearningState.NEW);
                        case "forgot" -> situation.setLearningState(Situation.LearningState.FORGOT);
                        case "repeat" -> situation.setLearningState(Situation.LearningState.REPEAT);
                        case "await" -> situation.setLearningState(Situation.LearningState.AWAIT);
                    }
                } else situation.setLearningState(Situation.LearningState.NOT_LEARNING);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Situation getSituationById(int id) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from situation where id=?")) {
            prepareStatement.setInt(1, id);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnSituation(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Situation getSituationByName(String name, int methodId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from situation where name=? " +
                "and method_id=?")) {
            prepareStatement.setString(1, name);
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnSituation(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public List<Integer> getNumberOfAllTypes(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select count(*) from situation where method_id=?")) {
            prepareStatement.setInt(1, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                resultSet.next();
                int total = resultSet.getInt("count");
                try (PreparedStatement prepareStatement1 = connection.prepareStatement(" select count(*) " +
                        "from cuber_learning_situation inner join situation on cuber_learning_situation.situation_id = situation.id" +
                        " where cuber_id=? and situation.method_id=?")) {
                    prepareStatement1.setInt(1, cuber.getId());
                    prepareStatement1.setInt(2, methodId);
                    prepareStatement1.execute();
                    try (ResultSet resultSet1 = prepareStatement1.getResultSet()) {
                        resultSet1.next();
                        int learning = resultSet1.getInt("count");
                        try (PreparedStatement prepareStatement2 = connection.prepareStatement(" select count(*) " +
                                "from cuber_learned_situation inner join situation on cuber_learned_situation.situation_id = situation.id" +
                                " where cuber_id=? and situation.method_id=?")) {
                            prepareStatement2.setInt(1, cuber.getId());
                            prepareStatement2.setInt(2, methodId);
                            prepareStatement2.execute();
                            try (ResultSet resultSet2 = prepareStatement2.getResultSet()) {
                                resultSet2.next();
                                int learned = resultSet2.getInt("count");
                                return Arrays.asList(total - learned - learning, learning, learned);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void check(Situation situation, Cuber cuber) throws DatabaseException {
        deleteFromCuberLearningSituation(situation, cuber);
        deleteFromCuberLearnedSituation(situation, cuber);
        try (PreparedStatement ps = connection.prepareStatement("insert into cuber_learned_situation" +
                "(cuber_id, situation_id) values (?,?)")) {
            ps.setInt(1, cuber.getId());
            ps.setInt(2, situation.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void plus(Situation situation, Cuber cuber) throws DatabaseException {
        deleteFromCuberLearnedSituation(situation, cuber);
        try (PreparedStatement psCheck = connection.prepareStatement("select * from cuber_learning_situation where cuber_id=? and situation_id=?")) {
            psCheck.setInt(1, cuber.getId());
            psCheck.setInt(2, situation.getId());
            psCheck.execute();
            try (ResultSet rs = psCheck.getResultSet()) {
                if (!rs.next()) {
                    PreparedStatement ps = connection.prepareStatement("insert into cuber_learning_situation(cuber_id, situation_id, last_interval, next_repeat) values (?,?,?,?)");
                    ps.setInt(1, cuber.getId());
                    ps.setInt(2, situation.getId());
                    ps.setInt(3, 1);
                    Date date = Date.valueOf(LocalDate.now());
                    ps.setDate(4, date);
                    ps.execute();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void delete(Situation situation, Cuber cuber) throws DatabaseException {
        deleteFromCuberLearningSituation(situation, cuber);
        deleteFromCuberLearnedSituation(situation, cuber);
    }

    private void deleteFromCuberLearningSituation(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_learning_situation" +
                " where cuber_id = ? and situation_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void deleteFromCuberLearnedSituation(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_learned_situation" +
                " where cuber_id = ? and situation_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public List<Situation> getAllTrainingSituationsByMethod(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_training_situation" +
                " inner join situation on cuber_training_situation.situation_id = situation.id where cuber_id=? and situation.method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Situation> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnSituation(resultSet));
                }
                if (list.isEmpty()) {
                    setDefaultTraining(methodId, cuber);
                    return getAllTrainingSituationsByMethod(methodId, cuber);
                } else return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setDefaultTraining(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        int sum = 0;
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_learning_situation" +
                " inner join situation on cuber_learning_situation.situation_id = situation.id where cuber_id=? and situation.method_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                while (resultSet.next()) {
                    sum++;
                    setTraining(returnSituation(resultSet), cuber);
                }
            }
            try (PreparedStatement prepareStatement1 = connection.prepareStatement("select * from cuber_learned_situation" +
                    " inner join situation on cuber_learned_situation.situation_id = situation.id where cuber_id=? and situation.method_id=?")) {
                prepareStatement1.setInt(1, cuber.getId());
                prepareStatement1.setInt(2, methodId);
                prepareStatement1.execute();
                try (ResultSet resultSet1 = prepareStatement1.getResultSet()) {
                    while (resultSet1.next()) {
                        sum++;
                        setTraining(returnSituation(resultSet1), cuber);
                    }
                }
            }
            if (sum == 0) {
                try (PreparedStatement prepareStatement3 = connection.prepareStatement("select * from situation" +
                        " where method_id=?")) {
                    prepareStatement3.setInt(1, methodId);
                    prepareStatement3.execute();
                    try (ResultSet resultSet3 = prepareStatement3.getResultSet()) {
                        while (resultSet3.next()) {
                            setTraining(returnSituation(resultSet3), cuber);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void setTraining(Situation situation, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into" +
                " cuber_training_situation(cuber_id, situation_id) values (?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void updateAllTraining(int methodId, Cuber cuber, HttpServletRequest req) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        deleteAllTraining(methodId, cuber);
        List<Situation> list = getSituationsByMethodId(methodId);
        for (Situation el : list) {
            if (req.getParameter(String.valueOf(el.getId())) != null) {
                setTraining(el, cuber);
            }
        }
    }

    private void deleteAllTraining(int methodId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_training_situation" +
                " where cuber_id = ? and situation_id in (select id from situation where method_id = ?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, methodId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void setLearningStatus(Situation situation, Cuber cuber, String status) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("update cuber_learning_situation set" +
                " status=? where situation_id=? and cuber_id=?")) {
            prepareStatement.setString(1, status);
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.setInt(3, cuber.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void updateNextLearning(Situation situation, Cuber cuber, double multiplier) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_learning_situation" +
                " where cuber_id=? and situation_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, situation.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (resultSet.next()) {
                    double last_interval = resultSet.getDouble("last_interval");
                    last_interval *= multiplier;
                    if (last_interval < 1) last_interval = 1;
                    Date date = Date.valueOf(LocalDate.now().plusDays((int) (last_interval)));
                    try (PreparedStatement prepareStatement1 = connection.prepareStatement("update cuber_learning_situation" +
                            " set next_repeat=?, last_interval=? where cuber_id=? and situation_id=?")) {
                        prepareStatement1.setDate(1, date);
                        prepareStatement1.setDouble(2, last_interval);
                        prepareStatement1.setInt(3, cuber.getId());
                        prepareStatement1.setInt(4, situation.getId());
                        prepareStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void updateLearningState(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_learning_situation" +
                " inner join situation s on s.id = cuber_learning_situation.situation_id where cuber_id=? and status=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setString(2, "await");
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                while (resultSet.next()) {
                    int situationId = resultSet.getInt("situation_id");
                    Date date = resultSet.getDate("next_repeat");
                    if (date.compareTo(Date.valueOf(LocalDate.now())) < 1) {
                        try (PreparedStatement prepareStatement1 = connection.prepareStatement("update cuber_learning_situation" +
                                " set status=? where cuber_id=? and situation_id=?")) {
                            prepareStatement1.setString(1, "repeat");
                            prepareStatement1.setInt(2, cuber.getId());
                            prepareStatement1.setInt(3, situationId);
                            prepareStatement1.execute();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
