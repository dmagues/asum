<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<title>Registrar Usuario</title>
<link href="styles/styles.css" rel="Stylesheet" type="text/css">
</head>
<body>
<h1>Sargento Fitness</h1>
<h2>Para iniciar el entrenamiento necesitamos que nos ayudes con datos importantes</h2>
<form action="saveuser.jsp" method="post">

<div id="content">
<br/>
<div class="container">
<div class ="row">
	<div class="left"><span>Edad:</span></div>
	<div class="right"><input id="edad" name="edad" type="text" style="width: 70px; "/></div>
</div>
<div class ="row">
<div class="left"><span>Sexo:</span></div>
<div class="right"><select id="sexo" name="sexo"><option value=""></option><option value="H">Hombre</option><option value="M">Mujer</option></select></div>
</div>
<div class ="row">
<div class="left"><span>Peso:</span></div>
<div class="right"><input id="peso" name="peso" type="text" style="width: 70px; "/></div> 
</div>
<div class ="row">
<div class="left"><span>Altura:</span></div>
<div class="right"><input id="estatura" name="estatura" type="text" style="width: 70px; "/></div>
</div>
<div class ="row">
<input type="submit" value="Ok!">
</div>
</div>
</div>
</form>



 </body>
</html>