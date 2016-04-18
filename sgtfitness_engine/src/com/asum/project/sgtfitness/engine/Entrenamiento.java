package com.asum.project.sgtfitness.engine;

import java.util.List;

public class Entrenamiento {

	private int objetivo;
	private int diasDisponible;
	private double horasDisponible;
	private List<Integer> preferenciaTipoActividad;
	private List<Integer> preferenciaSubtipoActividad;
	private Usuario usuario;
	private List<Actividad> actividades;
	private List<Actividad> resultados;
	
	public Entrenamiento(Usuario usuario){
		this.usuario=usuario;
	}

	public int getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(int objetivo) {
		this.objetivo = objetivo;
	}
	public int getDiasDisponible() {
		return diasDisponible;
	}

	public void setDiasDisponible(int diasDisponible) {
		this.diasDisponible = diasDisponible;
	}
	public double getHorasDisponible() {
		return horasDisponible;
	}

	public void setHorasDisponible(double horas) {
		this.horasDisponible = horas;
	}
	
	public List<Integer> getPreferenciaTipoActividad() {
		return preferenciaTipoActividad;
	}

	public void setPreferenciaTipoActividad(List<Integer> preferenciaTipoActividad) {
		this.preferenciaTipoActividad = preferenciaTipoActividad;
	}

	public List<Integer> getPreferenciaSubtipoActividad() {
		return preferenciaSubtipoActividad;
	}

	public void setPreferenciaSubtipoActividad(List<Integer> preferenciaSubtipoActividad) {
		this.preferenciaSubtipoActividad = preferenciaSubtipoActividad;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Actividad> getActividades() {
		return actividades;
	}
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	public List<Actividad> getResultados() {
		return resultados;
	}
	public void setResultados(List<Actividad> resultados) {
		this.resultados = resultados;
	}
	
	public String toString()
	{
		String s="";
		
		s+="\nNombre: " + this.usuario.getNombre();
		s+=String.format("\nIMC: %.2f",this.usuario.getIMC());
		s+=String.format("\nDias disponibles: %d \tHoras Disponibles: %.2f",this.diasDisponible, this.horasDisponible);
		s+="\nPreferencias Actividades:";
		for(int t:preferenciaTipoActividad)
		{
			s+="\n\t"+TipoActividad.getNombreTipoActividad(t);
		}
		s+="\nPreferencias:";
		for(int st:preferenciaSubtipoActividad)
		{
			s+="\n\t\t"+TipoActividad.getNombreSubtipoActividad(st);
		}
		s+="\nEntrenamiento:";
		for(Actividad a:this.actividades)
		{
			s+="\n\t"+a.toString();
		}
		
		return s;
	}
	
	public void evaluarEntrenamiento(){
		
		for(Actividad plan:actividades)
		{
			int dias = resultados.stream()
					.filter(r->r.getTipoActividad()==plan.getTipoActividad()&& r.getSubtipoActividad()==plan.getSubtipoActividad())
					.mapToInt(d->d.getDia())
					.sum();
			
			double horas = resultados.stream()
					.filter(r->r.getTipoActividad()==plan.getTipoActividad()&& r.getSubtipoActividad()==plan.getSubtipoActividad())
					.mapToDouble(d->d.getHora())
					.sum();
			
			plan.setTasaResultado((dias*horas)/(plan.getDia()*plan.getHora()));			
			
		}
		
		
	}



}