package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ConnectionFactory class provides static methods to create and manage database connections
 * as well as to close related resources.
 *
 * @see java.sql.Connection
 * @see java.sql.Statement
 * @see java.sql.ResultSet
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://localhost:5432/orderdb";
    private static final String USER = "postgres";
    private static final String PASS = "0000";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();
    private static Connection connection= null;

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        try {
            if(connection==null) {
                connection = DriverManager.getConnection(DBURL, USER, PASS);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Obtains a new database connection from the singleton instance.
     *
     * @return a {@link Connection} object representing an active connection to the database.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the current database connection if it has been initialized.
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    /**
     * Closes the provided {@link Statement} object if it is not null.
     *
     * @param statement the {@link Statement} instance to be closed.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    /**
     * Closes the provided {@link ResultSet} object if it is not null.
     *
     * @param resultSet the {@link ResultSet} instance to be closed.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
            }
        }
    }

}
