package ru.job4j.persistent;

import ru.job4j.models.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore implements Store {

    private static final MemoryStore INSTANCE = new MemoryStore();
    private int idCount = 1;

    private MemoryStore() {

    }

    public static MemoryStore getINSTANCE() {
        return INSTANCE;
    }

    private final Map<Integer, User> userList = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean add(User user) {
        int id = this.idCount++;
        user.setId(id);
        userList.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean update(User user) {
        this.userList.replace(user.getId(), user);
        return true;
    }

    @Override
    public boolean delete(int id) {
        userList.remove(id);
        return true;
    }

    @Override
    public List<User> findAll() {
        return new LinkedList<>(userList.values());
    }

    @Override
    public User findById(int id) {
        return this.userList.get(id);
    }
}
