package es.etsisi.poa.vista;

import javax.swing.JFrame;

import es.etsisi.poa.controlador.ControladorGrafico;
import es.etsisi.poa.modelo.JuegoModelo;

import javax.swing.border.Border;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class JuegoVistaGrafico {

	private JFrame frame;
	private JuegoModelo juegoModelo;
	private ControladorGrafico controlador;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					JuegoVistaGrafico window = new JuegoVistaGrafico(null,null);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public JuegoVistaGrafico(JuegoModelo juegoModelo,ControladorGrafico controlador) {
		initialize();
		this.frame.setVisible(true);
		setJuegoModelo(juegoModelo);
		setControlador(controlador);
	}

	/**Añade las filas y columnas del modelo en la tabla*/
	public void muestraTablero(){
		frame.getContentPane().removeAll();
		ArrayList<ArrayList<String>> tablero = getJuegoModelo().getTablero();
		//Posicion inicial
		Integer fila = 20;
		Integer columna = 20;
		//Color del borde de cada label
		Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
		//Se recorre el tablero
		for(Integer i = getJuegoModelo().FILAS - 1; i >= 0; i--){
			for(Integer j = 0; j < getJuegoModelo().COLUMNAS ; j++){
				//Un label por cada valor
				final JLabel label = new JLabel(tablero.get(i).get(j), JLabel.CENTER);
				aspectoLabel(label, border, fila, columna, tablero, i, j);
				frame.getContentPane().add(label);
				//Aumentar la posicion de la columna
				columna += 100;
			}
			/*Se aumenta la posicion de la fila
			 * y se vuelve a la posicion original de la columna*/
			fila += 100;
			columna = 20;
		}
		frame.repaint();
	}
	
	/**Recoge el mensaje a mostrar en el caso de fin de juego:
	 * Si hay victoria -> mensaje de victoria para las fichas actuales
	 * Si no hay victoria -> mensaje de empate*/
	public void victoria(Boolean victoria){
		muestraTablero();
		if(victoria){
			VistaFinJuego vistaFin = new VistaFinJuego("VICTORIA PARA LAS FICHAS "+getJuegoModelo().getFichaActual(), frame);
		}
		else{
			VistaFinJuego vistaFin = new VistaFinJuego("Nadie ha conseguido ganar", frame);
		}
	}
	
	/**Aspecto visual de cada label y evento on click*/
	private void aspectoLabel(final JLabel label, Border border, Integer fila, Integer columna, 
			ArrayList<ArrayList<String>> tablero, Integer posFila, Integer posColumna){
		//Posicion y tamaño
		label.setBounds(columna, fila, 100, 100);
		label.setBorder(border);
		//Fuente
		label.setFont(new Font("Serif", Font.BOLD, 16));
		//Color de la letra
		if(tablero.get(posFila).get(posColumna).equals("X"))
			label.setForeground(Color.BLUE);
		else if(tablero.get(posFila).get(posColumna).equals("X"))
			label.setForeground(Color.GREEN);
		else 
			label.setForeground(Color.BLACK);
		label.setName(posColumna.toString());
		//Recibir click mientras no haya fin de juego
		if(!getJuegoModelo().getHayFinJuego()){
			label.addMouseListener(new MouseAdapter()  
			{  
				public void mouseClicked(MouseEvent e)  
				{
					System.out.println("ENTRA "+label.getName());
					getControlador().compruebaCoordenadaValida(label.getName());
				}  
			});
		}
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 770, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public JuegoModelo getJuegoModelo() {
		return juegoModelo;
	}

	public void setJuegoModelo(JuegoModelo juegoModelo) {
		this.juegoModelo = juegoModelo;
	}

	public ControladorGrafico getControlador() {
		return controlador;
	}

	public void setControlador(ControladorGrafico controlador) {
		this.controlador = controlador;
	}

}
