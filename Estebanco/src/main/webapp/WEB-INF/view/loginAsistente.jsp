

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Inicie sesión:</h1>
<c:if test="${error != null}" >
    <p style="color:red;">
            ${error}
    </p>
</c:if>
<form action="/loginAsistente" method="post">
    <table>
        <tr>
            <td>Usuario:</td> <td><input type="text" name="usuario"></td>
        </tr>
        <tr>
            <td>Contraseña:</td> <td><input type="password" name="contraseña"> </td>
        </tr>
        <tr>
            <td colspan="2"> <button>Enviar</button></td>
        </tr>
    </table>
</form>
</body>
</html>