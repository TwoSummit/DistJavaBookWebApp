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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <title>Author List</title>
    </head>
    <body>
        <div class="container">
            
            <h1>Add Author</h1>
            <div class="col-sm-12">
                <form name="addAuthor" 
                      method="get">

                    <div class="row">
                        <div class="form-group ">
                            <label>Author Name
                                <input type="text" placeholder="example: Bob Smith" name="nameValue" class="form-control" />
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group">
                             <input type="hidden" name="action" value="createOne"/>
                            <button type="submit" class="button">Add Author</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </body>
</html>
