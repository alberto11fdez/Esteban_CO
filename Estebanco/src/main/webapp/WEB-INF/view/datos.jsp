<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datos</title>
</head>
<body>
<h1>Datos del cliente:</h1>
<form:form action="/persona/guardar" modelAttribute="persona" method="post">
    <form:hidden path="id"/>
    Dni:<form:input path="dni" size="9" maxlength="9"/><br/>
    Nombre:<form:input path="nombre" size="40" maxlength="40"/><br/>
</form:form>
</body>
</html>