package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class sets up a connection pool
 * @author Håkon
 */
public class ConnectionPool {
    private final int MAX_SIZE = 20;

    private HikariDataSource dataSource;

    public ConnectionPool(){

        //Sets up the config (optimized for MySQL)
        HikariConfig config = new HikariConfig("/database/hikari.properties");

        //Creates a datasource from the config
        dataSource = new HikariDataSource(config);
    }

    /**
     * This method is used to get a connection from the connection pool
     * @return a connection from the connection pool
     */
    public Connection getConnection(){
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e){
            System.out.println("Could not get connection!");
            e.printStackTrace();
        }

        return connection;
    }
}