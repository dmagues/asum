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
		
		
		for(int t:tipos)
		{
			for(int st:subtipos)
			{
				ReglaEntrenamiento regla = reglas.stream().filter((r) -> 
				esReglaValida(r, objetivo, dias, horas, t, st))
						.findFirst().orElse(null);
				
				
				if (regla!=null)
					reglasSeleccionadas.add(regla);
			}

		}

		

		if(!reglasSeleccionadas.isEmpty())
		{
			List<Actividad> actividades = new ArrayList<Actividad>();
			for(ReglaEntrenamiento regla:reglasSeleccionadas)
			{
				if (tasaExigencia!=0)
				{
					regla.getActividad().setHoras(regla.getActividad().getHoras() * tasaExigencia);
				}
					
				
				actividades.add(regla.getActividad());
				e =  new Entrenamiento(usuario);
				e.setActividades(actividades);
				e.setObjetivo(objetivo);
				e.setDiasDisponible(dias);
				e.setHorasDisponible(horas);
				e.setPreferenciaTipoActividad(tipos);
				e.setPreferenciaSubtipoActividad(subtipos);
			}
		}

		return e;
	}

	public Entrenamiento proponeEntrenamiento() {
		return null;
	}

	public double calculaTasaExigencia(Usuario usuario) {
		
		return DataAccess.consultarTasaExigenciaPorIMC(usuario.getIMC());
	}

	private void consultaReglas()
	{
		this.reglas = DataAccess.consultaReglas();   
	}

	private void consultaEntrenamientoUsuario(Usuario usuario)
	{
		this.entrenamientoActual = DataAccess.consultaEntrenamientoUsuario(usuario);   
	}
	
	private boolean esReglaValida(ReglaEntrenamiento r, int objetivo, int dias, double horas, 
			int tipos, int subtipos)
	{
		return r.getObjetivo() == objetivo 
				&& r.esRangoEdadValido(this.usuario.getEdad())
				&& r.getActividad().getDias() >= dias
				&& r.getActividad().getHoras() >= horas
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
			
		
		Entrenamiento e = control.generaEntrenamiento(Objetivos.SALUD_GENERAL, 3, 4, tipos, subtipos);
		System.out.println(e);

	}

}