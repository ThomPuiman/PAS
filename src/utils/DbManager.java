package utils;

import java.sql.*;

/**
 * The Database Manager is responsible for creating/closing/maintaining
 * connections to the database.
 */
public class DbManager {
    static final String JDBC_EXCEPTION = "JDBC Exception: ";
    static final String SQL_EXCEPTION = "SQL Exception: ";

    private static Connection connection;

    /**
     * Open database connection
     */
    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://127.0.0.1:5432/pas2";
            String user = "pas2", pass = "3btyf3p2";

            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.err.println(JDBC_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(JDBC_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Executes a query without result.
     * @param query, the SQl query
     */
    boolean executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * Executes a query without result.
     * @param query, the SQl query
     */
    void executeBQuery(String query, byte[] insertByte) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBytes(1, insertByte);
            statement.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
    ResultSet doQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = statement.executeQuery(query);
        } catch (java.sql.SQLException e) {
            System.err.println(SQL_EXCEPTION + e.getMessage());
            e.printStackTrace(System.err);
        }
        return result;
    }
    
    public static Connection getConn(){
        return connection;
    }
}
