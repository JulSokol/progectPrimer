<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />


<table style="width:100%" >
    <tr>
        <th colspan="4">Edit users</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.nik}</td>
            <td>
                <a href="/admin/users/${user.id}">Edit</a>
                <form method="post" action="/admin/users/${user.id}">
                    <input type="hidden" name="_method" value="DELETE" />
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="./include/footer.jsp" />
