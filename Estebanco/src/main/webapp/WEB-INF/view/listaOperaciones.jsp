<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.TipoOperacionEntity" %>
<%@ page import="es.estebanco.estebanco.dto.OperacionEntityDto" %>
<%@ page import="es.estebanco.estebanco.dto.TipoOperacionEntityDto" %><%--
  Created by IntelliJ IDEA.
  User: ferli
  Date: 29/03/2023
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  List<OperacionEntityDto> listaOperaciones = (List<OperacionEntityDto>) request.getAttribute("operaciones");
  List<TipoOperacionEntityDto> tipos = (List<TipoOperacionEntityDto>) request.getAttribute("tipos");
%>

<html>
<head>
    <title>Listado operaciones</title>
</head>
<body>

<form:form action="/cajero/filtrar" method="post" modelAttribute="filtro">
    IBAN DESTINO TRANSFERENCIA: <form:input path="texto"></form:input>
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
        <th>ID</th>
        <th>Fecha</th>
        <th>Tipo</th>
        <th>Cuenta origen</th>
        <th>Cantidad</th>
        <th>Moneda</th>
        <th>Cuenta destino transferencia</th>
    </tr>
    <%
        for (OperacionEntityDto operacion:listaOperaciones){
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCuentaByCuentaId().getIban()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getMoneda()%></td>
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
    </tr>
    <%
        }
    %>
</table>
<a href="/cajero/">Volver</a>
<a href="/cajeroLogin/logout">Salir</a>
</body>
</html>
