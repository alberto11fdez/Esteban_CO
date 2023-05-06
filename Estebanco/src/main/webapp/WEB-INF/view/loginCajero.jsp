<!-- FERNANDO -> 100% -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Autenticacion cajero</title>
</head>
<body>
<h1>Bienvenido al cajero:</h1>

<c:if test="${error != null}">
  <p style="color: red">
      ${error}
  </p>
</c:if>

<form action="/cajeroLogin/" method="post">
  <table>
    <tr>
      <td>Usuario:</td><td><input type="text" name="usuario"></td>
    </tr>
    <tr>
      <td>Clave:</td><td><input type="password" name="clave"></td>
    </tr>
    <tr>
      <td colspan="2"><button>Enviar</button></td>
    </tr>
  </table>
</form>
</body>
</html>
