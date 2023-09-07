<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse;
            border: 2px solid #000;
        }

        td, th {
            vertical-align: top;
            padding: 5px;
            border: 2px solid #000;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="<c:out value="${meal.dateTime }"/>">Add Meal</a></h3>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${listOfMeals}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${ parsedDateTime }"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="<c:out value="${meal.dateTime }"/>">Update</a></td>
            <td><a href="<c:out value="${meal.dateTime }"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
