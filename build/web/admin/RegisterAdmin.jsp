<jsp:include page="/includes/footer.jsp" />
<jsp:include page="/includes/header.jsp" />
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section id="admin">

    <h1>Log In</h1>
    <p><i>${message}</i></p>
    <form action="check" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Username:</label>
        <input type="text" name="username"><br>
        <label class="pad_top">Password:</label>
        <input type="password" name="password"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Log In" class="margin_left">
    </form>
    <h2>Password info</h2>
    <p>Hash:<br>${hashedPassword}</p>
    <p>Salt:<br>${salt}</p>
    <p>Salted Hash:<br>${saltedAndHashedPassword}</p>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />