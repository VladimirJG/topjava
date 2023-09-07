<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit</h2>
<div>
    <label>DateTime</label>
    <label for="dateTime"></label><input id="dateTime" name="dateTime" type="text" value="${ meal.dateTime }"/>
</div>
<div>
    <label>Description</label>
    <label for="description"></label><input id="description" name="description" type="text"
                                            value="${meal.description}"/>
</div>
<div>
    <label>Calories</label>
    <label for="calories"></label><input id="calories" name="calories" type="text" value="${meal.calories}"/>
</div>
<div>
    <button onclick="window.history.back()" type="button">Cancel</button>
    <button class="green-submit-button" type="submit">Сохранить</button>
</div>

</body>
</html>
