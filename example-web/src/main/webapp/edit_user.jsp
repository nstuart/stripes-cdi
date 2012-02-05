<%-- 
    Document   : edit_user
    Created on : Feb 5, 2012, 9:39:16 AM
    Author     : nick
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <stripes:form beanclass="${userManagement.class}">
            <jsp:include page="_user_form.jsp"/>
            <stripes:submit name="update"/>
        </stripes:form>
    </body>
</html>
