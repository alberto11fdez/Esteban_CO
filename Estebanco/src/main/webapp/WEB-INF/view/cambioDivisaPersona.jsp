<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.TipoMonedaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntity cuenta = (CuentaEntity) request.getAttribute("cuenta");
    List<TipoMonedaEntity> monedas = (List<TipoMonedaEntity>) request.getAttribute("monedas");
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
<a href="/cuentaEmpresa/?idCuenta=<%=cuenta.getId()%>">Volver</a>
</body>
</html>