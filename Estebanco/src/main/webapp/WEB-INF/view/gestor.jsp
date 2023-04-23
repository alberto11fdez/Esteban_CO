<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PersonaEntity> personas = (List<PersonaEntity>) request.getAttribute("personas");
    List<CuentaEntity> cuentas = (List<CuentaEntity>) request.getAttribute("cuentas");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
    List<RolEntity> roles = (List<RolEntity>) request.getAttribute("roles");
    List<TipoMonedaEntity> monedas = (List<TipoMonedaEntity>) request.getAttribute("monedas");
%>
<html>
<head> Gestión Bancaria</head>
<body>
<h1> Vista del gestor</h1>

<form:form action="/gestor/filtrar" method="post" modelAttribute="filtro">

    Contiene: <form:input path="texto"/>
        Divisa: <form:select multiple="true" path="monedas" size="4">
    <form:option value="" label ="Todas"/>
    <form:options items="${monedas}" itemLabel="moneda" itemValue="moneda"/>
</form:select>
    <button>Filtrar</button>
</form:form>

<h2> Vista de todas las cuentas. Clientes y Empresas</h2>

<table border="1">
    <tr>
        <th>IBAN</th>
        <th>SALDO</th>
        <th>MONEDA</th>
        <th>ESTADO</th>
        <th>FECHA APERTURA</th>
        <th>ID</th>
    </tr>
<%
    for (CuentaEntity cuenta : cuentas){
%>
    <tr>
        <td><%= cuenta.getIban()%></td>
        <td><%= cuenta.getSaldo()%></td>
        <td><%= cuenta.getMoneda()%></td>
        <td><%= cuenta.getEstado()%></td>
        <td><%= cuenta.getFechaApertura()%></td>
        <td><%= cuenta.getId()%></td>
    </tr>
<%
    }
%>
</table border="1">


    <a href="/gestor/gestorPersonas">Vista de los clientes</a>
    <a href="/gestor/solicitudes">Vista de las solicitudes</a>
</table>
</body>
</html>