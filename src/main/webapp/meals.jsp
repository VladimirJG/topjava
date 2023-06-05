<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 02.06.2023
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse; /* Убираем двойные границы между ячейками */
            border: 2px solid #000; /* Рамка вокруг таблицы */
        }

        td, th {
            vertical-align: top; /* Выравнивание по верхнему краю */
            padding: 5px; /* Поля вокруг содержимого ячеек */
            border: 2px solid #000; /* Рамка вокруг ячеек */
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${listOfMeals}" var="food">
        <tr style="color:${food.excess ? 'red' : 'green'}">
            <td><fmt:parseDate value="${ food.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
            <td><c:out value="${food.description}"/></td>
            <td><c:out value="${food.calories}"/></td>
            <td><a href="<c:out value="${food.dateTime }"/>">Update</a></td>
            <td><a href="<c:out value="${food.dateTime }"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
