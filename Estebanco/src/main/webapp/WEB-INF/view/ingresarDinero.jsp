<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="es.estebanco.estebanco.dto.CuentaEntityDto" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CuentaEntityDto cuenta = (CuentaEntityDto) request.getAttribute("cuenta");
%>

<html>
<head>
    <title>Ingresar dinero</title>
</head>
<body>
<c:if test="${error != null}" >
    <p style="color:red;">
            ${error}
    </p>
</c:if>
<h1>Ingresar dinero</h1>
<h2>Saldo: <%=cuenta.getSaldo()%></h2>

<form:form action="/cajero/ingresandoDinero" modelAttribute="cuenta" method="post">
    Cantidad a ingresar: <input type="number" name="valor">
    <form:hidden path="id"/>
    <button> Ingresar </button>
</form:form>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
