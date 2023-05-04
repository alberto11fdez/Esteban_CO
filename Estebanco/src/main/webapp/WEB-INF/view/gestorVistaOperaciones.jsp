<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
%>

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
        for (OperacionEntity operacion : operaciones) {
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getMoneda()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
        <td><%=operacion.getCuentaByCuentaId()%></td>
        <td><%=operacion.getPersonaByPersonaId()%></td>

    </tr>
    <%
        }
    %>
</table>
</body>
</html>