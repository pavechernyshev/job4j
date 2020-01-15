package ru.job4j.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.log.UsageLog4j2;
import ru.job4j.models.Role;
import ru.job4j.persistent.DbStore;
import ru.job4j.models.User;
import ru.job4j.persistent.Store;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * singleton from https://habr.com/ru/post/129494/
 */
public class ValidateService {

    private final static ValidateService INSTANCE = new ValidateService();
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());

    private ValidateService() {

    }

    public static ValidateService getINSTANCE() {
        return INSTANCE;
    }

    private final Store store = DbStore.getINSTANCE();

    private void checkUserId(int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id user is not correct");
        }
    }

    public boolean add(User user) {
        if (user.getName().length() == 0) {
            throw new IllegalArgumentException("User name is not correct");
        }
        return this.store.add(user);
    }

    public boolean update(User user) {
        checkUserId(user.getId());
        return this.store.update(user);
    }

    public List<User> findAll() {
        return this.store.findAll();
    }

    public User findById(int id) {
        this.checkUserId(id);
        return this.store.findById(id);
    }
    public User findByLogin(String login) {
        return this.store.findByLogin(login);
    }

    public boolean delete(User user) {
        this.checkUserId(user.getId());
        return this.store.delete(user.getId());
    }

    public boolean isCredentional(String login, String password) {
        return this.store.isCredentional(login, password);
    }

    public User getCurrentUser(HttpServletRequest request) {
        User user = null;
        try {
            HttpSession session = request.getSession();
            String userLogin = session.getAttribute("login").toString();
            user = this.findByLogin(userLogin);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return user;
    }
}
