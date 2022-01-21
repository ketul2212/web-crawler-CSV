<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>

<body>
    <form action="/serach" method="post">
        <input type="text" name="searchTerm" placeholder="Search here...." /><br/>
        <input type="submit" name="submit"/>
    </form>

    <table id="example" class="display" style="width:100%">
        <thead>
            <tr>
                <th>links</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="link" items="${links}" varStatus="status">
                <tr>
                    <td><a href="${link}">${link}</a></td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th>links</th>
            </tr>
        </tfoot>
    </table>

    <c:if test="${!empty links}">
    <a href="/download" >download CSV</a>
    </c:if>
   </body>

</html>