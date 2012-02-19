<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="${defaultLayout}">
    <stripes:layout-component name="pageContent">
        <div class="row">
            <div class="span12">
                <h1>Editing User</h1>
            </div>
        </div>
        <div class="row">
            <stripes:form beanclass="${userManagement.class}">
                <div class="span3">
                    <jsp:include page="_user_form.jsp"/>
                    <stripes:submit name="update" value="Update" class="btn-primary"/>
                    <stripes:submit name="cancel" value="Cancel" class="btn"/>
                </div>
                <div class="span3">
                    <stripes:errors/>
                </div>
            </stripes:form>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
