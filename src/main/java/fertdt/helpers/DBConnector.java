package fertdt.helpers;

import fertdt.exceptions.DatabaseException;

import java.sql.Connection;

public interface DBConnector {
    Connection getConnection() throws DatabaseException;
}
