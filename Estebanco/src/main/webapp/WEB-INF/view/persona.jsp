<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page import="es.estebanco.estebanco.entity.ConversacionEntity" %>
<%@ page import="es.estebanco.estebanco.ui.FiltroOperacion" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
    List<CuentaEntity> cuentas = (List<CuentaEntity>) request.getAttribute("cuentas");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
    List<ConversacionEntity> conversaciones = (List<ConversacionEntity>) request.getAttribute("conversaciones");
    FiltroOperacion filtro = (FiltroOperacion) request.getAttribute("filtro");

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
    <input type="hidden" name="id" value="${persona.id}" />
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
        <td><a href="/persona/mostrarTransferencia?idPersona=<%=persona.getId()%>&idCuenta=<%=cuenta.getId()%>">Realizar transferencia</a></td>
        <td><a href="/persona/mostrarDivisa?idPersona=<%=persona.getId()%>&idCuenta=<%=cuenta.getId()%>">Realizar cambio de divisa</a></td>
        <td><a href="">Activar/Desactivar cuenta</a></td>
        <td><a href="/persona/entrarEnCuenta?idPersona=<%=persona.getId()%>&idCuenta=<%=cuenta.getId()%>">Entrar</a></td>
    </tr>
<%
    }
%>
</table border="1">
<h2>Operaciones:</h2>

<form:form action="/persona/filtrar" method="post" modelAttribute="filtro">
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
    <tr>
        <th>ID</th>
        <th>FECHA DE OPERACIÓN</th>
        <th>TIPO</th>
        <th>CANTIDAD</th>
        <th>MONEDA</th>
        <th>CUENTA ORIGEN</th>
        <th>CUENTA DESTINO</th>
    </tr>
    <%
        for(OperacionEntity operacion: operaciones){
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getMoneda()%></td>
        <td><%=operacion.getCuentaByCuentaId().getIban()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
    </tr>
    <%
        }
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
        for(ConversacionEntity conversacion: conversaciones){
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