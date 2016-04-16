package es.etsisi.poa.controlador;


/**Recoge el parametro inicial para lanzar 
 * el controlador de una aplicacion u otra en
 * funcion del mismo*/
public class ControladorPrincipal {

	public static void main(String[] args) {
		//aplicacionTexto();
		aplicacionGrafica();
	}
	
	private static void aplicacionTexto(){
		ControladorTexto ct = new ControladorTexto();
		ct.controlar();
	}
	private static void aplicacionGrafica(){
		ControladorGrafico cg = new ControladorGrafico();
		cg.controlar();
	}
	private void aplicacionWeb(){
		//TODO
	}
}
