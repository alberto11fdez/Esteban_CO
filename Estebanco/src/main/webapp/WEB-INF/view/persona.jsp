<%@ page import="es.estebanco.estebanco.entity.PersonaEntity" %>
<%@ page import="es.estebanco.estebanco.entity.CuentaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.estebanco.estebanco.entity.OperacionEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PersonaEntity persona = (PersonaEntity) request.getAttribute("persona");
    List<CuentaEntity> cuentas = (List<CuentaEntity>) request.getAttribute("cuentas");
    List<OperacionEntity> operaciones = (List<OperacionEntity>) request.getAttribute("operaciones");
%>
<html>
<head>
    <title>Pefil</title>
</head>
<body>
<h1>Datos personales:</h1>

<ol>
    <li>Dni: <%=persona.getDni()%></li>
    <li>Nombre: <%=persona.getNombre()%></li>
    <li>Primer apellido: <%=persona.getApellido1()%></li>
    <li>Segundo apellido: <%=persona.getApellido2()%></li>
    <li>Correo: <%=persona.getCorreo()%></li>
    <li>Dirección: <%=persona.getDireccion()%></li>
    <li>Nombre de usuario: <%=persona.getUsuario()%></li>
    <li>Contraseña: <%=persona.getContraseña()%></li>
    <li>Estado de la cuenta: <%=persona.getEstado()%></li>
</ol>
<button>Modificar datos</button>
<h2>Cuentas:</h2>
<table border="1">
    <tr>
        <th>IBAN</th>
        <th>SALDO</th>
        <th>MONEDA</th>
        <th>ESTADO</th>
        <th>FECHA DE APERTURA</th>
    </tr>
    <%
        for(CuentaEntity cuenta: cuentas){
    %>
    <tr>
        <td><%=cuenta.getIban()%></td>
        <td><%=cuenta.getSaldo()%></td>
        <td><%=cuenta.getMoneda()%></td>
        <td><%=cuenta.getEstado()%></td>
        <td><%=cuenta.getFechaApertura()%></td>
        <td><a href="">Realizar operación</a></td>
        <td><a href="">Realizar cambio de divisa</a></td>
        <td><a href="">Activar/Desactivar cuenta</a></td>
    </tr>
<%
    }
%>
</table border="1">
<h2>Operaciones:</h2>
<button>Filtrar</button>
    <%
    if(operaciones==null||operaciones.isEmpty()){
    %>
<h3>No hay operaciones</h3>
<%
    }else{
%>
<table border="1">
    <tr>
        <th>ID</th>
        <th>FECHA DE OPERACIÓN</th>
        <th>TIPO</th>
        <th>CANTIDAD</th>
        <th>MONEDA</th>
        <th>CUENTA DESTINO</th>
    </tr>
    <%
        for(OperacionEntity operacion: operaciones){
    %>
    <tr>
        <td><%=operacion.getIdOperacion()%></td>
        <td><%=operacion.getFechaOperacion()%></td>
        <td><%=operacion.getTipo()%></td>
        <td><%=operacion.getCantidad()%></td>
        <td><%=operacion.getMoneda()%></td>
        <td><%=operacion.getIbanCuentaDestinoOrigen()%></td>
    </tr>
    <%
        }
        }
    %>
</table border="1">
</body>
</html>