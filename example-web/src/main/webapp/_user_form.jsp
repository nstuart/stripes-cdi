<%-- 
    Document   : _user_form
    Created on : Feb 5, 2012, 10:59:35 AM
    Author     : nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:form partial="true" beanclass="${userManagement.class}">
    <stripes:hidden name="id" />
    User Name: <stripes:text name="user.userName"/> <br/>
    EMail: <stripes:text name="user.email"/> <br/>
    Password: <stripes:password name="user.password"/> <br/>
</stripes:form>