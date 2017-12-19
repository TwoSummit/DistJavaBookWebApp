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
        <title>Book List</title>
    </head>
    <body>
        <div class="container">
            <h1>Book List</h1>
            <table border="1" class="table table-hover">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>ISBN</td>
                        <td>Title</td>
                        <td>Author</td>
                        <td>Edit</td>
                        <td>Delete</td>
                    </tr>
                </thead>
                <c:forEach var="b" items="${bookList}">
                    <tr>
                        <td>${b.bookId}</td>
                        <td>${b.isbn}</td>
                        <td>${b.title}</td>
                        <td>${b.authorObject.authorName}</td>
                        <td><a href="books?action=editBooksDirect&pkValue=${b.bookId}">Edit</a></td>
                        <td><a href="books?action=deleteOne&pkValue=${b.bookId}" class="btn btn-info">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <a href="books?action=createOneDirect" class="btn btn-info">
                Add Book
            </a>
        </div>
        
    </body>
</html>