package es.etsisi.poa.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.etsisi.poa.controlador.ControladorTexto;
import es.etsisi.poa.modelo.JuegoModelo;

public class JuegoVista {
	
	private JuegoModelo juegoModelo;
	private ControladorTexto controlador;

	public JuegoVista(JuegoModelo juegoModelo, ControladorTexto controlador){
		setJuegoModelo(juegoModelo);
		setControlador(controlador);
	}
	/** Muestra por consola el tablero recogido del modelo*/
	public void muestraTablero(){
		ArrayList<ArrayList<String>> tablero = getJuegoModelo().getTablero();
		System.out.println("TABLERO: ");
		//Recorro el tablero de forma inversa para mostrarlo correctamente
		for(int i = getJuegoModelo().FILAS - 1; i >= 0; i--){
			for(int j = 0; j < getJuegoModelo().COLUMNAS ; j++){
				System.out.print(tablero.get(i).get(j));
			}
			System.out.println();
		}
//		for(ArrayList<String> filas: tablero){
//			for (String columna: filas){
//				System.out.print(columna);
//			}
//			System.out.println();
//		}
		System.out.println(".................................................");
	}
	/**Inicia el movimiento recogiendo el valor por teclado*/
	public void iniciaMovimiento(){
		System.out.println("Le toca poner al jugador con fichas "+getJuegoModelo().getFichaActual());
		System.out.print("Introduzca columna [1-7]: ");
		String texto=leerTeclado();
		getControlador().compruebaCoordenadaValida(texto);
	}
	
	/**Muestra el mensaje de error recibido por parametro
	 * y reinicia el juego*/
	public void mensajeErrorMovimiento(String mensaje){
		System.out.println(mensaje);
		System.out.println("pulsa ENTER");
		leerTeclado();
		iniciaMovimiento();
	}
	/**Recoge el mensaje a mostrar en el caso de fin de juego:
	 * Si hay victoria -> mensaje de victoria para las fichas actuales
	 * Si no hay victoria -> mensaje de empate*/
	public void victoria(Boolean victoria){
		muestraTablero();
		if(victoria)
			System.out.println("VICTORIA PARA LAS FICHAS "+getJuegoModelo().getFichaActual());
		else
			System.out.println("Nadie ha conseguido ganar");
	}
	
	private String leerTeclado(){
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		String texto="";
		try {
			texto = br.readLine();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
		return texto;
	}
	public JuegoModelo getJuegoModelo() {
		return juegoModelo;
	}


	public void setJuegoModelo(JuegoModelo juegoModelo) {
		this.juegoModelo = juegoModelo;
	}
	public ControladorTexto getControlador() {
		return controlador;
	}
	public void setControlador(ControladorTexto controlador) {
		this.controlador = controlador;
	}
	
}
