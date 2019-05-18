<%@ page pageEncoding="utf-8" %>
<jsp:include page="./include/header.jsp" />

<button type="button" class="FindGame"  >Поиск партнеров</button>

<script>
    $('.FindGame').click(function() {
        $.get('/lobby/findgame');
    });

    setInterval(function () {
        $.get('/lobby/hasCurrentGame', function (result) {
            if (result){
                window.location='/chess/' + result;
            }
        });
    }, 1000)
</script>

<jsp:include page="./include/footer.jsp" />
