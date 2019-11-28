package ru.job4j.servlets;

import ru.job4j.logic.ValidateService;
import ru.job4j.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String strId = req.getParameter("id");
        if (strId == null) {
            writer.append("failure");
        } else {
            try {
                User user = ValidateService.instance.findById(Integer.valueOf(strId));
                writer.append("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <title>Title</title>" +
                        "</head>" +
                        "<body>" +
                        "<form method=\"post\" action=\"" + req.getContextPath() + "/list\">" +
                        "    <input type=\"text\" name=\"action\" value='update' hidden>" +
                        "    <input type=\"text\" name=\"id\" value='" + strId + "' hidden>" +
                        "    <input type=\"text\" name=\"login\" placeholder=\"Login\" value='" + user.getLogin() + "'>" +
                        "    <input type=\"text\" name=\"name\" placeholder=\"Name\" value='" + user.getName() + "'>" +
                        "    <input type=\"email\" name=\"email\" placeholder=\"Email\" value='" + user.getEmail() + "'>" +
                        "    <button type=\"submit\">Update</button>" +
                        "</form>" +
                        "</body>" +
                        "</html>");
            } catch (Exception e) {
                writer.append("failure. Have errors");
            }
        }
        writer.flush();
    }
}
