<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>

</head>
<body>
<h1>Micro Messenger App</h1>
<div class="container">
    <div class="messages">
        <div class="message">Test Message</div>
    </div>
    <div class="input">
        <input type="text" placeholder="Type message here!"/>
        <div class="emoji-btn open">:]
            <div class="emoji-popup"><div class="emoji-wrapper"></div></div>
        </div>
        <div class="btn">Send</div>
    </div>
</div>
</body>


</html>