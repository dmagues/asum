package com.asum.project.sgtfitness;


public class Usuario {
	String edad;
	String sexo;
	String peso;
	String estatura;
	
	/**
	 * @return the edad
	 */
	public String getEdad() {
		return edad;
	}
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(String edad) {
		this.edad = edad;
	}
	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}
	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	/**
	 * @return the peso
	 */
	public String getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}
	/**
	 * @return the estatura
	 */
	public String getEstatura() {
		return estatura;
	}
	/**
	 * @param estatura the estatura to set
	 */
	public void setEstatura(String estatura) {
		this.estatura = estatura;
	}
	
	public float getIndiceMasaCorporal()
	{
		return Integer.parseInt(peso)/Float.parseFloat(estatura)* Float.parseFloat(estatura);
	}

	
}
