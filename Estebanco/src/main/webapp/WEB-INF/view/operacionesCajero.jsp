<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntity cuenta = (CuentaEntity) request.getAttribute("cuenta");
%>

<html>
<head>
    <title>Operaciones cajero</title>
</head>
<body>
<c:if test="${error != null}" >
    <p style="color:red;">
            ${error}
    </p>
</c:if>
<h1>Estas operando con la cuenta con IBAN: <%=cuenta.getIban()%></h1>
<h1>Saldo: <%=cuenta.getSaldo()%></h1>
<table border="1">
    <tr>
        <td><a href="/cajero/sacarDinero?id=<%=cuenta.getId()%>">Sacar dinero</a></td>
        <td><a href="/cajero/ingresarDinero?id=<%=cuenta.getId()%>">Ingresar dinero</a></td>
    </tr>
    <tr>
        <td><a href="/cajero/editar?id=<%=cuenta.getId()%>">Modificar datos</a></td>
        <td><a href="/cajero/cambiarDivisa?id=<%=cuenta.getId()%>">Cambiar divisa</a></td>
    </tr>
    <tr>
        <td><a href="/cajero/listadoOperaciones?id=<%=cuenta.getId()%>">Listado operaciones</a></td>
        <td><a href="/cajero/transferirDinero?id=<%=cuenta.getId()%>">Transferencia</a></td>
    </tr>
</table>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
