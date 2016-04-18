package com.asum.project.sgtfitness.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Usuario {

	private int usuarioId;
	
	private double peso; /*kilos*/
	private double estatura; /*metros*/
	private int edad;
	private char sexo;
	private String nombre;
	private String login;
	private String pwd;

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getEstatura() {
		return estatura;
	}

	public void setEstatura(double estatura) {
		this.estatura = estatura;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Usuario() {		
	}
	
	public Usuario(double peso, double estatura, int edad, char sexo, String nombre, String login, String pwd) {
		super();
		this.peso = peso;
		this.estatura = estatura;
		this.edad = edad;
		this.sexo = sexo;
		this.nombre = nombre;
		this.login = login;
		this.pwd = pwd;
	}

	public double getIMC() {		
		return  new BigDecimal(peso/Math.pow(estatura,2))
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();	
	}

}