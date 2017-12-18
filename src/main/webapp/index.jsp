<%-- 
    Document   : index
    Created on : Sep 19, 2017, 8:01:39 PM
    Author     : jlombardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Web Application</title>
    </head>
    <body>
        <h1>Pick a Task</h1>
        <ul>
            <li><a href="authorController?action=listAll">View all Authors</a></li>
            <li>...more to come</li>
        </ul>
    </body>
    
    <sec:authorize access="hasAnyRole('ROLE_MGR')">
        <h1>For managers only</h1>
    </sec:authorize>
    
    <sec:authorize access="hasAnyRole('ROLE_MGR', 'ROLE_USER')">
        Logged in as: <sec:authentication property="principal.username"></sec:authentication>
        <a href='<%= this.getServletContext().getContextPath() + "/j_spring}_security_"%>'>Log Out</a>
    </sec:authorize>

</html>
