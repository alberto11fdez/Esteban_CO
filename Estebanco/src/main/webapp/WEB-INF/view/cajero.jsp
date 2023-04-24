<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
  List<CuentaEntity> cuentas = (List<CuentaEntity>) request.getAttribute("cuentas");

%>

<html>
<head>
    <title>Cajero</title>
</head>
<body>
<h1>Cuentas del cliente: </h1>

<table border="1">
  <tr>
    <th>IBAN</th>
    <th>SALDO</th>
    <th>MONEDA</th>
    <th>ESTADO</th>
    <th>FECHA DE APERTURA</th>
  </tr>

  <%
    for(CuentaEntity cuenta:cuentas){
  %>

  <tr>
    <td><%=cuenta.getIban()%></td>
    <td><%=cuenta.getSaldo()%></td>
    <td><%=cuenta.getMoneda()%></td>
    <td><%=cuenta.getEstado()%></td>
    <td><%=cuenta.getFechaApertura()%></td>
    <%
      if(cuenta.getEstado().equals("bien")){
    %>
    <td><a href="/cajero/mostrarOperaciones?id=<%=cuenta.getId()%>">Operar con esta cuenta</a></td>
    <%
      }else if(cuenta.getEstado().equals("bloqueado")) {
    %>
    <td><a href="/cajero/solicitarDesbloqueo?id=<%=cuenta.getId()%>">Solicitar desbloqueo</a></td>
    <%
      }else{
    %>
    <td>Esperando desbloqueo ... </td>
    <%
      }
    %>
  </tr>

  <%
    }
  %>
</table>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
