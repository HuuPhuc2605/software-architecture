package singleton;


import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {

        Connection conn1 = MySQLConnection.getInstance().getConnection();
        Connection conn2 = MySQLConnection.getInstance().getConnection();

        System.out.println(conn1);
        System.out.println(conn2);


    }
}
