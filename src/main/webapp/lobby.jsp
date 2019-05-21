<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />

<button type="button" class="FindGame"  >Поиск партнеров</button>

<div>
    <c:if test="${admin}">
        ADMIN!!!
        <a href="/admin/users">Управление пользователями</a>
    </c:if>
</div>
<script>
    var gameId = 0;
    $('.FindGame').click(function() {
        $.get('/lobby/findgame');
    });

    setInterval(function () {
        $.get('/lobby/hasCurrentGame', function (result) {
            if (result){
                window.location='/chess/' + result;
            }
        });
    }, 1000);
</script>

<jsp:include page="chat.jsp"/>

<jsp:include page="./include/footer.jsp" />
