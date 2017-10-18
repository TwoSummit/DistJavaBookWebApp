<%-- 
    Document   : AuthorList
    Created on : Sep 19, 2017, 8:35:59 PM
    Author     : cssco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <title>Author List</title>
    </head>
    <body>
        
        <div class="container">
            <h1>Author List</h1>
        </div>
        
        <div class="container">
            <c:forEach var="a" items="${authorList}">
                <div class="row">
                    <div class="col-sm-4"><strong>ID:</strong> ${a.authorId}</div>
                    <div class="col-sm-4"><strong>Name:</strong> ${a.authorName}</div>
                    <div class="col-sm-4"><strong>Date Added:</strong> <fmt:formatDate pattern="yyyy-MM-dd" value="${a.dateAdded}" /></div>
                </div>
            </c:forEach>
        </div>
        
    </body>
</html>
