<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.RolEntity" %>
<%@ page import="es.estebanco.estebanco.dao.RolRepository" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.OperacionEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.PersonaEntityDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CuentaEntityDto cuenta = (CuentaEntityDto) request.getAttribute("cuenta");
    List<OperacionEntityDto> operaciones = (List<OperacionEntityDto>) request.getAttribute("operaciones");
    PersonaEntityDto persona = (PersonaEntityDto) request.getAttribute("persona");
%>

<html>
<head>
    <title>Cuenta Persona</title>
</head>
<body>
<h1>Esta es tu cuenta:</h1>
<ol>
    <li>IBAN: <%=cuenta.getIban()%></li>
    <li>Saldo: <%=cuenta.getSaldo()%></li>
    <li>Moneda:<%=cuenta.getMoneda()%></li>
    <li>Estado: <%=cuenta.getEstado()%></li>
    <li>Fecha de Apertura: <%=cuenta.getFechaApertura()%></li>
</ol>
<h1>Operaciones:</h1>
<form:form action="/cuentaPersona/filtrar" method="post" modelAttribute="filtro">
    <form:hidden path="idpersona"/>
    Elige el tipo:
    <form:select path="tipo">
        <form:option value="" label="todo" />
        <form:options items="${tipos_filtro}"/>
    </form:select>
    <button>Filtrar</button>
</form:form>
<%
    if(operaciones==null||operaciones.isEmpty()){
%>
<h3>No hay operaciones</h3>
<%
}else{
%>
<table border="1">
    <%
        for(OperacionEntityDto operacion: operaciones ){
    %>
    <tr>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
    </tr>
    <%
        }
        }
    %>

</table border="1">
<button><a href="/cuentaPersona/mostrarTransferencia?idCuenta=<%=cuenta.getId()%>">Realizar transferencia</a></button>
<button><a href="/cuentaPersona/mostrarDivisa?idCuenta=<%=cuenta.getId()%>">Realizar cambio de divisa</a></button>
<a href="/persona/?id=<%=persona.getId()%>">Volver</a>
</body>
</html>