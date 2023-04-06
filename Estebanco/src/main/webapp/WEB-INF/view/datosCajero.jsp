<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 30/03/2023
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datos cajero</title>
</head>
<body>
<h1>Datos del cliente:</h1>
<form:form action="/cajero/guardar" modelAttribute="persona" method="post">
    <form:hidden path="id"/>
    Dni:<form:input path="dni" size="9" maxlength="9"/><br/>
    Nombre:<form:input path="nombre" size="40" maxlength="40"/><br/>
    Primer apellido: <form:input path="apellido1" size="40" maxlength="50"/><br/>
    Segundo apellido: <form:input path="apellido2" size="40" maxlength="50"/><br/>
    Correo: <form:input path="correo" size="40" maxlength="40"/><br/>
    Dirección: <form:input path="direccion" size="40" maxlength="40"/><br/>
    Teléfono: <form:input path="telefono" size="20" maxlength="20"/><br/>
    Nombre de usuario: <form:input path="usuario" size="20" maxlength="20"/><br/>
    Contraseña: <form:input path="contraseña" size="30" maxlength="30"/><br/>
    <form:button>Guardar</form:button>
</form:form>
</body>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</html>
