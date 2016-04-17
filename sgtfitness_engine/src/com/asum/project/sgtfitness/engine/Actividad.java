package com.asum.project.sgtfitness.engine;



public class Actividad {

	private int dias;
	private double horas;
	private int tipoActividad;
	private int subtipoActividad;

	public Actividad()
	{}

	public Actividad(int dias, double horas, int tipo, int subtipo)  
	{
		this.dias=dias;
		this.horas=horas;
		this.tipoActividad = tipo;
		this.subtipoActividad = subtipo;	  

	}

	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public double getHoras() {
		return horas;
	}
	public void setHoras(double horas) {
		this.horas = horas;
	}
	public int getTipoActividad() {
		return tipoActividad;
	}
	public void setTipoActividad(int tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public int getSubtipoActividad() {
		return subtipoActividad;
	}
	public void setSubtipoActividad(int subtipoActividad) {
		this.subtipoActividad = subtipoActividad;
	}
	
	public String toString(){
		 String s = String.format("\nActividad %s\\%s\n\tDias: %d Horas: %.2f", 
				TipoActividad.getNombreTipoActividad(this.tipoActividad)
				,TipoActividad.getNombreSubtipoActividad(this.subtipoActividad)
				,this.getDias()
				,this.getHoras());
		 return s;
	}
		


}