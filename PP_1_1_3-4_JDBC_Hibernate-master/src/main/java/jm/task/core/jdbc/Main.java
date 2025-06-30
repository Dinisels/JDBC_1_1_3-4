package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable(); // Создание таблицы User(ов)

        //Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем — name добавлен в базу данных)

        userService.saveUser("Ivan", "Ivanovich", (byte) 12);
        userService.saveUser("Petr", "Petrovich", (byte) 24);
        userService.saveUser("Senya", "Senovalov", (byte) 36);
        userService.saveUser("Miha", "Mihanov", (byte) 32);
        userService.saveUser("Rebs", "Rebsovich", (byte) 89);


        // Удаление пользователя по id, в здании не сказано чтобы был в мейне
        //userService.removeUserById(2);

        // Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        System.out.println(userService.getAllUsers());

        //Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Получение всех User из базы и вывод в консоль - оставил чтобы было видно что очистилась таблица
        System.out.println(userService.getAllUsers());

        //Удаление таблицы
        userService.dropUsersTable();

    }
}
