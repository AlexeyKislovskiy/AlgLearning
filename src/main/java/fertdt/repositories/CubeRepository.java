package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cube;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CubeRepository implements ICubeRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public CubeRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Cube returnCube(ResultSet resultSet) throws SQLException {
        return new Cube(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("image"), resultSet.getString("description"));
    }

    @Override
    public List<Cube> getAllCubes() throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cube where id<>-1 order by id")) {
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                List<Cube> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(returnCube(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Cube getCubeByName(String name) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();

        try (PreparedStatement prepareStatement = connection.prepareStatement(" select * from cube where name=?")) {
            prepareStatement.setString(1, name);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return returnCube(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
