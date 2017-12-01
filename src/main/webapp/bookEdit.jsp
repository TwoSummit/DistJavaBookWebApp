<%-- 
    Document   : bookEdit
    Created on : Nov 16, 2017, 9:02:00 PM
    Author     : cssco
--%>

<%@page import="java.util.List"%>
<%@page import="edu.wctc.distjava.jgl.bookwebapp.model.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>        
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Book</title>
    </head>
    <body>
            <h1>Edit Book</h1>
            <div class="col-sm-12">
                <form name="edit" 
                      method="get">

                    <div class="row">
                        <div class="form-group ">
                            <label>Title
                                <input type="text" placeholder="example: Advanced Java EE" name="titleValue" class="form-control" value="${bookObject.title}"/>
                            </label>
                        </div>
                        <div class="form-group ">
                            <label>ISBN
                                <input type="text" placeholder="example: 978-3-16-148410-0" name="isbnValue" class="form-control" value="${bookObject.isbn}"/>
                            </label>
                        </div>
                        <select name="authorId">    
                            <c:forEach var="a" items="${authorList}">
                                <option value="${a.authorId}">${a.authorName}</option>
                            </c:forEach>
                        </select>    
                    </div>

                    <div class="row">
                        <div class="form-group">
                            <input type="hidden" name="pkValue" value="${bookObject.bookId}"/>
                            <!-- I had this before moving the author id into a dropdown
                            <input type="hidden" name="authorId" value="${bookObject.authorObject.authorId}"/> -->
                            <input type="hidden" name="action" value="editOne"/>
                            <button type="submit" class="button">Update Book</button>
                        </div>
                    </div>

                </form>
            </div>
    </body>
</html>
