<!-- ALBERTO -> 100% -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 28/03/2023
  Time: 2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crear Conversación</title>
</head>
<body>
<h1>Crear Conversación</h1>

<form:form action="/asistente/guardar" modelAttribute="conversacionNueva" method="post">
    <form:hidden path="idconversacion"></form:hidden>
    <form:hidden path="estado"></form:hidden>
    <form:hidden path="fechaInicio"></form:hidden>
    <form:hidden path="fechaFin"></form:hidden>
    <form:hidden path="idPersona"></form:hidden>

    Selecciona al asistente que desea que le asista: <form:select path="idAsistente" items="${asistentes}" itemLabel="nombre" itemValue="id"></form:select></br>
    <form:button>Comenzar conversación</form:button>
</form:form>

</form>
</body>
</html>
