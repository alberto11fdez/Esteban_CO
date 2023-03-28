<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 28/03/2023
  Time: 2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Conversación</title>
</head>
<body>
<h1>Conversación</h1>

<ul>
    <c:forEach var="mensaje" items="${mensajes}">
        <li>${mensaje}</li>
    </c:forEach>
</ul>

<form method="post" action="/conversacion">
    <input type="text" name="mensaje" />
    <button type="submit">Enviar</button>
    <button type="submit">Acabar Conversación</button>
</form>
</body>
</html>
