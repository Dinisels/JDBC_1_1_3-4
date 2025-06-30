package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoJDBCImpl();



    @Override
    public void createUsersTable() {

        if (!userDao.tableExists("users")) {
            userDao.createUsersTable();
            System.out.println("Таблица users создана");
        } else {
            System.out.println("Таблица users уже существует");
        }

    }

    @Override
    public void dropUsersTable() {

        if (!userDao.tableExists("users")) {
            System.out.println("Таблица users не найдена");
        } else {
            userDao.dropUsersTable();
            System.out.println("Таблица users удалена");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        if (!userDao.tableExists("users")) {
            System.out.println("Таблица users не найдена");
        } else {
            userDao.saveUser(name, lastName, age);
            System.out.println("Пользователь с именем " + name + " " + lastName + " добавлен");
        }

    }
    @Override
    public void removeUserById(long id) {

        if (!userDao.tableExists("users")) {
            System.out.println("Таблица users не найдена");
        } else {
            userDao.removeUserById(id);
            System.out.println("Пользователь с id " + id + " удален или отсутствовал ранее");
        }

    }
    @Override
    public List<User> getAllUsers() {

        return userDao.getAllUsers();

    }
    @Override
    public void cleanUsersTable() {
        if (!userDao.tableExists("users")) {
            System.out.println("Таблица users не найдена");
        } else {
            userDao.cleanUsersTable();
            System.out.println("Таблица users - очищена от всех записей");
        }

    }


}
