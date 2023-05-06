<!-- FERNANDO -> 100% -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.TipoMonedaEntity" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.TipoMonedaEntityDto" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 30/03/2023
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntityDto cuenta = (CuentaEntityDto) request.getAttribute("cuenta");
    List<TipoMonedaEntityDto> monedas = (List<TipoMonedaEntityDto>) request.getAttribute("monedas");
%>

<html>
<head>
    <title>Datos divisa</title>
</head>
<body>
Cuenta IBAN: <%=cuenta.getIban()%>
Saldo: <%=cuenta.getSaldo()%>
<form:form action="/cajero/guardarDivisa" modelAttribute="cuenta" method="post">
    <form:hidden path="saldo"></form:hidden>
    <form:hidden path="iban"></form:hidden>
    <form:hidden path="estado"></form:hidden>
    <form:hidden path="fechaApertura"></form:hidden>
    <form:hidden path="id"></form:hidden>
    <form:select path="moneda">
        <form:options items="${monedas}" itemLabel="moneda" itemValue="moneda"></form:options>
    </form:select>
    <form:button>Guardar</form:button>
</form:form>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
