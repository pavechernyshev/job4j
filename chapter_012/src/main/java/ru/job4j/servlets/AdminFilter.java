package ru.job4j.servlets;

import ru.job4j.logic.RoleService;
import ru.job4j.logic.ValidateService;
import ru.job4j.models.Role;
import ru.job4j.models.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    private final RoleService roleService = RoleService.getINSTANCE();
    private final ValidateService validateService = ValidateService.getINSTANCE();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            User user = validateService.getCurrentUser(httpServletRequest);
            Role role = roleService.getUserRole(user);
            if (role.getId() != RoleService.ADMIN_ROLE_ID) {
                if (user == null) {
                    ((HttpServletResponse) response).sendRedirect(String.format("%s/signin", httpServletRequest.getContextPath()));
                    return;
                }
            }
        } catch (Exception ex) {
            ((HttpServletResponse) response).sendRedirect(String.format("%s/signin", httpServletRequest.getContextPath()));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
