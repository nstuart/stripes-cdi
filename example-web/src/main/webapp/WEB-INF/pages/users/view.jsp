<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="${defaultLayout}">
    <stripes:layout-component name="pageContent">
        <div class="row">
            <div class="span12">
                <h1>Viewing User ${userManagement.user.userName}!</h1>
                ${userManagement.user.firstName} ${userManagement.user.lastName}
            </div>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
