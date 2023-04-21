<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.ui.FiltroOperacion" %>
<%@ page import="es.estebanco.estebanco.dao.RolRepository" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
    List<CuentaEntity> cuentas = (List<CuentaEntity>) request.getAttribute("cuentas");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
    List<ConversacionEntity> conversaciones = (List<ConversacionEntity>) request.getAttribute("conversaciones");
    FiltroOperacion filtro = (FiltroOperacion) request.getAttribute("filtro");
    RolRepository rolRepository=(RolRepository) request.getAttribute("rolrepository");

%>
<html>
<head>
    <title>Pefil</title>
</head>
<body>
<h1>Datos personales:</h1>

<ol>
    <li>Dni: <%=persona.getDni()%></li>
    <li>Nombre: <%=persona.getNombre()%></li>
    <li>Primer apellido: <%=persona.getApellido1()%></li>
    <li>Segundo apellido: <%=persona.getApellido2()%></li>
    <li>Correo: <%=persona.getCorreo()%></li>
    <li>Dirección: <%=persona.getDireccion()%></li>
    <li>Nombre de usuario: <%=persona.getUsuario()%></li>
    <li>Contraseña: <%=persona.getContraseña()%></li>
    <li>Estado de la cuenta: <%=persona.getEstado()%></li>
</ol>
<a href="/persona/editar?id=<%= persona.getId() %>"> Editar</a>
<br/>
<h2>Cuentas:</h2>
<form:form action="/crearCuenta" modelAttribute="rolCuentaNueva" method="post">
    <form:button>Crear Cuenta </form:button>
    <form:hidden path="id"></form:hidden>
    <form:hidden path="personaByPersonaId"></form:hidden>
     Tipo de Cuenta: <form:select path="rol" items="${tipos_rol}" />
</form:form>

<br/>
<table border="1">
    <tr>
        <th>IBAN</th>
        <th>SALDO</th>
        <th>MONEDA</th>
        <th>ESTADO</th>
        <th>FECHA DE APERTURA</th>
    </tr>
    <%
        for(CuentaEntity cuenta: cuentas){
    %>
    <tr>
        <td><%=cuenta.getIban()%></td>
        <td><%=cuenta.getSaldo()%></td>
        <td><%=cuenta.getMoneda()%></td>
        <td><%=cuenta.getEstado()%></td>
        <td><%=cuenta.getFechaApertura()%></td>


        <%if(cuenta.getEstado().equals("bloqueado")){%>
            <td><a href="/persona/solicitarActivacion?idCuenta=<%=cuenta.getId()%>&&idPersona=<%=persona.getId()%>">Solicitar activacion</a></td>
        <%}else {%>
            <td>-------</td>
        <%}%>
        <%RolEntity rol= rolRepository.obtenerRol_Persona_en_Empresa(persona.getId(), cuenta.getId());%>
       <!--Si la cuenta esta activada y no es socio -->
        <% if(cuenta.getEstado().equals("Activado") && (rol.getRol().equals("normal") || rol.getRol().equals("empresa")) ){ %>
            <td><a href="/persona/entrarEnCuenta?idPersona=<%=persona.getId()%>&idCuenta=<%=cuenta.getId()%>">Entrar</a></td>
        <!--Si la cuenta esta activada y es un socio NO bloqueado -->
        <%}else if(cuenta.getEstado().equals("Activado") && rol.getRol().equals("socio") && rol.getBloqueado_Empresa()==0){%>
            <td><a href="/persona/entrarEnCuenta?idPersona=<%=persona.getId()%>&idCuenta=<%=cuenta.getId()%>">Entrar</a></td>
        <%}else{%>
            <td>No se le permite entrar</td>
        <%}%>
    </tr>
<%
    }
%>
    </table border="1">

<h2>Conversaciones Con Asistentes:</h2>
<table border="1">
    <tr>
        <th>ID_Conversacion</th>
        <th>Estado</th>
        <th>Fecha Inicio</th>
        <th>Fecha Fin</th>
        <th>PersonaId</th>
        <th>AsitenteId</th>
    </tr>
    <%
        for(ConversacionEntity conversacion: persona.getConversacionsById()){
    %>
    <tr>
        <th> <%=conversacion.getIdconversacion()%></th>
        <th> <%=conversacion.getEstado()%></th>
        <th> <%=conversacion.getFechaInicio()%></th>
        <th> <%=conversacion.getFechaFin()%></th>
        <th> <%=conversacion.getPersonaByPersonaId().getId()%></th>
        <th> <%=conversacion.getPersonaByAsistenteId().getId()%></th>
        <th><a href="/asistente/entrar?id=<%= conversacion.getPersonaByPersonaId() %>"> Entrar en conversación</a></th>

    </tr>
    <%
        }
    %>
</table border="1">
<button>Crear conversación</button>
</body>
</html>