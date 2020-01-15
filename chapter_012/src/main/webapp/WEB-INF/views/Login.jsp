<%--
  Created by IntelliJ IDEA.
  User: pavel_chernysev
  Date: 13.01.2020
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>login</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form method="post" action="${pageContext.servletContext.contextPath}/signin" >
    <input type="text" name="login" placeholder="Login">
    <input type="password" name="password" placeholder="Password">
    <button type="submit">Signin</button>
</form>
</body>
</html>
