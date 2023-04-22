<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntity cuenta = (CuentaEntity) request.getAttribute("cuenta");
    PersonaEntity persona=(PersonaEntity) request.getAttribute("persona");
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

<form:form action="/cuentaEmpresa/transfiriendoDinero" modelAttribute="cuenta"  method="post">
    Cantidad a sacar: <input type="number" name="valor">
    IBAN cuenta destino: <input type="text" name="destino">
    <form:hidden path="id"/>

    <button>Enviar</button>
</form:form>
<a href="/cuentaEmpresa/?id=<%=cuenta.getId()%>&idPersona=<%=persona.getId()%>">Volver</a>
</body>
</html>