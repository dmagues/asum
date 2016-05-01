<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Mis Resultados</title>
<%@ include file="common.jsp" %>
<script>
$(document).ready(function() {
	
	 
	
	
	$("#resultado").validate({
	errorClass: "invalid",
	errorElement: "em",
	
	errorLabelContainer: "#errorContainer",	
	wrapper: "li",	
	rules: {
		login: {
			required: true,
		},
		horas:{
			required: true,
			number: true,
			range: [0,8]			
		},
		fecha:{
			required: true
		},
		actividad:
		{
			required: true
		}
		
		},
	messages: {
		login: {
 			required: "Debe especificar un login existente"

	    },
		horas: {
 			required: "Necesitamos tus horas disponibles en el día",
	      	number: "Las horas deben ser un número",
	      	range: "Solo entre 1 y 8 horas del día"
	    },
	    fecha:{
	    	required: "Debe seleccionar una fecha"
	    },
	    actividad:{
	    	required: "Debe seleccionar una actividad"
	    },
	}
		    		
	
	});
	
});
</script>
</head>
<body>
<h1>Sargento Fitness</h1>
<br/>

<div>
	<div class="container">
	<form method="post" action="trainer?mode=results" id="resultado">
<fieldset>
<legend>Resultados</legend>
	<div class="row">
	<div class="cell"><label for="login">Usuario:</label></div>
	<div class="cell"><input type="text" id="login" name="login" /></div>
	</div><br/>
	<div class="row">
	<div class="cell"><label for="fecha">Fecha:</label></div>
	<div class="cell"><input type="text" id="fecha" name="fecha" /></div>
	</div><br/>
	<div class="row">
	<div class="cell"><label for="actividad">Actividad:</label></div>
	<div class="cell">
    <select name="actividad" id="actividad">
    	<option value=""></option>
      <optgroup label="Cardio">
        <option value="1">Correr</option>
        <option value="2">Montar Bicicleta</option>
        <option value="3">Baile</option>
        <option value="4">Aer&oacute;bicos</option>
      </optgroup>
      <optgroup label="Pesas">
        <option value="5">Peso Localizado</option>
        <option value="6">Peso Libre</option>
      </optgroup>
    </select></div>
    
	</div><br/>

	<div class="row">
	<div class="cell"><label for="horas">Horas:</label></div>
	<div class="cell"><input type="text" id="horas" name="horas" /></div>
	</div><br/>
	<div class="container">
		<div class="cell" >
			<input type="submit" value="Aceptar"/>
		</div>
	</div>
	<div class="container">
		<div class="cell" id="errorContainer">	
			<ul></ul>				
		</div>
	</div>
</fieldset>
</form>
		
	</div>
</div>	

</body>
</html>