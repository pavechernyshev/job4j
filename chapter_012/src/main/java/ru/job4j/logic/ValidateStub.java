package ru.job4j.logic;
import ru.job4j.models.User;
import ru.job4j.persistent.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateStub implements Store {
    private final Map<Integer, User> store = new HashMap<>();
    private int ids = 0;

    @Override
    public boolean add(User user) {
        user.setId(this.ids++);
        this.store.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(this.store.values());
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public boolean isCredentional(String login, String password) {
        return false;
    }

}