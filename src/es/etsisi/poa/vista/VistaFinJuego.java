package es.etsisi.poa.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class VistaFinJuego {

	private JFrame frame;
	private JLabel lblNewLabel;
	/**
	 * Create the application.
	 */
	public VistaFinJuego(String mensaje, JFrame vistaPadre) {
		lblNewLabel = new JLabel(mensaje, JLabel.CENTER);
		
		initialize(vistaPadre);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final JFrame vistaPadre) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 16));	
		lblNewLabel.setForeground(new Color(207, 8, 8));
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addMouseListener(new MouseAdapter()   {
			
			public void mouseClicked(MouseEvent e)  
		    {
				//Cerrar esta venta y la del padre
				frame.setVisible(false);
		    	frame.dispose();
		    	
		    	vistaPadre.setVisible(false);
		    	vistaPadre.dispose();
		    }  
		});
		
		frame.getContentPane().add(btnAceptar, BorderLayout.SOUTH);
		frame.getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		this.frame.setVisible(true);
	}

}
