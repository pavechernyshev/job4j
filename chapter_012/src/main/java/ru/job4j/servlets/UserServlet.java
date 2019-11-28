package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UserServlet extends HttpServlet {

    private final Map<String, Function<User, Boolean>> dispatch = new HashMap<>();
    private final ValidateService validateService = ValidateService.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> userList = validateService.findAll();
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<table>");
        for (User user: userList) {
            writer.append("    <tr>" +
                    "        <td>Login:"+user.getLogin()+"</td><td>Name:"+user.getName()+"</td><td>Email:"+user.getEmail()+"</td><td><a href=\"" + req.getContextPath() + "/update?id="+user.getId()+"\">Update</a></td>" +
                    "        <td>" +
                    "            <form method=\"post\" action=\"" + req.getContextPath() + "/list\">" +
                    "                <input type=\"text\" name=\"action\" value=\"delete\" hidden>" +
                    "                <input type=\"text\" name=\"id\" value=\""+user.getId()+"\" hidden>" +
                    "                <button type=\"submit\">Delete</button>" +
                    "            </form>" +
                    "        </td>" +
                    "    </tr>");
        }
        writer.append("</table>");

        writer.flush();
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
        boolean res = this.executeDispatch(req.getParameter("action"), user);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String printRes = res ? "success" : "failure";
        writer.append(printRes);
        writer.flush();
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
