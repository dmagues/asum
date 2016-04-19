package com.asum.project.sgtfitness.engine.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.JdbcRowSet;
import com.sun.rowset.JdbcRowSetImpl;

import com.asum.project.sgtfitness.engine.*;






public class DataAccess {




	public static Usuario guardaUsuario(Usuario usuario)
	{
		Usuario u = consultaUsuarioPorLogin(usuario.getLogin());
		if (u!=null) return u; 

		Connection conn = null;
		PreparedStatement pstmt = null;


		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO usuarios_info "
					+ "(nombre, edad, sexo, estatura, peso, login, pwd)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, usuario.getNombre());
			pstmt.setInt(2, usuario.getEdad());
			pstmt.setString(3, usuario.getSexo());
			pstmt.setDouble(4, usuario.getEstatura());
			pstmt.setDouble(5, usuario.getPeso());
			pstmt.setString(6, usuario.getLogin());
			pstmt.setString(7, usuario.getPwd());


			pstmt.execute();

			ResultSet keys =  pstmt.getGeneratedKeys();
			if (keys!= null)
			{
				int id= 0;
				while (keys.next())
				{
					id = keys.getInt(1);
				}			
				usuario.setUsuarioId(id);
				return usuario;

			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{

			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return usuario;


	}



	public static Entrenamiento guardaEntrenamiento(Entrenamiento e)
	{
		Entrenamiento actual = consultaEntrenamientoUsuario(e.getUsuario());
		if (actual!=null) return actual;

		Connection conn = null;
		PreparedStatement pstmt = null;
		int id= 0;

		try {

			conn = DataSourceFactory.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("INSERT INTO entrenamientos "
					+ "(objetivo, diasDisponibles, horasDisponibles, usuario_id, estado)"
					+ " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, e.getObjetivo());
			pstmt.setInt(2, e.getDiasDisponible());
			pstmt.setDouble(3, e.getHorasDisponible());
			pstmt.setInt(4, e.getUsuario().getUsuarioId());
			pstmt.setInt(5, Entrenamiento.ACTIVO);

			pstmt.execute();

			ResultSet keys =  pstmt.getGeneratedKeys();
			if (keys!= null)
			{

				while (keys.next())
				{
					id = keys.getInt(1);
				}		


			}else
				throw new Exception("Error al generar el id del entrenamiento");


			e.setEntrenamientoId(id);

			guardaPreferenciasEntrenamiento(e);
			guardaActividadesEntrenamiento(e);

			conn.commit();

			return e;


		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		catch (Exception e2) {
			e2.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally
		{
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

		}
		return e;
	}


	private static void guardaPreferenciasEntrenamiento(Entrenamiento ent)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;


		try {

			conn = DataSourceFactory.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO preferencias_tipo_actividad "
					+ "(entrenamiento_id, tipo_actividad)"
					+ " VALUES (?, ?)");

			for(int t:ent.getPreferenciaTipoActividad())
			{
				pstmt.setInt(1, ent.getEntrenamientoId());
				pstmt.setInt(2, t);
				pstmt.execute();
			}

			pstmt.close();

			pstmt = conn.prepareStatement("INSERT INTO preferencias_subtipo_actividad "
					+ "(entrenamiento_id, subtipo_actividad)"
					+ " VALUES (?, ?)");

			for(int st:ent.getPreferenciaSubtipoActividad())
			{
				pstmt.setInt(1, ent.getEntrenamientoId());
				pstmt.setInt(2, st);
				pstmt.execute();
			}

			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}finally
		{			
			try {
				if(pstmt!=null)pstmt.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}


	}


	private static void guardaActividadesEntrenamiento(Entrenamiento ent)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;


		try {
			conn = DataSourceFactory.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO actividades_entrenamiento "
					+ "(tipo_actividad, subtipo_actividad, dias, horas, entrenamiento_id)"
					+ " VALUES (?, ?, ?, ?, ?)");

			for(Actividad a:ent.getActividades())
			{
				pstmt.setInt(1, a.getTipoActividad());
				pstmt.setInt(2, a.getSubtipoActividad());
				pstmt.setInt(3, a.getDia());
				pstmt.setDouble(4, a.getHora());
				pstmt.setInt(5, ent.getEntrenamientoId());
				pstmt.execute();
			}

			pstmt.close();	


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally
		{			
			try 
			{
				// close connection en guardaEntrenamiento
				if(pstmt!=null)pstmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}			
		}


	}


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
		finally
		{
			try {
				if(reglasRowSet!=null) reglasRowSet.close();			
				if(actividadesRowSet!=null) actividadesRowSet.close();
				if(diasRowSet!=null) diasRowSet.close();
				if(horasRowSet!=null) horasRowSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reglas;

	}


	public static Entrenamiento consultaEntrenamientoUsuario(Usuario usuario) {


		Entrenamiento ent = null;		
		Connection conn=null;		
		JdbcRowSet entrenamientoRowSet = null;
		JdbcRowSet actividadesRowSet = null;
		JdbcRowSet preftipoRowSet = null;
		JdbcRowSet prefsubtipoRowSet = null;

		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			conn=ds.getConnection();
			entrenamientoRowSet = new JdbcRowSetImpl(conn);			
			entrenamientoRowSet.setCommand("SELECT entrenamiento_id, objetivo, diasDisponibles, horasDisponibles " 
					+ " FROM entrenamientos WHERE estado=1 and usuario_id=?;");
			entrenamientoRowSet.setInt(1, usuario.getUsuarioId());
			entrenamientoRowSet.execute();

			while (entrenamientoRowSet.next())
			{
				ent = new Entrenamiento(usuario);
				ent.setEntrenamientoId(entrenamientoRowSet.getInt(1));
				ent.setObjetivo(entrenamientoRowSet.getInt(2));
				ent.setDiasDisponible(entrenamientoRowSet.getInt(3));
				ent.setHorasDisponible(entrenamientoRowSet.getDouble(4));

			}

			if (ent== null) {
				entrenamientoRowSet.close();
				return null;
			}

			/* Consulta actividades por entrenamiento*/
			actividadesRowSet = new JdbcRowSetImpl(conn);
			actividadesRowSet.setCommand("SELECT tipo_actividad, subtipo_actividad, dias, horas " 
					+ " FROM actividades_entrenamiento WHERE entrenamiento_id=?;");
			actividadesRowSet.setInt(1, ent.getEntrenamientoId());
			actividadesRowSet.execute();

			List<Actividad> actividades = new ArrayList<Actividad>();			
			while(actividadesRowSet.next())
			{
				Actividad a = new Actividad();
				a.setTipoActividad(actividadesRowSet.getInt(1));
				a.setSubtipoActividad(actividadesRowSet.getInt(2));
				a.setDia(actividadesRowSet.getInt(3));
				a.setHora(actividadesRowSet.getDouble(4));				
				actividades.add(a);
			}			
			ent.setActividades(actividades);

			/* Consulta preferencias Tipo de Actividad por entrenamiento*/
			preftipoRowSet = new JdbcRowSetImpl(conn);
			preftipoRowSet.setCommand("SELECT tipo_actividad " 
					+ " FROM preferencias_tipo_actividad WHERE entrenamiento_id=?;");
			preftipoRowSet.setInt(1, ent.getEntrenamientoId());
			preftipoRowSet.execute();

			List<Integer> prefTipo = new ArrayList<Integer>();			
			while(preftipoRowSet.next())
			{
				prefTipo.add(preftipoRowSet.getInt(1));
			}			
			ent.setPreferenciaTipoActividad(prefTipo);


			/* Consulta preferencias Tipo de Actividad por entrenamiento*/
			prefsubtipoRowSet = new JdbcRowSetImpl(conn);
			prefsubtipoRowSet.setCommand("SELECT subtipo_actividad " 
					+ " FROM preferencias_subtipo_actividad WHERE entrenamiento_id=?;");
			prefsubtipoRowSet.setInt(1, ent.getEntrenamientoId());
			prefsubtipoRowSet.execute();

			List<Integer> prefSubTipo = new ArrayList<Integer>();			
			while(prefsubtipoRowSet.next())
			{
				prefSubTipo.add(prefsubtipoRowSet.getInt(1));
			}			
			ent.setPreferenciaSubtipoActividad(prefSubTipo);


			entrenamientoRowSet.close();
			actividadesRowSet.close();
			preftipoRowSet.close();
			prefsubtipoRowSet.close();

			return ent;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {

				if(entrenamientoRowSet!=null) entrenamientoRowSet.close();
				if(actividadesRowSet!=null) actividadesRowSet.close();
				if(preftipoRowSet!=null) preftipoRowSet.close();
				if(prefsubtipoRowSet!=null) prefsubtipoRowSet.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		return ent;
	}

	public static Usuario consultaUsuarioPorLogin(String login)
	{
		Usuario u = null;		
		Connection conn=null;		
		JdbcRowSet usuarioRowSet = null;



		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			conn=ds.getConnection();
			usuarioRowSet = new JdbcRowSetImpl(conn);			
			usuarioRowSet.setCommand("SELECT usuario_id, nombre, edad, sexo, estatura, peso, login, pwd " 
					+ " FROM usuarios_info WHERE login=?;");			
			usuarioRowSet.setString(1, login);
			usuarioRowSet.execute();

			while(usuarioRowSet.next())
			{
				u= new Usuario();
				u.setUsuarioId(usuarioRowSet.getInt(1));
				u.setNombre(usuarioRowSet.getString(2));
				u.setEdad(usuarioRowSet.getInt(3));
				u.setSexo(usuarioRowSet.getString(4));
				u.setEstatura(usuarioRowSet.getDouble(5));
				u.setPeso(usuarioRowSet.getDouble(6));
				u.setLogin(usuarioRowSet.getString(7));
				u.setPwd(usuarioRowSet.getString(8));

			}

			usuarioRowSet.close();

			return u;


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		finally
		{
			try {
				if(usuarioRowSet!=null) usuarioRowSet.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			

		return u;
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



	public static void finalizarEntrenamiento(Entrenamiento e) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DataSourceFactory.getConnection();
			pstmt = conn.prepareStatement("UPDATE entrenamientos SET estado = ? WHERE entrenamiento_id = ?");
			pstmt.setInt(1, 2);
			pstmt.setInt(2, e.getEntrenamientoId());

			pstmt.executeUpdate();


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally
		{
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}


	}

	public static void guardaTrabajoRealizado(String login, Date fecha, int tipo, int subtipo,double horas)
	{
		Usuario usuario = consultaUsuarioPorLogin(login);
		if(usuario==null) return;

		Entrenamiento ent = consultaEntrenamientoUsuario(usuario);
		if(ent==null) return;

		Connection conn = null;
		PreparedStatement pstmt = null;


		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO trabajo_actividad "
					+ "(entrenamiento_id, fecha, tipo_actividad, subtipo_actividad, horas)"
					+ " VALUES (?, ?, ?, ?, ?)");

			pstmt.setInt(1, ent.getEntrenamientoId());
			pstmt.setDate(2, fecha);
			pstmt.setInt(3, tipo);
			pstmt.setInt(4, subtipo);
			pstmt.setDouble(5, horas);

			pstmt.execute();			


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{

			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public static Entrenamiento consultaResultadosEntrenamiento(Entrenamiento ent)
	{
		Connection conn=null;		
		JdbcRowSet resultadosRowSet = null;
		
		try {
			DataSource ds = DataSourceFactory.getMySQLDataSource();
			conn=ds.getConnection();
			resultadosRowSet = new JdbcRowSetImpl(conn);			
			resultadosRowSet.setCommand("SELECT	tipo_actividad, subtipo_actividad, count(distinct fecha) as dias, sum(horas) as horas " 
					+ " FROM	trabajo_actividad "
					+ " WHERE 	entrenamiento_id = ? "
					+ " GROUP BY tipo_actividad, subtipo_actividad;");
			
			resultadosRowSet.setInt(1, ent.getEntrenamientoId());
			
			resultadosRowSet.execute();

			List<Actividad> resultados =  new ArrayList<Actividad>();
			while(resultadosRowSet.next())
			{
				Actividad a = new Actividad();
				
				a.setTipoActividad(resultadosRowSet.getInt(1));
				a.setSubtipoActividad(resultadosRowSet.getInt(2));
				a.setDia(resultadosRowSet.getInt(3));
				a.setHora(resultadosRowSet.getDouble(4));
				resultados.add(a);		

			}

			ent.setResultados(resultados);
			
			resultadosRowSet.close();

			return ent;


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		finally
		{
			try {
				if(resultadosRowSet!=null) resultadosRowSet.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			

		return ent;
	}
}
