package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The DatabaseManager-class is used to manage the communication with the database
 * @author Håkon
 */
public class DatabaseManager {

    /**
     * This method closes a connection
     * @param connection a connection to close
     */
    public static void closeConnection(Connection connection){
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e){
            System.out.println("Could not close connection!");
            e.printStackTrace();
        }
    }

    /**
     * This method sets AutoCommit to true on a given connection
     * @param connection the connection to set AutoCommit on
     */
    public static void setAutoCommit(Connection connection){
        try {
            if(connection != null && !connection.getAutoCommit())
                connection.setAutoCommit(true);
        } catch (SQLException e){
            System.out.println("Could not enable AutoCommit!");
            e.printStackTrace();
        }
    }

    /**
     * This method performs a rollback on a given connection
     * @param connection the connection to rollback
     */
    public static void rollBack(Connection connection){
        try {
            if(connection != null && !connection.getAutoCommit())
                connection.rollback();
        } catch (SQLException e){
            System.out.println("Could not rollback!");
            e.printStackTrace();
        }
    }

    /**
     * This method closes a PreparedStatement
     * @param statement the PreparedStatement to close
     */
    public static void closeStatement(PreparedStatement statement){
        try {
            if(statement != null)
                statement.close();
        } catch (SQLException e){
            System.out.println("Could not close statement!");
            e.printStackTrace();
        }
    }
}