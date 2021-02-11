<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- start the middle column -->

<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>

    <h1>404 Error</h1>
    <p>The server was not able to find the file you requested.</p>
    <p>To continue, click the Back button or select a link from this page.</p>

    <h2>Details</h2>
    <p>Requested URI: ${pageContext.errorData.requestURI}</p>
    <p>Requested URI: ${pageContext.errorData.servletName}</p>
    <p>Requested URI: ${pageContext.errorData.statusCode}</p>
    <p>Requested URI: ${pageContext.errorData.throwable.message}</p>
        <h2>Details</h2>
    <code>
    ${pageContext.errorData.servletName} threw a <br>
    ${pageContext.exception}<br>
    <c:forEach var="line" items="${pageContext.errorData.throwable.stackTrace}">
        &nbsp;&nbsp;&nbsp;&nbsp;at ${line}<br>
    </c:forEach>
    </code>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />