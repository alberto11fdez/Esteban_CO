<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page import="es.estebanco.estebanco.dto.OperacionEntityDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<OperacionEntityDto> operaciones = (List<OperacionEntityDto>) request.getAttribute("operaciones");
%>

<form:form method="post" modelAttribute="filtroOperaciones" action="/gestor/filtrarOperaciones">
    Buscar por fecha: <form:select path="fechaOperacion" size="15">
    <form:option value="" label="Sin fecha"/>
    <form:options items="${fechas}" itemLabel="fechaOperacion" itemValue="fechaOperacion"/>
</form:select>
</form:form>

<html>
<head>
    <title>Operaciones</title>
</head>
<body>
<h1> Listado de operaciones de la cuenta</h1>
<table border="1">
    <tr>
        <th>idOperacion</th>
        <th>fecha operacion</th>
        <th>tipo</th>
        <th>cantidad</th>
        <th>divisa</th>
        <th>IBAN cuenta destino</th>
        <th> Cuenta ID</th>
        <th> Persona ID</th>
    </tr>
    <%
        for (OperacionEntityDto operacion : operaciones) {
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getMoneda()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
        <td><%=operacion.getCuentaByCuentaId().getId()%></td>
        <td><%=operacion.getPersonaByPersonaId().getId()%></td>

    </tr>
    <%
        }
    %>
</table>
<a href="/gestor/">Volver</a>
</body>
</html>