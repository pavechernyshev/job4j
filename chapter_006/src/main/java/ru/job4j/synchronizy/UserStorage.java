package ru.job4j.synchronizy;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;
import java.util.HashSet;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final HashSet<User> users = new HashSet<>();

    public synchronized boolean add(User user) {
        return users.add(user);
    }

    public synchronized boolean update(User user) {
        boolean res = false;
        User oldUser = getById(user.getId());
        if (oldUser != null) {
            res = users.remove(oldUser) && users.add(user);
        }
        return res;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    public void transfer(int from, int to, int amount) throws IllegalArgumentException {
        User userFrom = getById(from);
        User userTo = getById(to);
        userFrom.takeAmount(amount);
        userTo.addAmount(amount);
    }

    private synchronized User getById(int id) {
        User res = null;
        for (User user: users) {
            if (user.getId() == id) {
                res = user;
            }
        }
        return res;
    }
}
