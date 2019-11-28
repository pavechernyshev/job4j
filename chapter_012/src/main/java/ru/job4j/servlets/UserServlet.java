package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UserServlet extends HttpServlet {

    private final Map<String, Function<User, Boolean>> dispatch = new HashMap<>();
    private final ValidateService validateService = ValidateService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        this.initDispatch();
        String strId = req.getParameter("id") == null ? "0" : req.getParameter("id");
        int id = Integer.parseInt(strId);
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        User user = new User(id, name, login, email);
        this.executeDispatch(req.getParameter("action"), user);
        resp.sendRedirect(String.format("%s/index.jsp", req.getContextPath()));
    }

    private void initDispatch() {
        this.load("add", validateService::add);
        this.load("update", validateService::update);
        this.load("delete", validateService::delete);
    }

    private boolean executeDispatch(String action, User user) {
        boolean res = false;
        Function<User, Boolean> func = this.dispatch.get(action);
        if (func != null) {
            res = func.apply(user);
        }
        return res;
    }

    public void load(String action, Function<User, Boolean> handle) {
        dispatch.put(action, handle);
    }
}
