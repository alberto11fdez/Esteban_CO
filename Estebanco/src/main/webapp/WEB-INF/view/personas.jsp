<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 20/03/2023
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PersonaEntity> listaPersonas = (List<PersonaEntity>) request.getAttribute("personas");
%>

<html>
<head>
    <title>Personas Ejmplos</title>
</head>
<body>

    <h1>Listado personas</h1>

    <table border="1">
        <tr>
            <th>DNI</th>
            <th>Nombre</th>
            <th>Apellido1</th>
            <th>Apellido2</th>
        </tr>

        <%
            if (listaPersonas != null)
            for(PersonaEntity personaActual : listaPersonas){
        %>
        <tr>
            <td><%=personaActual.getDni()%></td>
            <td><%=personaActual.getNombre()%></td>
            <td><%=personaActual.getApellido1()%></td>
            <td><%=personaActual.getApellido2()%></td>
        </tr>

        <%
            }
        %>

    </table border="1">

</body>
</html>
