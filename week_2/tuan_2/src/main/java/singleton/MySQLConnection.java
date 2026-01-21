package singleton;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static MySQLConnection instance;
    private Connection connection;


    private static final String URL =
            "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    private MySQLConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối MySQL thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
