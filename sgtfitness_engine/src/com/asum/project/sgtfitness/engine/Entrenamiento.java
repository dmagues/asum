package com.asum.project.sgtfitness.engine;

import java.io.Serializable;
import java.util.List;

public class Entrenamiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1588439623986434288L;
	public static final int ACTIVO=1;
	public static final int INACTIVO=2;
	
	private int objetivo;
	private int diasDisponible;
	private double horasDisponible;
	private List<Integer> preferenciaTipoActividad;
	private List<Integer> preferenciaSubtipoActividad;
	private Usuario usuario;
	private List<Actividad> actividades;
	private List<Actividad> resultados;
	private int entrenamientoId = 0;
	
	

	
	public int getEntrenamientoId() {
		return entrenamientoId;
	}

	public void setEntrenamientoId(int entrenamientoId) {
		this.entrenamientoId = entrenamientoId;
	}

	public Entrenamiento(){}
	
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
		
		s+=String.format("\nID: %d\tNombre: %s" , this.getEntrenamientoId(), this.usuario.getNombre());
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
			if(resultados!=null)
			{
				int dias = (int) resultados.stream()
						.filter(r->r.getTipoActividad()==plan.getTipoActividad()&& r.getSubtipoActividad()==plan.getSubtipoActividad())
						.mapToInt(d->d.getDia())
						.count();

				double horas = resultados.stream()
						.filter(r->r.getTipoActividad()==plan.getTipoActividad()&& r.getSubtipoActividad()==plan.getSubtipoActividad())
						.mapToDouble(d->d.getHora())
						.sum();
				plan.setTasaResultado((dias*horas)/(plan.getDia()*plan.getHora()));
			}
			else
				plan.setTasaResultado(0);			

		}
		
		
	}



}