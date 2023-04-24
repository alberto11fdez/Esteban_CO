<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<PersonaEntity> personas = (List<PersonaEntity>) request.getAttribute("personas");
%>
<html>
<head> Vista Gestor Bancaria Clientes</head>
<body>
<h1>Vista de las Personas</h1>


<table border="1">
    <tr>
        <th>ID</th>
        <th>NOMBRE</th>
        <th>APELLIDO 1</th>
        <th>APELLIDO 2</th>
        <th>CORREO</th>
        <th>DIRECCION</th>
        <th>TELEFONO</th>
        <th>USUARIO</th>
        <th>CONTRASEÑA</th>
        <th>ESTADO</th>
    </tr>
    <%
        for (PersonaEntity persona : personas){
    %>
    <tr>
        <td><%= persona.getId()%></td>
        <td><%= persona.getNombre()%></td>
        <td><%= persona.getApellido1()%></td>
        <td><%= persona.getApellido2()%></td>
        <td><%= persona.getCorreo()%></td>
        <td><%= persona.getDireccion()%></td>
        <td><%= persona.getTelefono()%></td>
        <td><%= persona.getUsuario()%></td>
        <td><%= persona.getContraseña()%></td>
        <td><%= persona.getEstado()%></td>
    </tr>
    <%
        }
    %>
    <a href="/gestor/"> Volver </a>

</table>

<a href="/gestor/solicitudes">Vista de las solicitudes</a>

</body>
</html>