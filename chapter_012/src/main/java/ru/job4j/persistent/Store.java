package ru.job4j.persistent;

import ru.job4j.models.User;

import java.util.List;

public interface Store {
    boolean add(User user);
    boolean update(User user);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
    User findByLogin(String login);
    boolean isCredentional(String login, String password);
}
