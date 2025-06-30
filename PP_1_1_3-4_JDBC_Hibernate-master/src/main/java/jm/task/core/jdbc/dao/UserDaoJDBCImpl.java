package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                lastName VARCHAR(50) NOT NULL,
                age TINYINT UNSIGNED
            )
            """;

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        String sql = """
                DROP TABLE users;
                """;

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?"
        ))

        {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {

        String sql = """
                SELECT * FROM users;
                """;
        List<User> usersList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql))
                {
                    while (resultSet.next()){
                        User user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("lastName"));
                        user.setAge(resultSet.getByte("age"));
                        usersList.add(user);
                    }
                    return usersList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {

        try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement())
        {

            statement.executeUpdate("""
                    TRUNCATE TABLE users;
                    """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean tableExists(String tableName) {
        String sql = "SHOW TABLES LIKE ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке существования таблицы:");
            e.printStackTrace();
            return false;
        }
    }
}
