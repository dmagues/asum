package com.asum.project.sgtfitness.engine;



public class Actividad {

	private int[] dias;
	private double[] horas;
	private int tipoActividad;
	private int subtipoActividad;
	private double tasaResultado;

	public Actividad()
	{}

	public Actividad(int[] dias, double[] horas, int tipo, int subtipo)  
	{
		this.dias=dias;
		this.horas=horas;
		this.tipoActividad = tipo;
		this.subtipoActividad = subtipo;	  

	}

	public int[] getDias() {
		return dias;
	}
	public int getDia() {
		if (dias.length==1)
			return dias[0];
		return 0;		 
	}
	public void setDias(int[] dias) {
		this.dias = dias;
	}
	public double[] getHoras() {
		return horas;
	}
	
	public double getHora() {
		if (horas.length==1)
			return horas[0];
		return 0;
	}
	public void setHoras(double[] horas) {
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
	
	public double getTasaResultado() {
		return tasaResultado;
	}

	public void setTasaResultado(double tasaResultado) {
		this.tasaResultado = tasaResultado;
	}

	public String toString(){
		 String s = String.format("\nActividad %s\\%s", 
				TipoActividad.getNombreTipoActividad(this.tipoActividad)
				,TipoActividad.getNombreSubtipoActividad(this.subtipoActividad)
				);
		 
		 s+="\n\tDías: ";
		 for(int d:this.getDias())
			 s+=String.format("\t%d", d);		 
		 
		 s+="\n\tHoras: ";
		 for(double h:this.getHoras())
			 s+=String.format("\t%.2f", h);
		 
		 s+=String.format("\nEvaluación: %.2f%%", this.tasaResultado*100);
		 
		 return s;
	}

	public void setDia(int dias) {
			this.dias =  new int[]{dias};		
	}

	public void setHora(double horas) {
		this.horas =  new double[]{horas};		
	}
		


}