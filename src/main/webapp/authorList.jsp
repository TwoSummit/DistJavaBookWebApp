<%-- 
    Document   : authorList
    Created on : Sep 19, 2017, 8:35:54 PM
    Author     : jlombardo
--%>

<%@page import="java.util.List"%>
<%@page import="edu.wctc.distjava.jgl.bookwebapp.model.Author"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <table border="1" class="table table-hover">
                <c:forEach var="a" items="${authorList}">
                    <tr>
                        <td>${a.authorId}</td>
                        <td>${a.authorName}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${a.dateAdded}" /></td>
                        <td><a href="authorController?action=editAuthorsDirect&pkValue=${a.authorId}">Edit</a></td>
                        <td><a href="authorController?action=deleteOne&pkValue=${a.authorId}" class="btn btn-info">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <a href="authorController?action=createOneDirect" class="btn btn-info">
                Add Author
            </a>
        </div>
        
    </body>
</html>