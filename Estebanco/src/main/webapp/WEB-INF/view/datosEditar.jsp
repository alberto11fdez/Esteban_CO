<!-- JOSÉ -> 100% -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datos</title>
</head>
<body>
<h1>Datos del cliente:</h1>
<form:form action="/persona/guardarEditar" modelAttribute="persona" method="post">

    Dni:<form:input path="dni" size="9" maxlength="9"/><br/>
    Nombre:<form:input path="nombre" size="40" maxlength="40"/><br/>
    Primer apellido: <form:input path="apellido1" size="40" maxlength="50"/><br/>
    Segundo apellido: <form:input path="apellido2" size="40" maxlength="50"/><br/>
    Correo: <form:input path="correo" size="40" maxlength="40"/><br/>
    Dirección: <form:input path="direccion" size="40" maxlength="40"/><br/>
    Teléfono: <form:input path="telefono" size="20" maxlength="20"/><br/>
    Nombre de usuario: <form:input path="usuario" size="20" maxlength="20"/><br/>
    Contraseña: <form:input path="contraseña" size="30" maxlength="30"/><br/>
    <form:hidden path="estado"></form:hidden>
    <form:hidden path="id"></form:hidden>

    <form:button>Guardar</form:button>
</form:form>
</body>
</html>