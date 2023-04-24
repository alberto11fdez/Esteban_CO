<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<html>
<%
    CuentaEntity cuenta = (CuentaEntity) request.getAttribute("cuentaRevisar");
%>

<head>
    <title>Revisar Cuenta</title>
</head>
<body>

<form:form method="post" modelAttribute="cuentaRevisar" action="revisarCuenta">
    <form:hidden path="id"></form:hidden>
    <form:hidden path="moneda"></form:hidden>
    <form:hidden path="saldo"></form:hidden>

    
    <form:checkbox path="estado" value="bien" label="aceptar"></form:checkbox>
    <form:checkbox path="estado" value="bloqueado" label="rechazar"></form:checkbox>

    <Button>Revisar Cuenta</Button>
</form:form>

<h1> Datos de la cuenta </h1>

<ul>
    <li>ID: <%=cuenta.getId()%></li>
    <li>FECHA APERTURA: <%=cuenta.getFechaApertura()%></li>
    <li>DIVISA: <%=cuenta.getMoneda()%></li>
    <li>SALDO: <%=cuenta.getSaldo()%></li>
</ul>
</body>
</html>