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

        Transaction transaction = null;




        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String check = """
            SELECT COUNT(*) 
            FROM information_schema.tables 
            WHERE table_schema = DATABASE() 
            AND table_name = 'users'
            """;

            Number tableCount = (Number) session.createNativeQuery(check).getSingleResult();

            if (tableCount.intValue() == 0){

            String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        lastName VARCHAR(50) NOT NULL,
                        age TINYINT UNSIGNED
                    )
                    """;

            session.createNativeQuery(sql).executeUpdate();
                System.out.println("Таблица users создана");
            }else {
                System.out.println("Таблица users уже существует");
            }

            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        String sql = """
                DROP TABLE users;
                """;

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String check = """
            SELECT COUNT(*) 
            FROM information_schema.tables 
            WHERE table_schema = DATABASE() 
            AND table_name = 'users'
            """;

            Number tableCount = (Number) session.createNativeQuery(check).getSingleResult();

            if (tableCount.intValue() != 0) {

                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Таблица users была удалена");

            }else {
                System.out.println("Таблица users не найдена");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();

            User user = (User) session.get(User.class, id);
            if (user != null){
            session.remove(user);
                System.out.println("Пользователь с id " + id + " удален");
            }else {
                System.out.println("Пользователь с id " + id + " не найден");
            }
            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()){

            return session.createQuery("FROM User", User.class).list();

        }
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction  = null;
        try (Session session = sessionFactory.openSession()){

           transaction = session.beginTransaction();

           session.createQuery("DELETE FROM User").executeUpdate();

           transaction.commit();

        }

    }

    @Override
    public boolean tableExists(String tableName) {
        return false;
    }
}
