package ru.job4j.servlets;

import ru.job4j.logic.RoleService;
import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateController extends HttpServlet {

    private final ValidateService validate = ValidateService.getINSTANCE();
    private final RoleService roleService = RoleService.getINSTANCE();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User editingUser = validate.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("user", editingUser);
        req.setAttribute("userRole", editingUser.getRole());
        User curUser = validate.getCurrentUser(req);
        if (roleService.getUserRole(curUser).getId() == RoleService.ADMIN_ROLE_ID) {
            req.setAttribute("roles", roleService.getRoles());
        }
        req.getRequestDispatcher("/WEB-INF/views/UserUpdateView.jsp").forward(req, resp);
    }
}
