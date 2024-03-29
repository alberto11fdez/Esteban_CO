<!-- SERGIO -> 100% -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page import="es.estebanco.estebanco.dto.OperacionEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<OperacionEntityDto> operaciones = (List<OperacionEntityDto>) request.getAttribute("operaciones");
    List<CuentaEntityDto> cuentasExternas = (List<CuentaEntityDto>) request.getAttribute("cuentasexternas");
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
<h2> Listado de las cuentas reconocidas como sospechosas</h2>

<table border="1">
    <tr>
        <th>IBAN</th>
        <th>MONEDA</th>
        <th>ESTADO</th>
        <th>FECHA APERTURA</th>
        <th>ID</th>
    </tr>
    <%
        for (CuentaEntityDto cuenta : cuentasExternas) {
    %>
    <tr>
        <td><%= cuenta.getIban() %></td>
        <td><%= cuenta.getMoneda() %></td>
        <td><%= cuenta.getEstado() %></td>
        <td><%= cuenta.getFechaApertura() %></td>
        <td><%= cuenta.getId() %></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/gestor/">Volver</a>
<a href="/gestor/cuentasSospechosas">Volver a las cuentas sospechosas</a>
</body>
</html>