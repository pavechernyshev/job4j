package ru.job4j.logic;

import ru.job4j.models.User;
import ru.job4j.persistent.MemoryStore;
import ru.job4j.persistent.Store;

import java.util.List;

/**
 * singleton from https://habr.com/ru/post/129494/
 */
public class ValidateService {

    public final static ValidateService INSTANCE = new ValidateService();

    private ValidateService() {

    }

    private final Store memoryStore = MemoryStore.INSTANCE;

    private void checkUserId(int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id user is not correct");
        }
    }

    public boolean add(User user) {
        if (user.getName().length() == 0) {
            throw new IllegalArgumentException("User name is not correct");
        }
        return this.memoryStore.add(user);
    }

    public boolean update(User user) {
        checkUserId(user.getId());
        return this.memoryStore.update(user);
    }

    public List<User> findAll() {
        return this.memoryStore.findAll();
    }

    public User findById(int id) {
        this.checkUserId(id);
        return this.memoryStore.findById(id);
    }

    public boolean delete(User user) {
        this.checkUserId(user.getId());
        return this.memoryStore.delete(user.getId());
    }
}
