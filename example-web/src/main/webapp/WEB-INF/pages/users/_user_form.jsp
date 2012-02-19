<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:form partial="true" beanclass="${userManagement.class}">
    <s:hidden name="user" />
    <div class="control-group">
        <s:label for="user.userName"/>
        <s:text name="user.userName"/>
        <s:label for="user.firstName"/>
        <s:text name="user.firstName"/>
        <s:label for="user.lastName"/>
        <s:text name="user.lastName"/>
        <s:label for="user.email"/>
        <s:text name="user.email"/>
        <s:label for="user.password"/>
        <s:password name="user.password"/>
        <s:label for="user.passwordConfirmation"/>
        <s:password name="user.passwordConfirmation"/>
    </div>
</s:form>