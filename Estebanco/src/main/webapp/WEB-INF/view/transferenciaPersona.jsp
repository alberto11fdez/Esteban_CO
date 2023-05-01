<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntityDto cuenta = (CuentaEntityDto) request.getAttribute("cuenta");

%>

<html>
<head>
    <title>Transferencia</title>
</head>
<body>
<c:if test="${error != null}" >
    <p style="color:red;">
            ${error}
    </p>
</c:if>
<h1>Transferir dinero</h1>
<h2>Saldo disponible: <%=cuenta.getSaldo()%></h2>

<form:form action="/cuentaPersona/transfiriendoDinero" modelAttribute="cuenta" method="post">
    Cantidad a sacar: <input type="number" name="valor">
    IBAN cuenta destino: <input type="text" name="destino">
    <form:hidden path="id"/>

    <button>Enviar</button>
</form:form>
<a href="/cuentaPersona/?idCuenta=<%=cuenta.getId()%>">Volver</a>
</body>
</html>