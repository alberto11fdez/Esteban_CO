<!-- SERGIO -> 100% ROBARLE A FERNANDO LISTA OPERACIONES -->
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
<%@ page import="java.util.List" %>
<%
    List<CuentaEntityDto> cuentas = (List<CuentaEntityDto>) request.getAttribute("cuentasSospechosas");
    List<CuentaEntityDto> cuentasExternas = (List<CuentaEntityDto>) request.getAttribute("cuentasexternas");
%>
<html>
<head>
    <title> Operaciones Sospechosas</title>
</head>
<body>
<h1> Vista de las cuentas con operaciones sospechosas</h1>

<table border="1">
    <tr>
        <th>IBAN</th>
        <th>MONEDA</th>
        <th>ESTADO</th>
        <th>FECHA APERTURA</th>
        <th>ID</th>
    </tr>
    <%
        for (CuentaEntityDto cuenta : cuentas) {
    %>
    <tr>
        <td><%= cuenta.getIban() %></td>
        <td><%= cuenta.getMoneda() %></td>
        <td><%= cuenta.getEstado() %></td>
        <td><%= cuenta.getFechaApertura() %></td>
        <td><%= cuenta.getId() %></td>
        <td><a href="/gestor/revisarCuenta?idCuenta=<%=cuenta.getId()%>">Revisar cuenta</a></td>
        <td><a href="/gestor/historico?idCuenta=<%=cuenta.getId()%>">Historial operaciones</a></td>
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

<a href="/gestor/"> Volver </a>
</body>
</html>