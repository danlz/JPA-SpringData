package pl.danlz.trainings.jpaspringdata;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String SERVER_NAME = "localhost";
    private static final int DATABASE_PORT = 3306;
    private static final String DATABASE_NAME = "jpa_training";
    private static final String DATABASE_USER = "app_user";
    private static final String DATABASE_PASSWORD = "app_pass";

    public static void main(String[] args) {

        printMySqlVersion(SERVER_NAME, DATABASE_PORT, DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
    }

    /**
     * Prints version of MySQL server.
     */
    private static void printMySqlVersion(String serverName, int port, String databaseName, String databaseUser, String databasePassword) {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(serverName);
            dataSource.setPort(port);
            dataSource.setDatabaseName(databaseName);
            dataSource.setUser(databaseUser);
            dataSource.setPassword(databasePassword);
            // time zone should be configured in server
            //dataSource.setServerTimezone("GMT+1");

            Connection connection = dataSource.getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT @@version");
            resultSet.next();
            String mySqlVersion = resultSet.getString("@@version");
            resultSet.close();

            connection.close();

            System.out.println("MySQL version: " + mySqlVersion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
