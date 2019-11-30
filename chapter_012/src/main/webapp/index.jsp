<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td><a href="<%= request.getContextPath()%>/create/">Добавить нового пользователя</a></td>
    </tr>
    <% for (User user: ValidateService.getINSTANCE().findAll()) { %>
    <tr>
        <td>Login:<%=user.getLogin()%></td>
        <td>Name:<%=user.getName()%></td>
        <td>Email:<%=user.getEmail()%></td>
        <td><a href="<%= request.getContextPath()%>/update/?id=<%=user.getId()%>">Update</a></td>
        <td>
            <form method="post" action="<%= request.getContextPath()%>/list">
                <input type="text" name="action" value="delete" hidden>
                <input type="text" name="id" value="<%= user.getId()%>" hidden>
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
