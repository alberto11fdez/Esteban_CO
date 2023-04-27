<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.MensajeEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<MensajeEntity> listaMensajes = (List<MensajeEntity>) request.getAttribute("listaMensajes");
    PersonaEntity cliente = (PersonaEntity) request.getAttribute("cliente");
    PersonaEntity asistente = (PersonaEntity) request.getAttribute("asistente");
    Integer idConversacion = (Integer) request.getAttribute("idConversacion");
    Integer soyCliente = (Integer) request.getAttribute("soyCliente");
%>
<html>
<head>
    <title>Pefil</title>
</head>
<body>
<h1>Mensajes con Asistente:</h1>


<h4 align="left">Mensajes Cliente</h4>
<h4 align="center">Mensajes Asistente</h4>

<%
    if(!listaMensajes.isEmpty()){
        for(MensajeEntity m : listaMensajes){
            if(m.getConversacionEmisorId()==cliente.getId()){
%>
<p><%=cliente.getNombre()%>: <%=m.getTexto()%> <a>Envio:(<%=m.getFechaEnvio()%>)</a></p>
<%
            }else{

%>
        <p align="center"><%=asistente.getNombre()%>: <%=m.getTexto()%>  <a>Envio:(<%=m.getFechaEnvio()%>)</a></p>
<%
            }
        }
    }
%>

<button><a href="/mensaje/crearMensaje?idCliente=<%=cliente.getId()%>&idAsistente=<%=asistente.getId()%>&idConversacion=<%=idConversacion%>&soyCliente=<%=soyCliente%>">Crear Mensaje</a></button></br>
</br>
<button><a href="/persona/?id=<%=cliente.getId()%>">Home</a></button>
</body>
</html>