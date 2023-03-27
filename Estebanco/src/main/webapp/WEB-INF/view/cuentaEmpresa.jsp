<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    CuentaEntity cuentaEmpresa = (CuentaEntity) request.getAttribute("cuentaEmpresa");
%>




<html>
<head>
    <title>Cuenta Empresa</title>
</head>
<body>
<h1>Esta es tu cuenta:</h1>
<ol>
     <li>IBAN: <%=cuentaEmpresa.getIban()%></li>
    <li>Saldo: <%=cuentaEmpresa.getSaldo()%></li>
    <li>Moneda:<%=cuentaEmpresa.getMoneda()%></li>
    <li>Estado: <%=cuentaEmpresa.getEstado()%></li>
    <li>Fecha de Apertura: <%=cuentaEmpresa.getFechaApertura()%></li>


</ol>
</body>
</html>