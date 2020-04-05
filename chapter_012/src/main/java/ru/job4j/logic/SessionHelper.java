package ru.job4j.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.log.UsageLog4j2;
import ru.job4j.models.User;
import ru.job4j.persistent.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());

    public User getCurrentUser(HttpServletRequest request, Store store) {
        User user = null;
        try {
            HttpSession session = request.getSession();
            String userLogin = session.getAttribute("login").toString();
            user = store.findByLogin(userLogin);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return user;
    }
}
