<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <% User user = ValidateService.getINSTANCE().findById(Integer.parseInt(request.getParameter("id")));%>
    <form method="post" action="<%= request.getContextPath()%>/list">
        <input type="text" name="action" value='update' hidden>
        <input type="text" name="id" value='<%= user.getId()%>' hidden>
        <input type="text" name="login" placeholder="Login" value='<%= user.getLogin() %>'>
        <input type="text" name="name" placeholder="Name" value='<%= user.getName() %>'>
        <input type="email" name="email" placeholder="Email" value='<%= user.getEmail() %>'>
        <button type="submit">Update</button>
    </form>
</table>
</body>
</html>
