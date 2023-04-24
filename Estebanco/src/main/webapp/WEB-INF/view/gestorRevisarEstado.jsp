<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page import="es.estebanco.estebanco.ui.FiltroGestor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PersonaEntity persona = (PersonaEntity) request.getAttribute("personaRevisar");

%>
<html>
<head>
    <title> Revisar Estado Persona</title>
</head>
<body>

<form:form method="post" modelAttribute="personaRevisar" action="revisar">
    <form:hidden path="id"></form:hidden>
    <form:hidden path="dni"></form:hidden>
    <form:hidden path="nombre"></form:hidden>
    <form:hidden path="apellido1"></form:hidden>
    <form:hidden path="apellido2"></form:hidden>
    <form:hidden path="correo"></form:hidden>
    <form:hidden path="telefono"></form:hidden>
    <form:hidden path="direccion"></form:hidden>
    <form:hidden path="contraseña"></form:hidden>
    <form:hidden path="usuario"></form:hidden>
    <form:checkbox path="estado" value="bien" label="aceptar"></form:checkbox>
    <form:checkbox path="estado" value="bloqueado" label="rechazar"></form:checkbox>

    <Button>Revisar Persona</Button>
</form:form>

<h1> Datos de la persona </h1>

<ul>
    <li><%=persona.getDni()%></li>
    <li><%=persona.getNombre()%></li>
    <li><%=persona.getApellido1()%></li>
    <li><%=persona.getApellido2()%></li>
    <li><%=persona.getCorreo()%></li>
    <li><%=persona.getTelefono()%></li>
    <li><%=persona.getDireccion()%></li>
</ul>
</body>
</html>