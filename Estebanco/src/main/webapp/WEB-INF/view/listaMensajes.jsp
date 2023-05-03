<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.MensajeEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.dto.MensajeEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.PersonaEntityDto" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<MensajeEntityDto> listaMensajes = (List<MensajeEntityDto>) request.getAttribute("listaMensajes");
    PersonaEntityDto cliente = (PersonaEntityDto) request.getAttribute("cliente");
    PersonaEntityDto asistente = (PersonaEntityDto) request.getAttribute("asistente");
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
        for(MensajeEntityDto m : listaMensajes){
            if(m.getConversacionEmisorId()==cliente.getId()){
%>
<p style="font-size: 15px"><%=cliente.getNombre()%>: <%=m.getTexto()%>.<a style="font-size: 10px; margin-left: 5px">(<%=m.getFechaEnvio()%>)</a></p>
<%
            }else{

%>
        <p style="font-size: 15px" align="center"><%=asistente.getNombre()%>: <%=m.getTexto()%>.<a style="font-size: 10px; margin-left: 5px">(<%=m.getFechaEnvio()%>)</a></p>
<%
            }
        }
    }
%>

<button><a href="/mensaje/crearMensaje?idCliente=<%=cliente.getId()%>&idAsistente=<%=asistente.getId()%>&idConversacion=<%=idConversacion%>&soyCliente=<%=soyCliente%>">Crear Mensaje</a></button></br>
</br>

<%
    if(soyCliente==1){
%>
<button><a href="/persona/?id=<%=cliente.getId()%>">Home</a></button>
<%
    }else{
%>
<button><a href="/asistente/?id=<%=asistente.getId()%>">Home</a></button>
<%
    }
%>
</body>
</html>