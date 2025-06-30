package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {


    static String url = "jdbc:mysql://localhost:3306/jdbc_1_1_3";
    static String username = "root";
    static String password = "Rjn2001njirfbdfyz";


    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
