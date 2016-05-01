<%@page import="com.asum.project.sgtfitness.engine.TipoActividad"%>
<%@page import="com.asum.project.sgtfitness.engine.Objetivos"%>
<%@page import="com.asum.project.sgtfitness.engine.Entrenamiento"%>
<%@page import="com.asum.project.sgtfitness.engine.Actividad"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="unUsuario" class="com.asum.project.sgtfitness.engine.Usuario" scope="session"></jsp:useBean>
<jsp:useBean id="unEntrenamiento" class="com.asum.project.sgtfitness.engine.Entrenamiento" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mi Entrenamiento</title>
<%@ include file="common.jsp" %>

</head>
<body>
<img style="position: absolute; right: 5%; top: 10px; width: 150px; height: 80px" alt="logo" src="images\logo.jpg" />
<h1>Sargento Fitness</h1>
<h2>Hola <%= unUsuario.getNombre() %>.</h2>
<br/>

<div id="content">

<div class="container">
<br/>
<h2>Tu Entrenamiento:</h2>
<br/>

	<% for(Actividad a:unEntrenamiento.getActividades()){ %>
	<div class="row"><div class="cell"><%= a.toString()  %></div>
	<div class=cell><%= String.format("%d día(s) a la semana. Al menos %.2f horas por día", a.getDia(), a.getHora()) %></div></div>
	<%} %>
	<h3>Esfuerzo realizado:</h3>
	<% for(Actividad a:unEntrenamiento.getActividades()){ %>
	<div class="row"><div class="cell"><%= a.toString()  %></div>
	<div class=cell><%= String.format("%.2f%%", a.getTasaResultado()*100	) %></div></div>
	
	<%} %>
	<div class="row"><a href="#" id="preference">Tus preferencias</a></div>
	<div class="row"><a href="#" id="closer">Completar entrenamiento</a></div>
</div>

<div id="dialog" title="Tus preferencias">
	<div class="container">
		
		<h3>Actividades preferidas:</h3>
		<div class="row"><%= String.format("Objetivo: %s" ,Objetivos.getDescripcionObjetivo(unEntrenamiento.getObjetivo())) %></div>
		
		<% for(int t:unEntrenamiento.getPreferenciaTipoActividad()){ %>
		<div class="row"><%= TipoActividad.getNombreTipoActividad(t) %></div>
		<%} %>		
		<div class="row"></div>
		<% for(int t:unEntrenamiento.getPreferenciaSubtipoActividad()){ %>
		<div class="row"><%= TipoActividad.getNombreSubtipoActividad(t) %></div>
		<%} %>
		<br/>
		<div class="row">
			<div class="cell"><%= String.format("Días disponibles: %d", unEntrenamiento.getDiasDisponible()) %></div>
		</div>	
		<div class="row">
			<div class="cell"><%= String.format("Horas disponibles: %.2f", unEntrenamiento.getHorasDisponible()) %></div>
		</div>				
	</div>
</div>

<div id="dialog-confirm" title="¿Terminar el Entrenamiento?">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		¿Vamos a terminar el entrenamiento?</p>
</div>
</div>
</body>
</html>