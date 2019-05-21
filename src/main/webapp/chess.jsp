<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />

<script>
    var gameId = ${gameId};
</script>

<div style="float: left;" class="board"></div>
<script src="/resources/js/chess.js"></script>

<div style="float: right;">
    <jsp:include page="chat.jsp"/>
</div>

<jsp:include page="./include/footer.jsp" />
