package ru.job4j.synchronizy;

import java.util.HashSet;

public class UserStorage {

    private final HashSet<User> users = new HashSet<>();

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean update(User user) {
        boolean res = false;
        User oldUser = getById(user.getId());
        if (oldUser != null) {
            res = users.remove(oldUser) && users.add(user);
        }
        return res;
    }

    public boolean delete(User user) {
        return users.remove(user);
    }

    public void transfer(int from, int to, int amount) throws IllegalArgumentException {
        User userFrom = getById(from);
        User userTo = getById(to);
        userFrom.takeAmount(amount);
        userTo.addAmount(amount);
    }

    private User getById(int id) {
        User res = null;
        for (User user: users) {
            if (user.getId() == id) {
                res = user;
            }
        }
        return res;
    }
}
