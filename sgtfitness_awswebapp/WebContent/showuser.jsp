<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="usuario" class="com.asum.project.sgtfitness.engine.Usuario" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tus Datos</title>
</head>
<body>
Tus Datos<BR>
Edad: <%= usuario.getEdad() %><BR>
Sexo: <%= usuario.getSexo() %><BR>
Peso: <%= usuario.getPeso() %><BR>
Estatura: <%= usuario.getEstatura() %><BR>
Indice de Masa Corporal: <%= usuario.getIndiceMasaCorporal() %><BR>
</body>
</html>