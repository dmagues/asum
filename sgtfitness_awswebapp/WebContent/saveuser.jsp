<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="usuario" class="com.asum.project.sgtfitness.Usuario" scope="session"></jsp:useBean>
<jsp:setProperty name="usuario" property="*" />
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nuevo Usuario</title>
</head>
<body>

<h2>Usuario registrado con éxito</h2>

<a href="showuser.jsp">Continuar</a>

</body>
</html>