package es.etsisi.poa.controlador;

import java.util.ArrayList;

import es.etsisi.poa.modelo.JuegoModelo;
import es.etsisi.poa.vista.JuegoVistaGrafico;

public class ControladorGrafico {
	
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
			String mensaje = finJuego(fila, coordenadaNum);
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
	
	/**Comprueba si hay fin de juego para lanzar el mensaje correcto:
	 * Si el tablero esta lleno y no hay victoria -> empate
	 * Si hay 4 en linea de una de las fichas -> victoria para la ficha correspondiente*/
	private String finJuego(Integer filaFicha, Integer columnaFicha){
		String mensaje = "";
		ArrayList<ArrayList<String>> tablero = modelo.getTablero();
		Boolean hayVictoria = false;
		//Para los casos de prueba compruebo el tablero completo
		if(filaFicha == null && columnaFicha == null){
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
		}
		/*Cuando envie las coordenadas, compruebo las fichas adyacentes a ella ya que es cuando habra victoria 
		 * (se deduce que si acaba de poner una ficha es porque el tablero no esta lleno y 
		 * aun no hay victoria -> comprobar en base a esa posicion) */
		else{
			hayVictoria = compruebaVictoria(filaFicha, columnaFicha, tablero);
			//Si hay victoria -> mensaje = ganador
			if(hayVictoria)
				mensaje = "victoria";
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
		victoria = compruebaHorizontal(filaActual, columnaActual, tablero);
		//Si no hay victoria -> compruebo en vertical
		if(!victoria)
			victoria = compruebaVertical(filaActual, columnaActual, tablero);
		//Si no -> comprobar diagonales
		if(!victoria)
			victoria = compruebaDiagonal(filaActual, columnaActual, tablero);
		return victoria;
			
	}
	
	/**Comprueba si hay victoria en horizontal*/
	private Boolean compruebaHorizontal(int filaActual, int columna, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Comprobar victoria por filas
		int i = compruebaPosicionVH(filaActual, columna, filaActual + 1, tablero, modelo.FILAS);
		//Si i = 4 -> victoria
		if(i == 4) victoria = true;
		return victoria;
	}
	
	/**Comprueba si hay victoria en vertical*/
	private Boolean compruebaVertical(int fila, int columnaActual, ArrayList<ArrayList<String>> tablero){
		Boolean victoria = false;
		//Comprobar victoria por columnas
		int i = compruebaPosicionVH(fila, columnaActual, columnaActual + 1, tablero, modelo.COLUMNAS);
		//Si i = 4 -> victoria
		if(i == 4) victoria = true;
		return victoria;
	}
	
	
	/**Recorre el tablero para contar el numero de fichas coincidentes.
	 * En funcion de el indice pasado se tiene que comprobar en vertical o en horizontal*/
	private int compruebaPosicionVH(int fila, int columna, int posicionActual, ArrayList<ArrayList<String>> tablero, int modeloPosicion){
		//Contador para el numero de coincidencias
		int i = 1;
		int posicion = posicionActual;
		//Compruebo hacia delante		
		//Si el indiceComparar =modelo.COLUMNAS -> posicion la uso para columnas
		if(modeloPosicion == modelo.COLUMNAS){
			while(posicion < modeloPosicion && posicion >= 0 && i < 4 && 
					tablero.get(fila).get(posicion).equals(modelo.getFichaActual())){
					i++;
					posicion++;
			}
			
		}
		//Si el indiceComparar = modelo.FILAS -> posicion la uso para filas
		else if(modeloPosicion == modelo.FILAS){
			while(posicion < modeloPosicion && posicion >= 0 && i < 4 && 
					tablero.get(posicion).get(columna).equals(modelo.getFichaActual())){
					i++;
					posicion++;
			}
		}
		
		
		//Si i = 4 -> victoria
		//Si no, reinicio el contador
		if(i < 4){
			posicion = posicionActual - 2;
			//Compruebo hacia atras
			if(modeloPosicion == modelo.COLUMNAS){
				while(posicion < modeloPosicion && posicion >= 0 && i < 4 && 
						tablero.get(fila).get(posicion).equals(modelo.getFichaActual())){
						i++;
						posicion--;
				}
				
			}
			//Si el indiceComparar = modelo.FILAS -> posicion la uso para filas
			else if(modeloPosicion == modelo.FILAS){
				while(posicion < modeloPosicion && posicion >= 0 && i < 4 && 
						tablero.get(posicion).get(columna).equals(modelo.getFichaActual())){
						i++;
						posicion--;
				}
			}
		}
		return i;
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
					&& i < 4 
					&& tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
			{
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
						&& i < 4 
						&& tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
				{
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
					&& i < 4
					&& tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
			{
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
						&& i < 4 
						&& tablero.get(fila).get(columna).equals(modelo.getFichaActual()))
				{
					i++;
					columna++;
					fila--;
				}
			}
		}
		return i;
	}
}
