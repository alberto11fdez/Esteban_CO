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
    PersonaEntity persona=(PersonaEntity) request.getAttribute("persona");
    List<OperacionEntity> operacionesRescibidas = (List<OperacionEntity>) request.getAttribute("operacionesRescibidas");

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




<h1>Operaciones realizadas:</h1>
<form:form method="post" action="/cuentaEmpresa/filtroOperacionSocio" modelAttribute="filtroOperacionSocio">
    <form:select path="idSocio" items="${socios}" itemValue="id" itemLabel="dni"></form:select>
    <form:button>Buscar sus Operaciones</form:button>
</form:form>

<form:form action="/cuentaEmpresa/filtrar" method="post" modelAttribute="filtroOperacion">
    <form:hidden path="idpersona"/>
    Elige el tipo:
    <form:select path="tipo">
        <form:option value="" label="todo" />
        <form:options items="${tipos_filtro}"/>
    </form:select>
    <button>Filtrar</button>
</form:form>

<table border="1">
    <%
        for(OperacionEntity operacion: operaciones){
    %>
    <tr>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
    </tr>
    <%
        }
    %>
</table >

<br/>
<button><a href="/cuentaEmpresa/mostrarTransferencia?idCuenta=<%=cuentaEmpresa.getId()%>&idPersona=<%=persona.getId()%>">Realizar transferencia</a></button>
<button><a href="/cuentaEmpresa/mostrarDivisa?idCuenta=<%=cuentaEmpresa.getId()%>&idPersona=<%=persona.getId()%>">Realizar cambio de divisa</a></button>
<br/>
<h1>Operaciones recibidas:</h1>

<%if(!(operacionesRescibidas ==null))
{%>
<table border="1">
    <%
        for(OperacionEntity operacion: operacionesRescibidas){
    %>
    <tr>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
    </tr>
    <%
        }
    %>
</table >
<%}%>
<br/>






<h1>Lista de socios:</h1>

<form:form action="/cuentaEmpresa/filtrarSocios" method="post" modelAttribute="filtroSocios">
    <form:hidden path="idpersona"/>
    Elige el tipo:
    <form:select path="tipo">
        <form:option value="" label="todo" />
        <form:options items="${tipos_filtro_socio}"/>
    </form:select>
    <button>Filtrar</button>
</form:form>

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
        <td><a href="/cuentaEmpresa/socio/bloquear?id=<%= p.getId()%>">Bloquear</a></td>
        <%}else{%>
        <td><a href="/cuentaEmpresa/socio/activar?id=<%= p.getId()%>">Activar</a></td>
        <%}%>
        <%}%>
    </tr>
    <%
        }
    %>
</table>
<br/>
<button><a href="/cuentaEmpresa/crearSocio?idCuenta=<%=cuentaEmpresa.getId()%>">Crear Socio</a></button>
</body>
</html>