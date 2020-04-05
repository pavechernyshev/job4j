package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SigninController extends HttpServlet {

    private final ValidateService validateService = ValidateService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        synchronized (session) {
            if (session.getAttribute("login") == null) {
                req.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(req, resp);
            } else {
                req.setAttribute("users", this.validateService.findAll());
                req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (validateService.isCredentional(login, password)) {
            HttpSession session = req.getSession();
            synchronized (session) {
                session.setAttribute("login", login);
            }
            resp.sendRedirect(String.format("%s/list", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credentional invalid");
            doGet(req, resp);
        }
    }
}
