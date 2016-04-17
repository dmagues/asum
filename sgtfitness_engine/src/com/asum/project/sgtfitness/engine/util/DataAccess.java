package com.asum.project.sgtfitness.engine.util;


import java.util.ArrayList;
import java.util.List;

import com.asum.project.sgtfitness.engine.*;



public class DataAccess {
	
	
	public static List<ReglaEntrenamiento> consultaReglas()
	{
	
		List<ReglaEntrenamiento> reglas = new ArrayList<ReglaEntrenamiento>();
		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,0.5,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,1.5,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,2,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		
		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,0.75,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,1.25,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,1.50,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		
				
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,0.50,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,0.75,TipoActividad.CARDIO,TipoActividad.CORRER)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,1.25,TipoActividad.CARDIO,TipoActividad.CORRER)
				));
		
		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,2,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,3,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,20,25,1
				,new Actividad(3,4,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		
		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,1.5,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,2,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,25,30,1
				,new Actividad(3,2.50,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		
				
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,0.75,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,1,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));		
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,1.25,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		reglas.add(new ReglaEntrenamiento(Objetivos.SALUD_GENERAL,30,35,1
				,new Actividad(3,1.5,TipoActividad.CARDIO,TipoActividad.MONTAR_BICICLETA)
				));
		
		return reglas;
		
	}

	public static Entrenamiento consultaEntrenamientoUsuario(Usuario usuario) {
		
		Entrenamiento e = new Entrenamiento(usuario);
		return e;
	}


	public static double consultarTasaExigenciaPorIMC(double imc) {
		double tasa = 0;
		
		if (imc < 18.5) // Bajo peso
			tasa = 0.75;
		
		if (imc >=18.5 && 25<imc) // Normal
			tasa = 1;
		
		if (imc >=25 && 30<imc) // Sobrepeso
			tasa = 1.25;
		
		if (imc >=30 && 40<imc) // Obesidad
			tasa = 1.50;
		
		if (imc >=40) // Obesidad morbosa
			tasa = 1.25;
		
		return tasa;
	}
	
	

}
