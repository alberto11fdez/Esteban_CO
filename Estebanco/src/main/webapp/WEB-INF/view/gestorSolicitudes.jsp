<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page import="es.estebanco.estebanco.dto.PersonaEntityDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PersonaEntityDto> personaSolicitante = (List<PersonaEntityDto>) request.getAttribute("personaSolicitante");
%>

<html>
<head>Personas Solicitante</head>
<title>Personas Solicitantes</title>
<body>
    <table border="1">
        <tr>
            <th> DNI </th>
            <th> NOMBRE</th>
            <th> APELLIDO 1</th>
            <th> APELLIDO 2</th>
            <th> CORREO </th>
            <th> DIRECCION </th>
            <th> TELEFONO </th>
            <th> USUARIO </th>
            <th> CONTRASEÑA </th>
            <th> ESTADO </th>
        </tr>
        <%
            for (PersonaEntityDto persona : personaSolicitante){

        %>
        <tr>
            <td><%=persona.getDni()%></td>
            <td><%=persona.getNombre()%></td>
            <td><%=persona.getApellido1()%></td>
            <td><%=persona.getApellido2()%></td>
            <td><%=persona.getCorreo()%></td>
            <td><%=persona.getDireccion()%></td>
            <td><%=persona.getTelefono()%></td>
            <td><%=persona.getUsuario()%></td>
            <td><%=persona.getContraseña()%></td>
            <td><%=persona.getEstado()%></td>
            <td><a href="/gestor/revisar?id=<%=persona.getId()%>">Revisar estado</a></td>
        </tr>
        <%
            }
        %>

    </table>

<a href="/gestor/"> Volver </a>
</body>
</html>