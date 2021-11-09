package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.DBConnector;
import fertdt.models.Cuber;
import fertdt.models.Setting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsRepository implements ISettingsRepository {
    private final DBConnector dbConnector;
    private Connection connection;

    public SettingsRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private Setting returnSetting(ResultSet resultSet) throws SQLException {
        return new Setting(resultSet.getInt("setting_id"), resultSet.getString("value"));
    }

    @Override
    public void saveSetting(Setting setting, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_settings " +
                "where cuber_id=? and setting_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, setting.getId());
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) insertNewSetting(setting, cuber);
                else updateSetting(setting, cuber);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void insertNewSetting(Setting setting, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("insert into " +
                "cuber_settings(cuber_id, setting_id, value) values (?,?,?)")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, setting.getId());
            prepareStatement.setString(3, setting.getValue());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    private void updateSetting(Setting setting, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("update cuber_settings set value=? " +
                "where cuber_id=? and setting_id=?")) {
            prepareStatement.setString(1, setting.getValue());
            prepareStatement.setInt(2, cuber.getId());
            prepareStatement.setInt(3, setting.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public Setting getSettingById(int settingId, Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("select * from cuber_settings " +
                "where cuber_id=? and setting_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, settingId);
            prepareStatement.execute();
            try (ResultSet resultSet = prepareStatement.getResultSet()) {
                if (!resultSet.next()) return null;
                else return (returnSetting(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void resetAll(Cuber cuber) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_settings where cuber_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }

    @Override
    public void reset(Cuber cuber, int settingId) throws DatabaseException {
        if (connection == null) connection = dbConnector.getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement("delete from cuber_settings where cuber_id=? and setting_id=?")) {
            prepareStatement.setInt(1, cuber.getId());
            prepareStatement.setInt(2, settingId);
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Problem with database", e);
        }
    }
}
