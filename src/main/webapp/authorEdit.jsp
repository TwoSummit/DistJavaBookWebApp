<%-- 
    Document   : authorEdit
    Created on : Nov 16, 2017, 9:02:00 PM
    Author     : cssco
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
        <title>Edit Author</title>
    </head>
    <body>
            <h1>Edit Author</h1>
            <div class="col-sm-12">
                <form name="edit" 
                      method="get">

                    <div class="row">
                        <div class="form-group ">
                            <label>Author Name
                                <input type="text" placeholder="example: Bob Smith" name="nameValue" class="form-control" value="${authorObject.authorName}"/>
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group">
                            <input type="hidden" name="pkValue" value="${authorObject.authorId}"/>
                            <input type="hidden" name="action" value="editOne"/>
                            <button type="submit" class="button">Update Author</button>
                        </div>
                    </div>

                </form>
            </div>
    </body>
</html>
