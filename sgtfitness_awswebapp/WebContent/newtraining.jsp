<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nuevo Entrenamiento</title>
<%@ include file="common.jsp" %>
<script>
$(document).ready(function() {
		
		 $.validator.addMethod("activityRequired", 
		 						$.validator.methods.required, 
		 						"Seleccione por lo menos una actividad");
 		
		jQuery.validator.addClassRules("actividad", {
  			activityRequired: function(element){
            					return  $('.actividad:checked').length == 0 ;} 
 
		});
		
		
		
		$("#preferencias").validate({
		errorClass: "invalid",
		errorElement: "em",
		
		errorLabelContainer: "#errorContainer",	
		wrapper: "li",	
		rules: {
    		dias: {
    			required: true,      			
      			digits: true,
      			range: [1,7]
    		},
    		horas:{
    			required: true,
    			number: true,
    			range: [1,6]
    			
    		},
    		objetivo:{
    			required: true
    		}
    		
  		},
		messages: {
    		dias: {
      			required: "Necesitamos tus días disponibles a la semana",
		      	digits: "Los días deben ser un número",
		      	range: "Solo entre 1 y 7 días de la semana"
		    },
    		horas: {
      			required: "Necesitamos tus horas disponibles en el día",
		      	number: "Las horas deben ser un número",
		      	range: "Solo entre 1 y 6 horas del día"
		    },
		    objetivo:{
		    	required: "Debe seleccionar el objetivo del entrenamiento"
		    }    		 
    	}
    		    		
		
		});
		
	});
</script>
</head>
<body>
<img style="position: absolute; right: 5%; top: 10px; width: 150px; height: 80px" alt="logo" src="images\logo.jpg" />
<h2>Para iniciar el entrenamiento debes completar el siguiente formulario:</h2>
<div id="content">
<form method="post" action="trainer?mode=new" id="preferencias">
<fieldset>
<legend>Preferencias</legend>
	<div class="container">
			<div class="row">
				<div class="cell"><label for="dias">Días disponibles:</label></div>
				<div class="cell"><input type="text" id="dias" name="dias" /></div></div>
			<div class="row">
				<div class="cell"><label for="horas">Horas disponibles:</label></div>
				<div class="cell"><input type="text" id="horas" name="horas" /></div>
				</div>
			<div class="row">
				<div class="cell"><label for="objetivo">Objetivo:</label></div>
				<div class="cell">
					<select name="objetivo" id="objetivo">
						<option value="">Seleccionar...</option>
						<option value="1">Salud General</option>
						<option value="2">Bajar de Peso</option>
						<option value="3">Tonificaci&oacute;n</option>
						<option value="4">Masa Muscular</option>	 
					</select> 
					</div>
				</div>	
				
			</div>
				
			<div class="container">
			<div class="row">
			<fieldset> 
			<legend>Actividades preferidas:</legend>	
			<div class="row">
				<div class="cell"><label for="cardio">Cardio</label></div><div class="cell"><label for="pesas">Pesas</label></div>
			</div>
			<div class="row">
				<div class="cell">
						<input class="actividad" type="checkbox" name="cardio" id="cardio_correr" value="1">Correr<br/>
						<input class="actividad" type="checkbox" name="cardio" id="cardio_bicicleta" value="2">Montar Bicicleta<br/>
						<input class="actividad" type="checkbox" name="cardio" id="cardio_bailar" value="3">Bailar<br/>
						<input class="actividad" type="checkbox" name="cardio" id="cardio_aerobicos" value="4">Aer&oacute;bicos<br/>						
					</div>
					<div class="cell">
						<input class="actividad" type="checkbox" name="pesas" id="pesas_localizado" value="5">Localizados<br/>
						<input class="actividad" type="checkbox" name="pesas" id="pesas_peso_libre" value="6">Peso libre<br/>												
					</div>
			</div>			
			</fieldset>
			</div>
			</div>
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
</body>
</html>