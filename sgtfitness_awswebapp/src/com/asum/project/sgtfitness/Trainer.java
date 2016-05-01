package com.asum.project.sgtfitness;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asum.project.sgtfitness.engine.*;
import com.asum.project.sgtfitness.engine.util.DataAccess;

/**
 * Servlet implementation class Training
 */
@WebServlet("/trainer")
public class Trainer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trainer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mode = request.getParameter("mode");
		if(mode!=null && !mode.isEmpty()){
		    //processing here
			switch(mode){
			
			case "new":
				doNew(request, response);
				break;
			case "finish":
				doFinish(request, response);
				break;
			case "continue":
				doContinue(request, response);
				break;
			case "results":
				doResults(request, response);
			};
			
		}
		else
			doLogin(request, response);
		
	}

	private void doResults(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ControlEntrenamiento control =  new ControlEntrenamiento();
		String login = request.getParameter("login");
		Integer subtipo = Integer.parseInt(request.getParameter("actividad"));
		Date fecha = Date.valueOf(request.getParameter("fecha"));
		Double horas = Double.parseDouble(request.getParameter("horas"));
		
		int tipo = 0;		
		if (subtipo>=1 && subtipo<=4) tipo = 1;
		if (subtipo==5 || subtipo==6) tipo = 2;
		
		
		control.guardaResultadoEntrenamiento(login, fecha, tipo, subtipo, horas);
		response.sendRedirect("myresults.jsp");
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String login = request.getParameter("login");
		Usuario usuario =  DataAccess.consultaUsuarioPorLogin(login);
		
		if(usuario==null){ response.getWriter().append("El usuario no existe");}
		else{				
			ControlEntrenamiento control = new ControlEntrenamiento(usuario);
			control.consultaEntrenamientoUsuario(usuario);		
			
			
			request.getSession().setAttribute("unUsuario", usuario);
			
			if (control.getEntrenamientoActual()!=null)
			{
				control.consultaResultadosEntrenamiento();
				control.evaluarEntrenamiento();
				request.getSession().setAttribute("unEntrenamiento", control.getEntrenamientoActual());
				response.sendRedirect("mytraining.jsp");
			}
			else
				response.sendRedirect("newtraining.jsp");
			
			
		}
	}


	protected void doNew(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Usuario usuario = (Usuario)request.getSession().getAttribute("unUsuario");
		ControlEntrenamiento control = new ControlEntrenamiento(usuario);		
		
		Integer dias = Integer.parseInt(request.getParameter("dias"));
		Double horas = Double.parseDouble(request.getParameter("horas"));
		Integer objetivo = Integer.parseInt(request.getParameter("objetivo"));
		String[] cardio =  request.getParameterValues("cardio");
		String[] pesas =  request.getParameterValues("pesas");
		
		List<Integer> tiposActividad = new ArrayList<Integer>();
		List<Integer> subtiposActividad = new ArrayList<Integer>();
		
		if (cardio!= null && cardio.length>0)
		{
			tiposActividad.add(TipoActividad.CARDIO);
			for(String c:cardio)
			{
				subtiposActividad.add(Integer.parseInt(c));	
			}
			
		}
		if (pesas!= null && pesas.length>0)
		{
			tiposActividad.add(TipoActividad.PESAS);
			for(String p:pesas)
			{
				subtiposActividad.add(Integer.parseInt(p));	
			}
		}
		
		control.generaEntrenamiento(objetivo, dias, horas, tiposActividad, subtiposActividad);
		request.getSession().setAttribute("unEntrenamiento", control.getEntrenamientoActual());
		response.sendRedirect("mytraining.jsp");
		
		
		
	}
	
	protected void doFinish(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Usuario usuario = (Usuario)request.getSession().getAttribute("unUsuario");
				
		ControlEntrenamiento control = new ControlEntrenamiento(usuario);
		control.consultaEntrenamientoUsuario(usuario);
		control.finalizarEntrenamiento();
		response.sendRedirect("newtraining.jsp");
		
		
	}
	
	protected void doContinue(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Usuario usuario = (Usuario)request.getSession().getAttribute("unUsuario");
		
		ControlEntrenamiento control = new ControlEntrenamiento(usuario);
		control.consultaEntrenamientoUsuario(usuario);
		control.consultaResultadosEntrenamiento();
		control.proponeEntrenamiento();
		request.getSession().setAttribute("unEntrenamiento", control.getEntrenamientoActual());
		response.sendRedirect("mytraining.jsp");
		
		
	}
}
