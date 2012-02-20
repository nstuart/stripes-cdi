<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<stripes:layout-definition>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>
                <stripes:layout-component name="pageTitle">
                    Stripes Bugger App
                </stripes:layout-component>
            </title>
            <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet"/>
            <link href="${ctx}/assets/css/bootstrap-responsive.css" rel="stylesheet"/>
            <link href="${ctx}/assets/css/styles.css" rel="stylesheet"/>
            <style type="text/css">
                body{
                    padding-top: 60px;
                }
            </style>
        </head>
        <body>
            <div class="navbar navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container">
                        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </a>
                        <a class="brand" href="#">Bugger</a>
                        <div class="nav-collapse">
                            <ul class="nav">
                                <li class="active">
                                    <stripes:link beanclass="com.bittheory.stripes.beans.Home">
                                        Home
                                    </stripes:link>
                                </li>
                                <li>
                                    <stripes:link beanclass="com.bittheory.stripes.beans.UserManagement">
                                        Users
                                    </stripes:link>
                                </li>
                                <li><stripes:link beanclass="com.bittheory.stripes.beans.Logout" event="logout">Logout</stripes:link></li>
                            </ul>
                        </div>
                        <p class="navbar-text pull-right">
                            Logged In: <a href="#">${currentUser.userName}</a>/<a href="#">Logout</a>
                        </p>
                    </div>
                </div>
            </div>

            <div class="container">
                <stripes:layout-component name="pageContent"/>
            </div>
            <script src="${ctx}/assets/js/jquery.js"></script>
            <script src="${ctx}/assets/js/bootstrap.js"></script>
            <script src="${ctx}/assets/js/less-1.2.1.min.js"></script>
        </body>
    </html>
</stripes:layout-definition>