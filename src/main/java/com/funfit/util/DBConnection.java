package com.funfit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ✅ IMPORTANT: Added allowPublicKeyRetrieval=true (fixes your error)
    // ❗ Make sure database name "funfit_db" is correct
    private static final String URL =
            "jdbc:mysql://localhost:3306/funfit_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // ❗ Replace with your actual MySQL username and password
    private static final String USER = "root";        // <-- put your username
    private static final String PASSWORD = "Punit@002";    // <-- put your password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 8 driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}













//package com.funfit.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/funfit_db?useSSL=false&serverTimezone=UTC";
//    private static final String USER = "your_db_username";
//    private static final String PASSWORD = "your_db_password";
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}
//
