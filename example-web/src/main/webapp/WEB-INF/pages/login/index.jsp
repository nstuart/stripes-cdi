<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="${defaultLayout}">
    <stripes:layout-component name="pageContent">
        <div class="row">
            <div class="span12">
                <h1>Welcome!</h1>
            </div>
        </div>
        <div class="row">
            <div class="span8">
                <p>
                    Please login before proceeding.
                </p>
            </div>
            <div class="span4">
                <stripes:form beanclass="${login.class}">
                    <stripes:errors/>
                    <div class="control-group">
                        <stripes:label for="loginRequest.userName" />
                        <stripes:text name="loginRequest.userName"/>
                    </div>
                    <div class="control-group">
                        <stripes:label for="loginRequest.password" />
                        <stripes:password name="loginRequest.password"/>
                    </div>
                    <stripes:submit name="login"/>
                </stripes:form>
            </div>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
