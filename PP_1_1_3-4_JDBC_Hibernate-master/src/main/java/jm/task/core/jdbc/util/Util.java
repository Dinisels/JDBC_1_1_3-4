package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jdbc_1_1_3")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "Rjn2001njirfbdfyz")
                .setProperty("hibernate.show_sql", "true");

        return configuration.buildSessionFactory();
    }


}
