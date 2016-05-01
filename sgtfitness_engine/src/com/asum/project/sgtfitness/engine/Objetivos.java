package com.asum.project.sgtfitness.engine;

import java.util.HashMap;
import java.util.Map;

public class Objetivos {

  public static int SALUD_GENERAL =1;
  public static int BAJAR_PESO=2;
  public static int TONIFICACION=3;
  public static int MASA_MUSCULAR=4;
  
  private static final Map<Integer, String> DESCRIPCION_OBJETIVOS;
  
  static{
	  DESCRIPCION_OBJETIVOS = new HashMap<Integer, String>();
	  DESCRIPCION_OBJETIVOS.put(SALUD_GENERAL, "Salud General");
	  DESCRIPCION_OBJETIVOS.put(BAJAR_PESO, "Bajar de Peso");
	  DESCRIPCION_OBJETIVOS.put(TONIFICACION, "Tonificaci&ocaute;n");
	  DESCRIPCION_OBJETIVOS.put(MASA_MUSCULAR, "Masa Muscular");
  }
  
  public static String getDescripcionObjetivo(int objetivo)
  {
	  if(DESCRIPCION_OBJETIVOS.containsKey(objetivo))
	  {
		  return DESCRIPCION_OBJETIVOS.get(objetivo);
	  }
	  return null;
  }

}