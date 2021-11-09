package fertdt.helpers;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SendEmailException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresqlDBConnector implements DBConnector {
    private Connection connection;

    @Override
    public Connection getConnection() throws DatabaseException {
        if (connection != null) return connection;
        Properties p = new Properties();
        try {
            p.load(this.getClass().getResourceAsStream("/algLearningDB.ini"));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read email settings", new SendEmailException());
        }
        String connectionUrl = p.getProperty("connectionUrl");
        String name = p.getProperty("name");
        String password = p.getProperty("password");
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionUrl, name, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException("Сan't connect to the database", e);
        }
        return connection;
    }
}
