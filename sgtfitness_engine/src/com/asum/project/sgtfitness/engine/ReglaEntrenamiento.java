package com.asum.project.sgtfitness.engine;


public class ReglaEntrenamiento {

	private int objetivo;
	private int edadInicio;
	private int edadFin;
	private int nivel;
	private Actividad actividad;

	public ReglaEntrenamiento()
	{}
	
	public ReglaEntrenamiento(int objetivo, int edadInicio, int edadFin,  int nivel, Actividad actividad)
	{
		this.objetivo = objetivo;
		this.edadInicio = edadInicio;
		this.edadFin = edadFin;
		this.actividad = actividad;
		this.nivel = nivel;
	}
	public int getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(int objetivo) {
		this.objetivo = objetivo;
	}
	public Actividad getActividad() {
		return actividad;
	}
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public boolean esRangoEdadValido(int edad)
	{
		return (edadInicio<=edad && edad<edadFin);
	}
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}