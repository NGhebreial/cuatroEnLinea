package es.etsisi.poa.controlador;

import java.util.ArrayList;

import es.etsisi.poa.modelo.JuegoModelo;
import es.etsisi.poa.vista.JuegoVistaGrafico;

public class ControladorGrafico extends Controlador{
	
	private JuegoModelo modelo;
	private JuegoVistaGrafico vista;
	
	public ControladorGrafico(){}
	
	/** Inicializa la aplicacion inicializando las instancias de vista y modelo*/
	public void controlar(){
		modelo = new JuegoModelo();
		vista = new JuegoVistaGrafico(modelo, this);
		//Muestra la tabla
		vista.muestraTablero();
	}
	/** Comprueba que la coordenada recogida sea valida:
	 * Que haya algun valor
	 * Que sea un numero
	 * Que este dentro del rango
	 * Que no este ocupado*/
	public void compruebaCoordenadaValida(String coordenada){
		try{
			//Le resto 1 para cuadrar con las posiciones en la lista
			Integer coordenadaNum = new Integer(coordenada);
			//Si la coordenada es valida -> actualizar el modelo
			if(coordenadaNum >= 0 && coordenadaNum < modelo.COLUMNAS){
				coordenadaValida(coordenadaNum, coordenada);
			}
		}
		//Si no es un numero --> error
		catch(NumberFormatException e){
		}
		
	}
	/**Comprueba la coordenada pasada para saber si se puede colocar en el tablero*/
	private void coordenadaValida(Integer coordenadaNum, String coordenada){

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
			String mensaje = finJuego(fila, coordenadaNum, modelo);
			if(!mensaje.equals("")){
				modelo.setHayFinJuego(true);
				//Victoria para las fichas actuales
				if(mensaje.equals("victoria")){
					vista.victoria(true);
				}
				//Empate
				else{
					vista.victoria(false);
				}
			}
			//No ha acabado el juego -> cambiar de jugador e iniciar los movimientos
			else{
				//Mostrar el tablero
				modelo.cambiaFicha();							
				vista.muestraTablero();
			}
		}

	}
}
