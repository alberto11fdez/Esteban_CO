<!-- JOSÉ -> 100% -->
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

    CuentaEntityDto cuentaEmpresa = (CuentaEntityDto) request.getAttribute("cuentaEmpresa");
    List<OperacionEntityDto> operaciones = (List<OperacionEntityDto>) request.getAttribute("operaciones");
    List<PersonaEntityDto> socios = (List<PersonaEntityDto>) request.getAttribute("socios");
    PersonaEntityDto persona=(PersonaEntityDto) request.getAttribute("persona");
    List<OperacionEntityDto> operacionesRecibidas = (List<OperacionEntityDto>) request.getAttribute("operacionesRecibidas");
    List<Integer> sociosActivos=(List<Integer>) request.getAttribute("sociosActivos");
    List<Integer> sociosBloqueados=(List<Integer>) request.getAttribute("sociosBloqueados");

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

<% if(!(operaciones==null)){%>
    <table border="1">
        <%
            for(OperacionEntityDto operacion: operaciones){
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
<button><a href="/cuentaEmpresa/mostrarTransferencia?idCuenta=<%=cuentaEmpresa.getId()%>&idPersona=<%=persona.getId()%>">Realizar transferencia</a></button>
<button><a href="/cuentaEmpresa/mostrarDivisa?idCuenta=<%=cuentaEmpresa.getId()%>&idPersona=<%=persona.getId()%>">Realizar cambio de divisa</a></button>
<br/>
<h1>Operaciones recibidas:</h1>

<%if(!(operacionesRecibidas ==null))
{%>
<table border="1">
    <%
        for(OperacionEntityDto operacion: operacionesRecibidas){
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
        for(PersonaEntityDto p: socios){
    %>
    <tr>
        <td><%=p.getDni()%></td>
        <td><%=p.getNombre()%></td>
        <td><%=p.getApellido1()%></td>
        <td><%=p.getEstado()%></td>
        <%if(p.getEstado().equals("espera_confirmacion")){%>
        <td>---------</td>
        <%}else{%>
        <%
            if(sociosActivos.contains(p.getId())){%>
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