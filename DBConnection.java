package librarymanagementapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db", // database name
                "root", // MySQL username
                "root"      // empty password
            );
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}


