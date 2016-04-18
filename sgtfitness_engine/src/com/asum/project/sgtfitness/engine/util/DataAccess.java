package com.asum.project.sgtfitness.engine.util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.swing.plaf.ActionMapUIResource;

import org.mariadb.jdbc.MariaDbDataSource;

import com.asum.project.sgtfitness.engine.*;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;




public class DataAccess extends MariaDbDataSource {
	
	
	public static List<ReglaEntrenamiento> consultaReglas(){
		
		List<ReglaEntrenamiento> reglas = new ArrayList<ReglaEntrenamiento>();
		Connection conn=null;
		
		JdbcRowSet reglasRowSet = null;			
		JdbcRowSet actividadesRowSet = null;
		JdbcRowSet diasRowSet = null;
		JdbcRowSet horasRowSet = null;
		
		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			
			conn=ds.getConnection();
			
			reglasRowSet = new JdbcRowSetImpl(conn);			
			actividadesRowSet = new JdbcRowSetImpl(conn);
			diasRowSet = new JdbcRowSetImpl(conn);
			horasRowSet = new JdbcRowSetImpl(conn);
			
			reglasRowSet.setCommand("SELECT regla_id, objetivo, edadInicio, edadFin, nivel FROM reglas_entrenamiento;");
			actividadesRowSet.setCommand("SELECT actividad_id, regla_id, tipo_actividad, subtipo_actividad FROM actividades_reglas;");
			diasRowSet.setCommand("SELECT actividad_id, dias_actividad FROM dias_actividad_regla;");
			horasRowSet.setCommand("SELECT actividad_id, horas_actividad FROM horas_actividad_regla;");
			
			reglasRowSet.execute();
			actividadesRowSet.execute();
			diasRowSet.execute();
			horasRowSet.execute();
			
			while(reglasRowSet.next()){
				int reglaID = reglasRowSet.getInt("regla_id");
				
				actividadesRowSet.beforeFirst();
				while (actividadesRowSet.next())
				{
					if(reglaID==actividadesRowSet.getInt("regla_id"))
					{
						ReglaEntrenamiento e = new ReglaEntrenamiento(reglasRowSet.getInt("objetivo")
							,reglasRowSet.getInt("edadInicio")
							,reglasRowSet.getInt("edadFin")
							,reglasRowSet.getInt("nivel")
							,null);
						
						int actividadID = actividadesRowSet.getInt("actividad_id");
						Actividad a = new Actividad();
						a.setTipoActividad(actividadesRowSet.getInt("tipo_actividad"));
						a.setSubtipoActividad(actividadesRowSet.getInt("subtipo_actividad"));
						
						List<Integer> dias = new ArrayList<Integer>();
						diasRowSet.beforeFirst();
						while(diasRowSet.next()){
							if(actividadID==diasRowSet.getInt("actividad_id"))
							{								
								dias.add(diasRowSet.getInt("dias_actividad"));
							}
						}
						
						List<Double> horas = new ArrayList<Double>();
						horasRowSet.beforeFirst();
						while(horasRowSet.next()){
							if(actividadID==horasRowSet.getInt("actividad_id"))
							{								
								horas.add(horasRowSet.getDouble("horas_actividad"));
							}
						}
						
						int[] arrDias = dias.parallelStream().mapToInt(Integer::intValue).toArray();
						double[] arrHoras = horas.parallelStream().mapToDouble(Double::doubleValue).toArray();
						
						a.setDias(arrDias);
						a.setHoras(arrHoras);
						
						e.setActividad(a);
						
						reglas.add(e);
					}// end if actividad.regla_id ==  regla_id
				}// end while actividad
			}// end while reglas
			
			reglasRowSet.close();			
			actividadesRowSet.close();
			diasRowSet.close();
			horasRowSet.close();
			
			return reglas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reglas;
		
	}
	

	public static Entrenamiento consultaEntrenamientoUsuario(Usuario usuario) {
		
		usuario = new Usuario();
		usuario.setEdad(22);
		usuario.setNombre("DANIEL MAGUES");
		usuario.setSexo('M');
		usuario.setEstatura(1.70);
		usuario.setPeso(80);
		usuario.setLogin("dmagues");
		usuario.setLogin("s3cret");
		usuario.setUsuarioId(1000);
		
		ControlEntrenamiento control = new ControlEntrenamiento(usuario);
		
		List<Integer> tipos =  new ArrayList<Integer>();
		tipos.add(TipoActividad.CARDIO);
		
		List<Integer> subtipos =  new ArrayList<Integer>();
		subtipos.add(TipoActividad.CORRER);
		subtipos.add(TipoActividad.MONTAR_BICICLETA);
			
		
		Entrenamiento e = control.generaEntrenamiento(Objetivos.SALUD_GENERAL, 3, 1, tipos, subtipos);
		
		List<Actividad> resultados = new ArrayList<Actividad>();
		resultados.add(new Actividad(new int[]{2},new double[]{1},TipoActividad.CARDIO, TipoActividad.CORRER));
		resultados.add(new Actividad(new int[]{2},new double[]{4},TipoActividad.CARDIO, TipoActividad.MONTAR_BICICLETA));
		
		e.setResultados(resultados);
		
		e.evaluarEntrenamiento();
		
		return e;
	}


	public static double consultaTasaExigenciaPorIMC(double imc) {
		double tasa = 0;
		
		if (imc < 18.5) // Bajo peso
			tasa = 0.75;
		
		if (imc >=18.5 && imc<25) // Normal
			tasa = 1;
		
		if (imc >=25 && imc<30) // Sobrepeso
			tasa = 1.25;
		
		if (imc >=30 && imc<40) // Obesidad
			tasa = 1.50;
		
		if (imc >=40) // Obesidad morbosa
			tasa = 1.75;
		
		return tasa;
	}
	
	public static int consultaNivelPorIMC(double imc) {
		int nivel = 0;
		
		if (imc < 18.5) // Bajo peso
			nivel = 0;
		
		if (imc >=18.5 && imc<25) // Normal
			nivel = 1;
		
		if (imc >=25 && imc<30) // Sobrepeso
			nivel = 2;
		
		if (imc >=30 && imc<40) // Obesidad
			nivel = 3;
		
		if (imc >=40) // Obesidad morbosa
			nivel = 4;
		
		return nivel;
	}
	

}
