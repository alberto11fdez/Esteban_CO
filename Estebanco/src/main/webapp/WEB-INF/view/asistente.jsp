<%@ page import="es.estebanco.estebanco.entity.ConversacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.MensajeEntity" %><%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 27/03/2023
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ConversacionEntity> conversaciones = (List<ConversacionEntity>) request.getAttribute("conversaciones");
    List<MensajeEntity> mensajes = (List<MensajeEntity>) request.getAttribute("conversaciones");
%>

<html>
<head>
    <title>Asistente</title>
</head>
<body>
<h1>Asistente: </h1>

<h2>Conversaciones Con Clientes:</h2>

<form:form action="/asistente/filtrar" method="post" modelAttribute="filtro">
    Buscar por: <br/>
    Contiene: <form:input path="idConver"/>
    Mensaje:
    <form:select multiple="true" path="mensajesByIdconversacion"  size="6" >
        <form:option value="" label="------" />
        <form:options items="${mensajes}" itemLabel="mensajes" itemValue="mensajes" />
    </form:select>
    <button>Filtrar</button>
</form:form>

<table border="1">
    <tr>
        <th>ID_Conversacion</th>
        <th>Estado</th>
        <th>Fecha Inicio</th>
        <th>Fecha Fin</th>
        <th>PersonaId</th>
        <th>AsitenteId</th>
        <th>Entrar</th>
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

</body>
</html>
