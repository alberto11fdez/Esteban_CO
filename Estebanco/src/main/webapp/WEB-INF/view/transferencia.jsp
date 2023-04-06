<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  CuentaEntity cuenta = (CuentaEntity) request.getAttribute("cuenta");
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

<form:form action="/cajero/transfiriendoDinero" modelAttribute="cuenta" method="post">
  Cantidad a sacar: <input type="number" name="valor">
  IBAN cuenta destino: <input type="text" name="destino">
  <form:hidden path="id"/>
  <button>Enviar</button>
</form:form>
</body>
</html>
