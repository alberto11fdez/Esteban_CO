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
        <th><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-circle" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V4.5z"/>
        </svg></th>

        <th><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-x" viewBox="0 0 16 16">
            <path d="M6.146 6.146a.5.5 0 0 1 .708 0L8 7.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 8l1.147 1.146a.5.5 0 0 1-.708.708L8 8.707 6.854 9.854a.5.5 0 0 1-.708-.708L7.293 8 6.146 6.854a.5.5 0 0 1 0-.708z"/>
            <path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
        </svg></th>
    </tr>
    <%
        for(ConversacionEntity conversacion: persona.getConversacionsById()){
            if(conversacion.getEstado()==1){
    %>
    <tr>
        <th> <%=conversacion.getIdconversacion()%></th>
        <th> <%=conversacion.getEstado()%></th>
        <th> <%=conversacion.getFechaInicio()%></th>
        <th> <%=conversacion.getFechaFin()%></th>
        <th> <%=conversacion.getPersonaByPersonaId().getId()%></th>
        <th> <%=conversacion.getPersonaByAsistenteId().getId()%></th>
        <th><a href="/mensaje/entrarCliente?idCliente=<%=conversacion.getPersonaByPersonaId().getId()%>&idAsistente=<%=conversacion.getPersonaByAsistenteId().getId()%>&idConversacion=<%=conversacion.getIdconversacion()%>"> Entrar en conversación</a></th>
        <th><a href="/asistente/cerrar?id=<%=conversacion.getIdconversacion()%>">Cerrar conversación</a></th>

    </tr>
    <%
            }
        }
    %>
</table border="1">

<button><a href="/asistente/crearConversacion?idCliente=<%=persona.getId()%>">Crear conversación</a></button></br></br>
<button><a href="/gestor/"> Entrar como gestor</a></button>
<button><a href="/logout">Log out</a></button>


</body>
</html>