<!-- NICOLÁS -> 100% -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.TipoMonedaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.dto.TipoMonedaEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
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
<form:form action="/cuentaPersona/guardarDivisa" modelAttribute="cuenta" method="post">
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
<a href="/cuentaPersona/?idCuenta=<%=cuenta.getId()%>">Volver</a>
</body>
</html>