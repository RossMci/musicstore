<jsp:include page="/includes/footer.jsp" />
<jsp:include page="/includes/header.jsp" />
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section id="admin">

    <h1>Register Administrator</h1>
    <p><i>${message}</i></p>
    <form action="<c:url value='/adminController/RegisterAdmin'/>" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Username:</label>
        <input type="text" name="Username"><br>
        <label class="pad_top">Password:</label>
        <input type="password" name="Password"><br>
         <label class="pad_top">Role name</label>
        <input type="Rolename" name="Rolename"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Add Admin" class="margin_left">
    </form>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />