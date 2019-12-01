package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UsersController extends HttpServlet {

    private final Map<String, Function<User, Boolean>> dispatch = new HashMap<>();
    private final ValidateService validateService = ValidateService.getINSTANCE();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", validateService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

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
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }

    private void initDispatch() {
        this.load("add", validateService::add);
        this.load("update", validateService::update);
        this.load("delete", validateService::delete);
    }

    private void executeDispatch(String action, User user) {
        Function<User, Boolean> func = this.dispatch.get(action);
        if (func != null) {
            func.apply(user);
        }
    }

    public void load(String action, Function<User, Boolean> handle) {
        dispatch.put(action, handle);
    }
}
