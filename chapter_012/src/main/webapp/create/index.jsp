<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <form method="post" action="<%= request.getContextPath()%>/list">
        <input type="text" name="action" value="add" hidden>
        <input type="text" name="login" placeholder="Login">
        <input type="text" name="name" placeholder="Name">
        <input type="email" name="email" placeholder="Email">
        <button type="submit">Create</button>
    </form>
</table>
</body>
</html>
