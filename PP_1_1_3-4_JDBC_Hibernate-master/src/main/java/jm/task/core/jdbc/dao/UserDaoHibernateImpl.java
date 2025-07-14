package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {


        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {

                String sql = """
                        CREATE TABLE IF NOT EXISTS users (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            lastName VARCHAR(50) NOT NULL,
                            age TINYINT UNSIGNED
                        )
                        """;

                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();

            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("ошибка при создании");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS users;";

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Таблица users была удалена");
            } catch (Exception e) {
                transaction.rollback();

            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении таблицы users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {


        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User(name, lastName, age);
                session.persist(user);
                transaction.commit();
                System.out.println("User с именем " + name + " добавлен в базу данных");
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("ошибка при сохранении");
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {


        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            try {
                User user = (User) session.get(User.class, id);
                if (user != null) {
                    session.remove(user);
                    System.out.println("Пользователь с id " + id + " удален");
                } else {
                    System.out.println("Пользователь с id " + id + " не найден");
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        } catch (Exception e) {
            System.out.println("ошибка при удалении пользователя");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {


        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("DELETE FROM User").executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при очишении");
        }

    }

    @Override
    public boolean tableExists(String tableName) {
        return false;
    }
}
