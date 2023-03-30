
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Crear Persona</title>
</head>
<body>
<h1> Formulario de Registro:</h1>

<form:form action="/persona/guardar" modelAttribute="persona" method="post">
    <form:hidden path="id"/>
    Dni: <form:input path="dni" size="30" maxlength="30"  /><br/>
    Nombre: <form:input path="nombre" size="40"  maxlength="40"/> <br/>
    Primer Apellido: <form:input path="apellido1"  size="30" maxlength="30" /><br/>
    Segundo Apellido: <form:input path="apellido2"  size="30" maxlength="30" /><br/>
    Correo: <form:input path="correo" maxlength="25" /> <br/>
    Direccion: <form:input path="direccion" maxlength="40" size="30" /><br/>
    Telefono: <form:input path="telefono" size="30" /><br/>
    Usuario: <form:input path="usuario" maxlength="12" size="30" /><br/>
    Contraseña: <form:input path="contraseña" maxlength="12" size="30" /><br/>

    <form:button>Guardar</form:button>

</form:form>
</body>
</html>