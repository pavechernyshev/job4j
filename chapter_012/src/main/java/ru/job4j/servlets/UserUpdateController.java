package ru.job4j.servlets;

import ru.job4j.logic.RoleService;
import ru.job4j.logic.SessionHelper;
import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateController extends HttpServlet {

    private final Store validate = ValidateService.getINSTANCE();
    private final RoleService roleService = RoleService.getINSTANCE();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User editingUser = validate.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("user", editingUser);
        req.setAttribute("userRole", editingUser.getRole());
        SessionHelper sessionHelper = new SessionHelper();
        User curUser = sessionHelper.getCurrentUser(req, validate);
        if (roleService.getUserRole(curUser).getId() == RoleService.ADMIN_ROLE_ID) {
            req.setAttribute("roles", roleService.getRoles());
        }
        req.getRequestDispatcher("/WEB-INF/views/UserUpdateView.jsp").forward(req, resp);
    }
}
