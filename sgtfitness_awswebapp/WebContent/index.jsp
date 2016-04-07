<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrar Usuario</title>
</head>
<body>
<h1>Sargento Fitness</h1>
<h2>Para iniciar el entrenamiento necesitamos que nos ayudes con datos importantes</h2>
<form action="saveuser.jsp" method="post">

<div id="datos">
<span>Edad:</span><input id="edad" name="edad" type="text" /><br/>
<span>Sexo:</span><input id="sexo" name="sexo" type="text" /><br/>
<span>Peso:</span><input id="peso" name="peso" type="text" /><br/>
<span>Altura:</span><input id="estatura" name="estatura" type="text" /><br/>
<input type="submit" value="Ok!">

</div>
</form>



 </body>
</html>