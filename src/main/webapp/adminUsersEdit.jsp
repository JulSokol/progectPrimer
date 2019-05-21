<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<form>
    ${user.id}
    ${user.username}
    ${user.nik}
</form>
<div  class="container">

<form:form method="POST" modelAttribute="user" class="form-signin" action="/admin/users/${user.id}">
    <spring:bind path="nik">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="nik" class="form-control" placeholder="Nik"
                        autofocus="true"></form:input>
            <form:errors path="nik"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="country">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="country" class="form-control" placeholder="Country"
                        autofocus="true"></form:input>
            <form:errors path="country"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="city">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="city" class="form-control" placeholder="City"
                        autofocus="true"></form:input>
            <form:errors path="city"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="email">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="email" path="email" class="form-control" placeholder="email"
                        autofocus="true"></form:input>
            <form:errors path="email"></form:errors>
        </div>
    </spring:bind>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
</form:form>

</div>

<jsp:include page="./include/footer.jsp" />
