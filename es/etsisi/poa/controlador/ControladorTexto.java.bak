package es.etsisi.poa.controlador;

import java.util.ArrayList;

import es.etsisi.poa.modelo.JuegoModelo;
import es.etsisi.poa.vista.JuegoVista;

public class ControladorTexto {
	
	private JuegoModelo modelo;
	private JuegoVista vista;
	
	public ControladorTexto(){
		
	}
	
	public void controlar(){
		//Inicializar la vista y el modelo
		modelo = new JuegoModelo();
		vista = new JuegoVista(modelo, this);
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
			String mensaje = finJuego();
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
	
	/**Comprueba si hay fin de juego para lanzar el mensaje correcto:
	 * Si el tablero esta lleno y no hay victoria -> empate
	 * Si hay 4 en linea de una de las fichas -> victoria para la ficha correspondiente*/
	private String finJuego(){
		String mensaje = "";
		ArrayList<ArrayList<String>> tablero = modelo.getTablero();
		Boolean hayVictoria = false;
		//Primero compruebo si para la ficha actual hay victoria
		for(int i = 0; i < modelo.FILAS && !hayVictoria; i++){
			//Compruebo si en la fila hay alguna ficha actual
			ArrayList<String> fila = tablero.get(i);
			for(int j = 0; j < modelo.COLUMNAS && !hayVictoria; j++){
				//Coincidencia en una posicion
				if(fila.get(j).equals(modelo.getFichaActual())){
					hayVictoria = compruebaVictoria(i, j, tablero);
					//Si hay victoria -> mensaje = ganador
					if(hayVictoria)
						mensaje = "victoria";					
				}
			}
		}
		//Si el mensaje es "" -> no ha habido victoria, comprobar que el tablero no esta lleno
		if(mensaje.equals("")){
			if(tableroLleno(tablero))
				mensaje = "empate";
		}
		return mensaje;
	}
	
	private Boolean tableroLleno(ArrayList<ArrayList<String>> tablero){
		Boolean tableroLleno = true;
		for(int i = 0; i < modelo.FILAS && tableroLleno; i++){
			for(int j = 0; j < modelo.COLUMNAS && tableroLleno; j++){
				if(tablero.get(i).get(j).equals("-")){
					tableroLleno = false;
				}
			}
		}
		return tableroLleno;
	}
	/**En base a la posicion recibida se comprueba el tablero:
	 * Victoria si hay 4 en horizontal, vertical o diagonal*/
	private Boolean compruebaVictoria(int filaActual, int columnaActual, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Compruebo en horizontal
		victoria = compruebaHorizontal(filaActual + 1, columnaActual, tablero);
		//Si no hay victoria -> compruebo en vertical
		if(!victoria)
			victoria = compruebaVertical(filaActual, columnaActual + 1, tablero);
		//Si no -> comprobar diagonales
		if(!victoria)
			victoria = compruebaDiagonal(filaActual, columnaActual, tablero);
		return victoria;
			
	}
	
	/**Comprueba si hay victoria en horizontal*/
	private Boolean compruebaHorizontal(int filaActual, int columna, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Contador para el numero de coincidencias
		int i = compruebaPosicionVH(filaActual, columna, filaActual, tablero, modelo.FILAS);
		//Si i = 4 -> victoria
		if(i == 4) victoria = true;
		return victoria;
	}
	
	/**Comprueba si hay victoria en vertical*/
	private Boolean compruebaVertical(int fila, int columna, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Variable para el numero de coincidencias
		int i = compruebaPosicionVH(fila, columna, columna, tablero, modelo.COLUMNAS);
		//Si i = 4 -> victoria
		if(i == 4) victoria = true;
		return victoria;
	}
	
	/**Comprueba si hay victoria en diagonal*/
	private Boolean compruebaDiagonal(int filaActual, int columnaActual, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Primero hacia la derecha
		int i = compruebaPosicionDiagonal(filaActual + 1, columnaActual + 1, tablero, true);
		//Si no hay 4 -> comprobar hacia la izquierda
		if(i == 4)
			victoria = true;
		else
			i = compruebaPosicionDiagonal(filaActual + 1, columnaActual - 1, tablero, false);
		if(i == 4) victoria = true;
		return victoria;
	}
	
	/**Recorre el tablero para contar el numero de fichas coincidentes.
	 * En funcion de el indice pasado se tiene que comprobar en vertical o en horizontal*/
	private int compruebaPosicionVH(int fila, int columna, int posicionActual, ArrayList<ArrayList<String>> tablero, int indiceComparar){
		//Contador para el numero de coincidencias
		int i = 1;
		int posicion = posicionActual;
		//Compruebo hacia delante
		while(posicion < indiceComparar && posicion >= 0 && i < 4){			
			//Si el indiceComparar = 7 -> posicion la uso para columnas
			if(indiceComparar == 7){
				if(tablero.get(fila).get(posicion).equals(modelo.getFichaActual()))
					i++;
			}
			//Si el indiceComparar = 5 -> posicion la uso para filas
			else if(indiceComparar == 5){
				if(tablero.get(posicion).get(columna).equals(modelo.getFichaActual()))
					i++;
			}
			posicion++;
		}
		//Si i = 4 -> victoria
		//Si no, reinicio el contador
		if(i < 4){
			posicion = posicionActual - 2;
			//Compruebo hacia atras
			while(posicion >= 0 && posicion < indiceComparar && i < 4){
				//Si el indiceComparar = 7 -> posicion la uso para columnas
				if(indiceComparar == 7){
					if(tablero.get(fila).get(posicion).equals(modelo.getFichaActual()))
						i++;
				}
				//Si el indiceComparar = 5 -> posicion la uso para filas
				else if(indiceComparar == 5){
					if(tablero.get(posicion).get(columna).equals(modelo.getFichaActual()))
						i++;
				}
				posicion--;
			}
		}
		return i;
	}
	
	/**Comprueba en diagonal hacia la derecha o en diagonal hacia la izquierda en funcion del parametro recibido*/
	private int compruebaPosicionDiagonal(int filaActual, int columnaActual, ArrayList<ArrayList<String>> tablero, boolean derecha){
		int i = 1;
		int fila = filaActual;
		int columna = columnaActual;
		//Si se quiere comprobar hacia la derecha
		if(derecha){
			//Primero hacia arriba
			while(columna < modelo.COLUMNAS && columna >= 0 
					&& fila < modelo.FILAS && fila >= 0 
					&& i < 4)
			{
				if(tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
					i++;
				columna++;
				fila++;
			}
			//Si i = 4 -> victoria
			//Si no, reinicio los contadores
			if(i < 4){
				fila = filaActual - 2;
				columna = columnaActual - 2;
				//Hacia abajo
				while(columna >= 0 && columna < modelo.COLUMNAS 
						&& fila >= 0 && fila < modelo.FILAS  
						&& i < 4)
				{
					if(tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
						i++;
					columna--;
					fila--;
				}
			}
		}
		//si no, comprobar hacia la izquierda
		else{
			//Primero hacia arriba
			while(columna >= 0 && columna < modelo.COLUMNAS 
					&& fila >= 0 && fila < modelo.FILAS 
					&& i < 4)
			{
				if(tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
					i++;
				columna--;
				fila++;
			}
			//Si i = 4 -> victoria
			//Si no, reinicio los contadores
			if(i < 4){
				fila = filaActual - 2;
				columna = columnaActual + 2;
				//Hacia abajo
				while(columna < modelo.COLUMNAS && columna >= 0 
						&& fila >= 0 && fila < modelo.FILAS 
						&& i < 4)
				{
					if(tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
						i++;
					columna++;
					fila--;
				}
			}
		}
		return i;
	}
}
