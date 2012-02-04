<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <stripes:useActionBean binding="/home" id="bean"/>
        <c:if test="${empty bean.loginInfo.user}">
            <stripes:link href="/home/login">login</stripes:link>
            <stripes:form beanclass="${home.class}">
                <stripes:errors/>
                User Name: <stripes:text name="loginRequest.userName"/>
                <stripes:submit name="login"/>
            </stripes:form>
        </c:if>
        <c:if test="${not empty bean.loginInfo.user}">
            
            <h1>Hello World! ${home.loginInfo.user.firstName}</h1>
            ${currentUser.firstName}
            <ul>
                <c:forEach items="${bean.loginInfo.user.aliases}" var="alias">
                    <li>${alias.alias}</li>
                </c:forEach>
            </ul>
        </c:if>
    </body>
</html>
