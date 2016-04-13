package es.etsisi.poa.modelo;

import java.util.ArrayList;

public class JuegoModelo {	
	
	public final int FILAS = 5;
	
	public final int COLUMNAS = 7;
	
	private Boolean hayFinJuego;
	
	private String mensajeFinJuego;
	
	private ArrayList<ArrayList<String>> tablero = new ArrayList<>();
	
	private String fichaActual;
	
	/**Inicializa el tablero con 5 filas y 7 columnas con caracteres -
	 * Inicializa hayFinJuego a false
	 * Inicializa la ficha actual a X*/
	public JuegoModelo(){
		for(int i = 0; i < FILAS; i++){
			ArrayList<String> fila = new ArrayList<String>();			
			for(int j = 0; j < COLUMNAS; j++){
				fila.add("-");
			}
			tablero.add(fila);
		}
		setHayFinJuego(false);
		setFichaActual("X");
	}
	
	/**Añade la ficha en las coordenadas pasadas por parametro
	 * Devuelve true en caso de haberse asignado correctamente
	 * @return Boolean*/
	public Boolean añadeFicha(int fila, int columna){
		Boolean fichaAñadida = false;
		try{
			tablero.get(fila).set(columna, getFichaActual());
			fichaAñadida = true;
		}
		catch(Exception e){
			fichaAñadida = false;
		}
		return fichaAñadida;
	}
	/** Cambia la ficha que esta poniendo en el tablero*/ 
	public void cambiaFicha(){
		if(getFichaActual().equals("X"))
			setFichaActual("O");
		else
			setFichaActual("X");
	}

	public ArrayList<ArrayList<String>> getTablero() {
		return tablero;
	}

	public void setTablero(ArrayList<ArrayList<String>> tablero) {
		this.tablero = tablero;
	}

	public Boolean getHayFinJuego() {
		return hayFinJuego;
	}

	public void setHayFinJuego(Boolean hayFinJuego) {
		this.hayFinJuego = hayFinJuego;
	}

	public String getMensajeFinJuego() {
		return mensajeFinJuego;
	}

	public void setMensajeFinJuego(String mensajeFinJuego) {
		this.mensajeFinJuego = mensajeFinJuego;
	}

	public String getFichaActual() {
		return fichaActual;
	}

	public void setFichaActual(String fichaActual) {
		this.fichaActual = fichaActual;
	}
	
	
}