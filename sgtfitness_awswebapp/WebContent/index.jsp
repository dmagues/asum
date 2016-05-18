<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<%@ include file="common.jsp" %>
<script>
$(document).ready(function() {
		$("#form1").validate({
		errorClass: "invalid",
		errorElement: "em",		
		errorLabelContainer: "#errorContainer",	
		wrapper: "li",	
		rules: {
    		login: {
    			required: true,      			
      			maxlength: 10
    		}

  		},
		messages: {
    		login: {
      			required: "Debe ingrear su login.",
		      	maxlength: jQuery.validator.format("El login es de {0} caracteres.")
		    }    		 
    	}    		    		
		
		});
		
	});
</script>
</head>
<body>
<img style="position: absolute; right: 5%; top: 10px; width: 150px; height: 80px" alt="logo" src="images\logo.jpg" />
<h1>Sargento Fitness
	</h1>
<h2>Bienvenidos.</h2>
<br/>
<div id="content">
<br />
<form action="trainer" method="post" id="form1">
<div class="container">	
		<div class="row">
			<div class="cell">Login:</div><div class="cell"><input type="text" id="login" name="login"></div>
		</div>
		<div class="row">
			<div class="cell">Password:</div><div class="cell"><input type="password" id="pwd" name="pwd"></div>
		</div>
		<div class="row">
			<input type="submit" value="Aceptar" />
		</div>
	</div>	
	<div class="container">
				<div class="cell" id="errorContainer">	
					<ul></ul>				
				</div>
			</div>
	</form>
</div>

</body>
</html>