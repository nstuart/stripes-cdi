<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML >

<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<stripes:layout-render name="${defaultLayout}">
    <stripes:layout-component name="pageContent">
        <div class="row">
            <div class="span12">
                <h1>System Users</h1>
            </div>
        </div>
        <div class="row">
            <div class="span4">
                <c:if test="${not empty message}">
                    <div class="alert alert-success">
                        <a class="close" data-dismiss="alert">×</a>
                        ${message}
                    </div>
                </c:if>
                <p>
                    Current users of the system are on the right. All users shown
                    are currently active.
                </p>
                <p>
                    <stripes:url beanclass="${userManagement.class}" event="new" var="createUrl"/>
                    <button class="btn btn-primary" onclick="window.location.href = '${createUrl}'">Create User</button>
                </p>
            </div>
            <div class="span8">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>User Name</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${userManagement.users}" var="user">
                            <tr>
                                <td><c:out value="${user.userName}"/></td>
                                <td><c:out value="${user.firstName}"/></td>
                                <td><c:out value="${user.lastName}"/></td>
                                <td><c:out value="${user.email}"/></td>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-primary" href="#">
                                            <i class="icon-user icon-white"></i> Action</a>
                                        <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href="#">
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <stripes:link beanclass="${userManagement.class}" event="edit">
                                                    <stripes:param name="user" value="${user}"/>
                                                    <i class="icon-pencil"></i> Edit
                                                </stripes:link>
                                            </li>
                                            <li><a href="#"><i class="icon-trash"></i> Delete</a></li>
                                            <li><a href="#"><i class="icon-ban-circle"></i> Ban</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#"><i class="i"></i> Make admin</a></li>
                                        </ul>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </stripes:layout-component>
</stripes:layout-render>
