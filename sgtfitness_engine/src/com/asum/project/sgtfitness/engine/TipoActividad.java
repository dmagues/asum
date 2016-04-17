package com.asum.project.sgtfitness.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoActividad {

  public static final int CARDIO = 1;
  public static final int PESAS = 2;
  
  public static int CORRER = 1;
  public static int MONTAR_BICICLETA = 2;
  public static int BAILE = 3;
  public static int AEROBICOS = 4;
  public static int LOCALIZADOS = 5;
  public static int PESO_LIBRE = 6;

  
  public static final Map<Integer, String> TIPOS_ACTIVIDAD;
  public static final Map<Integer, String> SUBTIPOS_CARDIO;
  public static final Map<Integer, String> SUBTIPOS_PESAS;
  
  
  static{
	  TIPOS_ACTIVIDAD = new HashMap<Integer, String>();
	  TIPOS_ACTIVIDAD.put(CARDIO, "Cardio");
	  TIPOS_ACTIVIDAD.put(PESAS, "Pesas");
	  
	  SUBTIPOS_CARDIO =  new HashMap<Integer, String>();
	  SUBTIPOS_CARDIO.put(CORRER, "Correr");
	  SUBTIPOS_CARDIO.put(MONTAR_BICICLETA, "Montar Bicicleta");
	  SUBTIPOS_CARDIO.put(BAILE, "Baile");
	  SUBTIPOS_CARDIO.put(AEROBICOS, "Aeróbicos");
	  
	  SUBTIPOS_PESAS =  new HashMap<Integer, String>();
	  SUBTIPOS_PESAS.put(LOCALIZADOS, "Peso Localizado");
	  SUBTIPOS_PESAS.put(PESO_LIBRE, "Peso Libre");	  	  
  }
  
  public static List<String> getTiposActividad()
  {
	  return new ArrayList<String>(TIPOS_ACTIVIDAD.values());
  }
  
  public static List<String> getSubtipoActividadPorTipo(int tipo)
  {
	  List<String> subtipos = null;
	  switch(tipo)
	  {
	  case CARDIO:
		  subtipos = new ArrayList<String>(SUBTIPOS_CARDIO.values());
	  case PESAS:
		  subtipos = new ArrayList<String>(SUBTIPOS_PESAS.values());	  
	  }
	  return subtipos;
  }
  
  public static String getNombreTipoActividad(int tipo)
  {
	  if(TIPOS_ACTIVIDAD.containsKey(tipo))
	  {
		  return TIPOS_ACTIVIDAD.get(tipo);
	  }
	  return null;
  }
  
  public static String getNombreSubtipoActividad(int subtipo)
  {
	  if(SUBTIPOS_CARDIO.containsKey(subtipo))
	  {
		  return SUBTIPOS_CARDIO.get(subtipo);
	  }
	  if(SUBTIPOS_PESAS.containsKey(subtipo))
	  {
		  return SUBTIPOS_PESAS.get(subtipo);
	  }	  
	  return null;
  }

}