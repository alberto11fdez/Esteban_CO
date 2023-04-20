<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.TipoOperacionEntity" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  List<OperacionEntity> listaOperaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
  List<TipoOperacionEntity> tipos = (List<TipoOperacionEntity>) request.getAttribute("tipos");
%>

<html>
<head>
    <title>Listado operaciones</title>
</head>
<body>

<form:form action="/cajero/filtrar" method="post" modelAttribute="filtro">
    IBAN: <form:input path="texto"></form:input>
    Tipo operacion:
    <form:select path="tipoOperacion">
        <form:option value="" label="-----"></form:option>
        <form:options items="${tipos}" itemLabel="nombre" itemValue="nombre"></form:options>
    </form:select>
    <button>Filtrar</button>
    <a href="/cajero/listadoOperaciones">Quitar filtros</a>
</form:form>

<table border="1">
    <tr>
        <td>ID</td>
        <td>Fecha</td>
        <td>Tipo</td>
        <td>Cuenta origen</td>
        <td>Cuenta destino transferencia</td>
        <td>Moneda</td>
    </tr>
    <%
        for (OperacionEntity operacion:listaOperaciones){
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCuentaByCuentaId().getIban()%></td>
        <td>
            <%
                if(operacion.getIbanCuentaDestinoOrigen()==null){
            %>
            ---
            <%
                }else{
            %>
            <%=operacion.getIbanCuentaDestinoOrigen()%>
            <%
                }
            %>
        </td>
        <td><%=operacion.getMoneda()%></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
