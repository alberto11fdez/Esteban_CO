<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    CuentaEntity cuentaEmpresa = (CuentaEntity) request.getAttribute("cuentaEmpresa");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
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
<h1>Operaciones:</h1>
<table border="1">
<%
    for(OperacionEntity operacion: operaciones){
%>
<tr>
    <td><%=operacion.getTipo()%></td>
    <td><%=operacion.getCantidad()%></td>
    <td><%=operacion.getFechaOperacion()%></td>
</tr>
<%
    }
%>
</table border="1">
</body>
</html>