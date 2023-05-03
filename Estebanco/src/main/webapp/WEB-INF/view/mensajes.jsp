<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 28/03/2023
  Time: 2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer soyCliente = (Integer) request.getAttribute("soyCliente");
%>

<html>
<head>
    <title>Conversación</title>
</head>
<body>
<h1>Mensajes</h1>

<form:form method="post" action="/mensaje/guardar" modelAttribute="mensajeNuevo">
    <form:hidden path="idmensaje"></form:hidden>
    <form:hidden path="fechaEnvio"></form:hidden>
    <form:hidden path="conversacionEmisorId"></form:hidden>
    <form:hidden path="conversacionReceptorId"></form:hidden>
    <form:hidden path="idconversacion"></form:hidden>
    <input type="hidden" name="soyCliente" value="<%=soyCliente%>">

    -<form:input path="texto" size="100" maxlength="100"></form:input>

    <form:button>Enviar</form:button>
</form:form>
</body>
</html>
