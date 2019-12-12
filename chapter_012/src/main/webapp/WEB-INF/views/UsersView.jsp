<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td><a href="${pageContext.servletContext.contextPath}/create">Добавить нового пользователя</a></td>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td>Login:<c:out value="${user.login}"></c:out></td>
        <td>Name:<c:out value="${user.name}"></c:out></td>
        <td>Email:<c:out value="${user.email}"></c:out></td>
        <td><a href="${pageContext.servletContext.contextPath}/download?name=${user.photoId}">Download</a></td>
        <td>
            <img src="${pageContext.servletContext.contextPath}/download?name=${user.photoId}" width="100px" height="100px"/>
        </td>
        <td><a href="<%= request.getContextPath()%>/update?id=<c:out value="${user.id}"></c:out>">Update</a></td>
        <td>
            <form method="post" action="${pageContext.servletContext.contextPath}/list" enctype="multipart/form-data">
                <input type="text" name="action" value="delete" hidden>
                <input type="text" name="id" value="<c:out value="${user.id}"></c:out>" hidden>
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
