package com.asum.project.sgtfitness.engine;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.asum.project.sgtfitness.engine.util.DataAccess;

public class ControlEntrenamiento {

	private double tasaExigencia;
	private Entrenamiento entrenamientoActual;
	private List<ReglaEntrenamiento> reglas;
	private Usuario usuario;

	public ControlEntrenamiento(Usuario usuario) {
		this.usuario = usuario;
		consultaReglas();
	}

	public ControlEntrenamiento() {
		
	}
	public void generaEntrenamiento(int objetivo, int dias, double horas,
			List<Integer> tipos, List<Integer> subtipos) 
	{
		Entrenamiento e = null;
		this.tasaExigencia = calculaTasaExigencia(usuario);
		
		List<ReglaEntrenamiento> reglasSeleccionadas = new ArrayList<ReglaEntrenamiento>(); 
		ReglaEntrenamiento regla;
		
		for(int t:tipos)
		{
			for(int st:subtipos)
			{
				regla = reglas.stream().filter((r) -> esReglaValida(r, objetivo, t, st))
						.findFirst()
						.orElse(null);
				
				if (regla!=null)
				{		
					int d[] = regla.getActividad().getDias();
					int N=d.length;
					int nuevosDias = 0;
					
					for(int i=0; i<N; i++){
						if (i==0)
						{	if(d[i]>dias)
							{nuevosDias = dias;
							break;}
						}
						if (i+1<N)
						{	if( d[i]<=dias && dias<d[i+1])
							{ nuevosDias=d[i];
							break;}
						}else
						{ nuevosDias=d[i]; }
					}
					
					double h[] = regla.getActividad().getHoras();
					N=h.length;
					double nuevasHoras = 0;
					int nivel = DataAccess.consultaNivelPorIMC(usuario.getIMC());
					if (horas > h[nivel])
					{ nuevasHoras=h[nivel];
					}else
					{
						for(int i=0; i<N; i++){
							if (i==0)
							{	if(h[i]>horas)
								{nuevasHoras = horas;
								break;}
							}
							if (i+1<N)
							{	if( h[i]<=horas && horas<h[i+1])
								{nuevasHoras = h[i];
								break;}
							}else
							{ nuevasHoras = h[i]; }					
						}
					}
					
					
					
					
					Actividad nuevaActividad = new Actividad(new int[]{nuevosDias}, 
							new double[]{nuevasHoras}, 
							regla.getActividad().getTipoActividad(), 
							regla.getActividad().getSubtipoActividad());
					
					regla.setActividad(nuevaActividad);
					reglasSeleccionadas.add(regla);
				}
			}

		}

		

		if(!reglasSeleccionadas.isEmpty())
		{
			List<Actividad> actividades = new ArrayList<Actividad>();
			e =  new Entrenamiento(usuario);
			e.setObjetivo(objetivo);
			e.setDiasDisponible(dias);
			e.setHorasDisponible(horas);
			e.setPreferenciaTipoActividad(tipos);
			e.setPreferenciaSubtipoActividad(subtipos);
			
			for(ReglaEntrenamiento r:reglasSeleccionadas)
			{
				if (tasaExigencia!=0)
				{
					double[] arrHoras = r.getActividad().getHoras();
					for(int j=0; j<arrHoras.length; j++)
					{
						arrHoras[j]*=tasaExigencia;
					}					
					r.getActividad().setHoras(arrHoras);
				}
							
				actividades.add(r.getActividad());
				
			}
			e.setActividades(actividades);
		}

		
		e = DataAccess.guardaEntrenamiento(e);
		
		this.entrenamientoActual = e;
	}

	public void proponeEntrenamiento() {
		if(this.entrenamientoActual==null)
			return;
		
		Entrenamiento anterior = this.entrenamientoActual;				
		Entrenamiento nuevo = new Entrenamiento(anterior.getUsuario());
		finalizarEntrenamiento();
		
		
		anterior.evaluarEntrenamiento();
		
		nuevo.setDiasDisponible(anterior.getDiasDisponible());
		nuevo.setHorasDisponible(anterior.getHorasDisponible());
		nuevo.setObjetivo(anterior.getObjetivo());
		nuevo.setPreferenciaTipoActividad(anterior.getPreferenciaTipoActividad());
		nuevo.setPreferenciaSubtipoActividad(anterior.getPreferenciaSubtipoActividad());
				
		List<Actividad> actividades = anterior.getActividades();
		List<Actividad> actividadesPropuesta = new ArrayList<Actividad>();
		
		for(Actividad a:actividades)
		{
			double[] horas = calculaPropuestaHoras(a.getHoras(), a.getTasaResultado());
			Actividad nuevaActividad = new Actividad(a.getDias(), horas, a.getTipoActividad(), a.getSubtipoActividad());
			actividadesPropuesta.add(nuevaActividad);
		}		
		nuevo.setActividades(actividadesPropuesta);	
				
		nuevo = DataAccess.guardaEntrenamiento(nuevo);
		this.entrenamientoActual = nuevo;
		
		
		
		
	}
	
