package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserCreateServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        writer.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>Title</title>" +
                "</head>" +
                "<body>" +
                "<form method=\"post\" action=\"" + req.getContextPath() + "/list\">" +
                "    <input type=\"text\" name=\"action\" value='add' hidden>" +
                "    <input type=\"text\" name=\"login\" placeholder=\"Login\">" +
                "    <input type=\"text\" name=\"name\" placeholder=\"Name\">" +
                "    <input type=\"email\" name=\"email\" placeholder=\"Email\">" +
                "    <button type=\"submit\">Create</button>" +
                "</form>"+
                "</body>" +
                "</html>");
        writer.flush();
    }
}
