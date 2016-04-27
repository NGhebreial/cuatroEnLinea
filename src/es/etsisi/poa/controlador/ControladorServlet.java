package es.etsisi.poa.controlador;

import java.io.IOException;
import java.util.ArrayList;

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
		//Inicia el servlet -> iniciar los objetos
		inicializaObjetos();
		System.out.println("init");
	}
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String peticion = request.getParameter("peticion");
		System.out.println("peticion "+peticion);
		if(peticion != null){
			//Primera carga del tablero
			if(peticion.equals("cargaTablero")){
				//Se carga por primera vez el tablero -> iniciar los objetos
				inicializaObjetos();
				request.setAttribute("modelo", modelo);
				request.getRequestDispatcher("vistas/tablero.jsp")
				.forward(request, response);
			}
			//recarga del tablero recogiendo la columna
			else if(peticion.equals("recargaTablero")){
				String mensaje = compruebaCoordenadaValida(request.getParameter("columna"));
				if(!modelo.getHayFinJuego()){
					request.setAttribute("modelo", modelo);
					request.getRequestDispatcher("vistas/tablero.jsp")
					.forward(request, response);
				}
				//Si hay fin de juego -> mostrar vista de fin de juego
				else{
					request.setAttribute("modelo", modelo);
					request.setAttribute("mensaje", mensaje);
					request.getRequestDispatcher("vistas/finJuego.jsp")
					.forward(request, response);
				}
			}
		}
		//Si no hay peticion -> no llevar a ninguna vista
		
	}
	private void inicializaObjetos(){
		modelo = new JuegoModelo();
		controlador = new Controlador();
	}
	/** Comprueba que la coordenada recogida sea valida:
	 * Que haya algun valor
	 * Que sea un numero
	 * Que este dentro del rango
	 * Que no este ocupado*/
	private String compruebaCoordenadaValida(String coordenada){
		String mensaje="";
		try{
			//Le resto 1 para cuadrar con las posiciones en la lista
			Integer coordenadaNum = new Integer(coordenada);
			//Si la coordenada es valida -> actualizar el modelo
			if(coordenadaNum >= 0 && coordenadaNum < modelo.COLUMNAS){
				mensaje=coordenadaValida(coordenadaNum, coordenada);
			}
		}
		//Si no es un numero --> error
		catch(NumberFormatException e){
		}
		return mensaje;
		
	}
	/**Comprueba la coordenada pasada para saber si se puede colocar en el tablero*/
	private String coordenadaValida(Integer coordenadaNum, String coordenada){
		String mensaje = "";
		ArrayList<ArrayList<String>> tablero = modelo.getTablero();
		//Variable para la fila y para controlar si la columna es valida
		Integer fila = null;
		//Itero con un indice para conocer la fila sobre la que estoy
		for(int i = 0; i < modelo.FILAS && fila == null; i++){
			String posicion = tablero.get(i).get(coordenadaNum);
			if(posicion.equals("-")){
				fila = i;
			}
		}
		//Si la fila != null -> la coordenada encontrada es valida
		if(fila != null){
			modelo.añadeFicha(fila, coordenadaNum);
			//Ahora se comprueba si hay victoria o empate
			mensaje = controlador.finJuego(fila, coordenadaNum, modelo);
			if(!mensaje.equals("")){
				modelo.setHayFinJuego(true);
			}
			//No ha acabado el juego -> cambiar de jugador e iniciar los movimientos
			else{
				//Mostrar el tablero
				modelo.cambiaFicha();
			}
		}
		return mensaje;

	}
}

