package ru.job4j.servlets;

import ru.job4j.logic.RoleService;
import ru.job4j.models.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCreateController extends HttpServlet {
    private final RoleService roleService = RoleService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Role> roles = roleService.getRoles();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("/WEB-INF/views/UserCreateView.jsp").forward(req, resp);
    }
}
