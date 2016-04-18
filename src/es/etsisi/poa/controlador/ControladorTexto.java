package es.etsisi.poa.controlador;

import java.util.ArrayList;

import es.etsisi.poa.modelo.JuegoModelo;
import es.etsisi.poa.vista.JuegoVistaTexto;

public class ControladorTexto extends Controlador {
	
	private JuegoModelo modelo;
	private JuegoVistaTexto vista;
	
	public ControladorTexto(){}
	
	/** Inicializa la aplicacion inicializando las instancias de vista y modelo*/
	public void controlar(){
		//Inicializar la vista y el modelo
		modelo = new JuegoModelo();
		vista = new JuegoVistaTexto(modelo, this);
		//Mostrar el tablero
		vista.muestraTablero();
		//Pedir ficha
		vista.iniciaMovimiento();
	}
	
	/** Comprueba que la coordenada recogida sea valida:
	 * Que haya algun valor
	 * Que sea un numero
	 * Que este dentro del rango
	 * Que no este ocupado*/
	public void compruebaCoordenadaValida(String coordenada){
		//Si se recibe algun valor
		if(coordenada == null || !coordenada.equals("")){
			try{
				//Le resto 1 para cuadrar con las posiciones en la lista
				Integer coordenadaNum = new Integer(coordenada) - 1;
				//Si la coordenada es valida -> actualizar el modelo
				if(coordenadaNum >= 0 && coordenadaNum < modelo.COLUMNAS){
					coordenadaValida(coordenadaNum, coordenada);
				}
				//Si no esta dentro de las coordenadas validas -> error
				else{
					vista.mensajeErrorMovimiento(coordenada+" no es una columna válida");
				}
			}
			//Si no es un numero --> error
			catch(NumberFormatException e){
				vista.mensajeErrorMovimiento("Introduce un número entre 1 y 7");
			}
		}
		//Si no ha introducido nada -> error
		else{
			vista.mensajeErrorMovimiento("Introduce un número entre 1 y 7");
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
				//Victoria para las fichas actuales
				if(mensaje.equals("victoria"))
					vista.victoria(true);
				//Empate
				else 
					vista.victoria(false);
			}
			//No ha acabado el juego -> cambiar de jugador e iniciar los movimientos
			else{
				//Mostrar el tablero
				vista.muestraTablero();
				modelo.cambiaFicha();							
				vista.iniciaMovimiento();
			}
		}
		//Si la fila es null -> toda la columna esta llena
		else{
			vista.mensajeErrorMovimiento("La columna "+coordenada+" está llena");
		}

	}
	
	
	public static void main(String[] args) {
		ControladorTexto c = new ControladorTexto();
		c.casosDePrueba(c);
	}
	private void casosDePrueba(ControladorTexto c){
		JuegoModelo jm = new JuegoModelo();
		ArrayList<ArrayList<String>> tablero = new ArrayList<>();
		
		JuegoVistaTexto vista = new JuegoVistaTexto(jm, c);
		
		/*1- Tablero lleno victoria vertical*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				if(j%2 == 0)
					fila.add("X");
				else
					fila.add("O");
				
			}
			tablero.add(fila);
		}
		c.estructuraPrueba(jm, vista, tablero, c);
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*2 Tablero lleno victoria horizontal*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
					if(i%2 == 0)
						fila.add("O");
					else
						fila.add("X");
				
			}
			tablero.add(fila);
		}
		c.estructuraPrueba(jm, vista, tablero, c);
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*3 Tablero lleno victoria diagonal (Sin direccion)*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				if(i%2==0){
					if(j%2 == 0)
						fila.add("X");
					else
						fila.add("O");
				}
				else
					if(j%2 == 0)
						fila.add("O");
					else
						fila.add("X");
				
			}
			tablero.add(fila);
		}
		c.estructuraPrueba(jm, vista, tablero, c);
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*4 Tablero vacio victoria diagonal derecha*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("-");				
			}
			tablero.add(fila);
		}
		tablero.get(0).set(0, "X");
		tablero.get(1).set(1, "X");
		tablero.get(2).set(2, "X");
		tablero.get(3).set(3, "X");
		c.estructuraPrueba(jm, vista, tablero, c);
		
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*5 Tablero vacio victoria diagonal izquierda*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("-");				
			}
			tablero.add(fila);
		}
		tablero.get(0).set(6, "X");
		tablero.get(1).set(5, "X");
		tablero.get(2).set(4, "X");
		tablero.get(3).set(3, "X");
		c.estructuraPrueba(jm, vista, tablero, c);
		
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*6 Tablero vacio victoria vertical*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("-");				
			}
			tablero.add(fila);
		}
		tablero.get(0).set(0, "X");
		tablero.get(1).set(0, "X");
		tablero.get(2).set(0, "X");
		tablero.get(3).set(0, "X");
		c.estructuraPrueba(jm, vista, tablero, c);
		
		/*-------------------------------------------------------------*/
		tablero = new ArrayList<>();
		/*7 Tablero vacio victoria horizontal*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("-");				
			}
			tablero.add(fila);
		}
		tablero.get(0).set(0, "X");
		tablero.get(0).set(1, "X");
		tablero.get(0).set(2, "X");
		tablero.get(0).set(3, "X");
		c.estructuraPrueba(jm, vista, tablero, c);
		/*-------------------------------------------------------------*/
		
		tablero = new ArrayList<>();
		/*8 Tablero lleno no victoria*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("R");
				
			}
			tablero.add(fila);
		}
		c.estructuraPrueba(jm, vista, tablero, c);
		/*-------------------------------------------------------------*/
		
		tablero = new ArrayList<>();
		/*9 Tablero vacio*/
		for (int i=0; i<jm.FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();
			for(int j=0; j<jm.COLUMNAS; j++){
				fila.add("-");
				
			}
			tablero.add(fila);
		}
		c.estructuraPrueba(jm, vista, tablero, c);
		
	}
	private void estructuraPrueba(JuegoModelo jm, JuegoVistaTexto vista, ArrayList<ArrayList<String>> tablero, ControladorTexto c){
		jm.setTablero(tablero);
		c = new ControladorTexto();
		c.modelo = jm;
		vista = new JuegoVistaTexto(jm, c);
		//Mostrar el tablero
		vista.muestraTablero();
		System.out.println(c.finJuego(null, null, c.modelo));
	}
}