	private double[] calculaPropuestaHoras(double[] horas, double porcentaje)
	{
		double delta=1;
		for(int i=0; i<horas.length; i++)
		{
			if(porcentaje>0.9) delta=1.10;
			if(porcentaje<=0.9) delta=0.90;			
			horas[i]=horas[i]*delta; 
		}
		
		return horas;
		
	}

	public double calculaTasaExigencia(Usuario usuario) {
		
		return DataAccess.consultaTasaExigenciaPorIMC(usuario.getIMC());
	}

	private void consultaReglas()
	{
		this.reglas = DataAccess.consultaReglas();
	}

	public void consultaEntrenamientoUsuario(Usuario usuario)
	{
		this.entrenamientoActual = DataAccess.consultaEntrenamientoUsuario(usuario);   
	}
	
	private boolean esReglaValida(ReglaEntrenamiento r, int objetivo, int tipos, int subtipos)
	{
		return r.getObjetivo() == objetivo 
				&& r.esRangoEdadValido(this.usuario.getEdad())
				&& r.getActividad().getTipoActividad()==tipos
				&& r.getActividad().getSubtipoActividad()==subtipos;
		
	}
	
	public void finalizarEntrenamiento()
	{
		if(this.entrenamientoActual!=null)
		{
			DataAccess.finalizarEntrenamiento(this.entrenamientoActual);
			this.entrenamientoActual=null;
		}
	}
	
	
	public void guardaResultadoEntrenamiento(String login, Date fecha, int tipo, int subtipo, double horas){
		
		DataAccess.guardaTrabajoRealizado(login, fecha, tipo, subtipo, horas);
		
	}
	
	public void consultaResultadosEntrenamiento()
	{
		if (this.entrenamientoActual!=null) 
			DataAccess.consultaResultadosEntrenamiento(this.entrenamientoActual);
	}

	public void evaluarEntrenamiento()
	{
		if (this.entrenamientoActual!=null) 
			this.entrenamientoActual.evaluarEntrenamiento();
	}
	public static void main(String[] args)
	{
		Usuario usuario = new Usuario();
		usuario.setEdad(34);
		usuario.setNombre("JOHANNA ORDONEZ");
		usuario.setSexo("F");
		usuario.setEstatura(1.50);
		usuario.setPeso(60);
		usuario.setLogin("jordonez");
		usuario.setPwd("s3cret");
				
		usuario = DataAccess.guardaUsuario(usuario);
			
		Usuario usuario2 = DataAccess.consultaUsuarioPorLogin("dmagues");
		
		System.out.println(usuario);
		System.out.println(usuario2);
		
		
		ControlEntrenamiento control = new ControlEntrenamiento(usuario);
		
		List<Integer> tipos =  new ArrayList<Integer>();
		tipos.add(TipoActividad.CARDIO);
		
		List<Integer> subtipos =  new ArrayList<Integer>();
		subtipos.add(TipoActividad.CORRER);
		subtipos.add(TipoActividad.MONTAR_BICICLETA);
			
		
		control.generaEntrenamiento(Objetivos.SALUD_GENERAL, 2, 4, tipos, subtipos);
		//System.out.println(control.getEntrenamientoActual());
		
		control.consultaEntrenamientoUsuario(usuario);
		System.out.println(control.getEntrenamientoActual());	
		
//		control.guardaResultadoEntrenamiento(Date.valueOf("2016-1-1"), 1, 2, 2.34);
//		control.guardaResultadoEntrenamiento(Date.valueOf("2016-1-2"), 1, 2, 1.15);				
//		control.guardaResultadoEntrenamiento(Date.valueOf("2016-1-22"), 1, 1, 1.2);
		
		control.consultaResultadosEntrenamiento();
		control.getEntrenamientoActual().evaluarEntrenamiento();
		
		System.out.println(control.getEntrenamientoActual());	
		

	}

	public Entrenamiento getEntrenamientoActual() {
		// TODO Auto-generated method stub
		return this.entrenamientoActual;
	}

}