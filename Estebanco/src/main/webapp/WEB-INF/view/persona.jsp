<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
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
</ol>
</body>
</html>