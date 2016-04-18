package com.asum.project.sgtfitness.engine;

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

	public Entrenamiento generaEntrenamiento(int objetivo, int dias, double horas,
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

		return e;
	}

	public Entrenamiento proponeEntrenamiento() {
		return null;
	}

	public double calculaTasaExigencia(Usuario usuario) {
		
		return DataAccess.consultaTasaExigenciaPorIMC(usuario.getIMC());
	}

	private void consultaReglas()
	{
		this.reglas = DataAccess.consultaReglas();   
	}

	private void consultaEntrenamientoUsuario(Usuario usuario)
	{
		this.entrenamientoActual = DataAccess.consultaEntrenamientoUsuario(usuario);   
	}
	
	private boolean esReglaValida(ReglaEntrenamiento r, int objetivo, 
			int tipos, int subtipos)
	{
		return r.getObjetivo() == objetivo 
				&& r.esRangoEdadValido(this.usuario.getEdad())
				&& r.getActividad().getTipoActividad()==tipos
				&& r.getActividad().getSubtipoActividad()==subtipos;	
		
	}
	
	

	public static void main(String[] args)
	{
		Usuario usuario = new Usuario();
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
			
		
		Entrenamiento e = control.generaEntrenamiento(Objetivos.SALUD_GENERAL, 3, 2, tipos, subtipos);
		System.out.println(e);
		
		e = DataAccess.consultaEntrenamientoUsuario(usuario);
		System.out.println(e);
		
		
				

	}

}