<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />

<script>
    var gameId = ${gameId};
</script>

<div class="board"></div>
<script src="/resources/js/chess.js"></script>

<jsp:include page="./include/footer.jsp" />