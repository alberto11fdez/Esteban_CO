<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.RolEntity" %>
<%@ page import="es.estebanco.estebanco.dao.RolRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    CuentaEntity cuentaEmpresa = (CuentaEntity) request.getAttribute("cuentaEmpresa");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
    List<PersonaEntity> socios = (List<PersonaEntity>) request.getAttribute("socios");
    RolRepository rolRepository=(RolRepository) request.getAttribute("rolrepository");
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
<button><a href="/operacion">Realizar operacion</a></button>


<h1>Lista de socios</h1>
<table border="1">
        <%
    for(PersonaEntity p: socios){
%>
    <%RolEntity rol =rolRepository.obtenerRol_Persona_en_Empresa(p.getId(),cuentaEmpresa.getId());%>
    <tr>
        <td><%=p.getDni()%></td>
        <td><%=p.getNombre()%></td>
        <td><%=p.getApellido1()%></td>
        <td><%=p.getEstado()%></td>
        <%if(p.getEstado().equals("espera_confirmacion")){%>
            <td>---------</td>
        <%}else{%>
            <%
            if(rol.getBloqueado_Empresa()==0){%>
                <td><a href="/socio/bloquear?id=<%= p.getId()%>">Bloquear</a></td>
            <%}else{%>
                <td><a href="/socio/activar?id=<%= p.getId()%>">Activar</a></td>
            <%}%>
        <%}%>
    </tr>
        <%
    }
%>
</table>
<button><a href="/crearSocio?idCuenta=<%=cuentaEmpresa.getId()%>">Crear Socio</a></button>
</body>
</html>