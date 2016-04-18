package es.etsisi.poa.controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.etsisi.poa.modelo.JuegoModelo;


@SuppressWarnings("serial")
public class ControladorServlet extends HttpServlet {
	
	private JuegoModelo modelo;
	private Controlador controlador;
	public void init() throws ServletException {
		modelo = new JuegoModelo();
		controlador = new Controlador();
		System.out.println("init");
	}
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String peticion = request.getParameter("peticion");
		if(peticion != null){
			if(peticion.equals("cargaTablero")){
				request.setAttribute("tablero", modelo.getTablero());
				request.getRequestDispatcher("vistas/tablero.jsp")
				.forward(request, response);
			}
		}
		//Si no hay peticion -> no llevar a ninguna vista
		else{
			
		}
		
	}

}

