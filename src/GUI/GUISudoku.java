
package GUI;

import Logica.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISudoku extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected Juego juego;
	protected RejojCronometro cronometro;
	protected JButton[][] tablero;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISudoku frame = new GUISudoku();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame.
	 */
	public GUISudoku() {
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelTablero = new JPanel();
		panelTablero.setBounds(10, 11, 441, 441);
		contentPane.add(panelTablero);
		panelTablero.setLayout(new GridLayout(9, 9, 0, 0));

		JPanel relojPanel=new JPanel();
		relojPanel.setBackground(Color.white);
		relojPanel.setBounds(461, 11, 290, 100);
		relojPanel.setLayout(new GridLayout(1,8));
		contentPane.add(relojPanel);
		JLabel [] arregloLabels=new JLabel[8];

		for(int i=0; i<8; i++) {
			if(i!=2 && i!=5) {
				arregloLabels[i]=new JLabel();
				relojPanel.add(arregloLabels[i]);
			}else {
				ImageIcon imagenPuntos=new ImageIcon(this.getClass().getResource("/ImagenesCrono/dosPuntos.png"));
				arregloLabels[i]=new JLabel(imagenPuntos);
				relojPanel.add(arregloLabels[i]);
				reDimensionar(arregloLabels[i],imagenPuntos);
			}	
		}
		cronometro=new RejojCronometro(arregloLabels);

		crear(panelTablero);

		JButton botonReiniciar = new JButton();
		botonReiniciar.setEnabled(false);
		botonReiniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//panelCronometro.restart();
				reiniciar();
			}
		});


		botonReiniciar.setBounds(580, 349, 75, 75);
		botonReiniciar.setBackground(new Color(255,255,255));
		ImageIcon RestartImagen=new ImageIcon(this.getClass().getResource("/Reset.jpg"));
		botonReiniciar.setIcon(RestartImagen);
		reDimensionar(botonReiniciar, (ImageIcon)botonReiniciar.getIcon());
		contentPane.add(botonReiniciar);


		JButton botonIniciar = new JButton();
		botonIniciar.setActionCommand("i");
		botonIniciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(botonIniciar.isEnabled()) {
					Component boton = e.getComponent();
					String codigo = ((JButton) boton).getActionCommand();
					if(codigo=="i") {
						cronometro.iniciaCrono();
						iniciar();

						botonIniciar.setActionCommand("r");
						//botonFrenar.setEnabled(true);
						botonReiniciar.setEnabled(true);
						botonIniciar.setEnabled(false);
					}
					if(codigo=="r") {
						panelTablero.setVisible(true);
					}
				}
			}
		});
		botonIniciar.setBounds(580, 230, 75, 75);
		botonIniciar.setBackground(new Color(255,255,255));
		ImageIcon startImagen=new ImageIcon(this.getClass().getResource("/Start.jpg"));
		botonIniciar.setIcon(startImagen);
		reDimensionar(botonIniciar, (ImageIcon)botonIniciar.getIcon());

		contentPane.add(botonIniciar);
	}

	/**
	 *	Crea la grafica del juego.
	 * @param panelTablero -- Panel contenedor del tablero del juego.
	 */
	public void crear(JPanel panelTablero) {
		juego = new Juego();
		if(juego.getTablero()!=null) {
			tablero(panelTablero);
		}	
		else {
			JOptionPane.showConfirmDialog(new JFrame(),"SOLUCION INCORRECTA","ERROR",JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Crea y organiza la grafica del tablero del juego.
	 * @param panelTablero -- Panel contenedor del tablero del juego
	 */
	protected void tablero(JPanel panelTablero) {
		tablero = new JButton[juego.getCantFilas()][juego.getCantColumnas()];
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				crearBoton(f, c);
				tablero[f][c].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String codigo = e.getActionCommand();
						logicaBoton(codigo);
					}
				});
				panelTablero.add(tablero[f][c]);
				tablero[f][c].setEnabled(false);
			}
	}

	/**
	 * Organiza los botones del juego
	 * @param f -- Corresponde a las filas del juego.
	 * @param c -- Corresponde a las columnas del juego.
	 */
	protected void crearBoton(int f, int c) {
		tablero[f][c] = new JButton();
		tablero[f][c].setActionCommand(f+","+c);
		tablero[f][c].setBackground(Color.white);
		tablero[f][c].setEnabled(false);
		if(f==0 || f==3 || f==6) {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
		}
		else if(f==8) {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
		}
		else {
			if(c==0 || c==3 || c==6)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
			else if(c==8)
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
			else
				tablero[f][c].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		}
	}

	/**
	 * Crea y asigna la parte lógica de los botones del tablero del juego.
	 * @param codigo -- Índice asociado a la ubicación del botón en el tablero.
	 */
	protected void logicaBoton(String codigo) {
		String[] fyc= codigo.split(",");
		int f=Integer.parseInt(fyc[0]);
		int c=Integer.parseInt(fyc[1]);
		Celda celda = juego.getCelda(f,c);
		String[] repetidos;
		boolean ganado;
		if(tablero[f][c].isEnabled()) 
			celda.actualizar();
		juego.setControl(celda.getValor());
		if(celda.getValor() != 0) {
			tablero[f][c].setIcon(celda.getEntidadGrafica().getImagen());
		}
		else {
			tablero[f][c].setIcon(null);
		}
		reDimensionar(tablero[f][c], celda.getEntidadGrafica().getImagen());
		juego.setErrores(codigo);
		repetidos = juego.getErrores();
		pintar(repetidos);
		if(repetidos==null) {
			if(juego.getControl()==(juego.getCantFilas()*juego.getCantColumnas())) {
				ganado = juego.comprobar();
				if(ganado) {
					ganar();

				}
			}
		}
	}


	/**
	 * Crea e inicia el juego mostrando el tablero del juego.
	 */
	public void iniciar() {
		Celda celda;
		juego.crearJuego();
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				celda = juego.getCelda(f, c);

				tablero[f][c].setBackground(new Color(255,255,255));
				tablero[f][c].setIcon(celda.getEntidadGrafica().getImagen());
				reDimensionar(tablero[f][c], celda.getEntidadGrafica().getImagen());
				if(celda.getValor()==0){  
					tablero[f][c].setEnabled(true); //Solo se habilitan los valores que el usuario debe completar.
					tablero[f][c].setIcon(null);
				}
			}

	}

	/**
	 * Reinicia el juego volviendo a su estado incicial.
	 */
	public void reiniciar(){
		for(int f=0; f<tablero.length; f++)
			for(int c=0; c<tablero[0].length; c++) {
				if(tablero[f][c].isEnabled()) {
					tablero[f][c].setIcon(null);
					juego.getCelda(f, c).setValor(0);
				}	
				tablero[f][c].setBackground(new Color(255,255,255));	
			}
		cronometro.reset();
		cronometro.iniciaCrono();
	}

	/**
	 * Pinta los botones cuyos índices son parametrizados.
	 * @param repetidas
	 */
	public void pintar(String[] repetidas) {
		pintar(Color.WHITE);
		if(repetidas!=null) 
			advertencia(repetidas);
	}

	/**
	 * Pinta todo el tablero del juego.
	 * @param color --Color  con el que se va a pintar la grilla de juego.
	 */
	protected void pintar(Color color) {
		for(int f=0; f<juego.getCantFilas(); f++)
			for(int c=0; c<juego.getCantColumnas(); c++)
				tablero[f][c].setBackground(color);	
	}

	/**
	 * Pinta de rojo aquellos botones del tablero del juego que poseen los índices parametrizados.
	 * @param repetidas - Índices donde se desea pintar de rojo.
	 */
	protected void advertencia(String[] repetidas) {
		String[] fyc;
		int fila, columna;
		for(int i=0; i<repetidas.length && repetidas[i]!=null; i++) {
			fyc = repetidas[i].split(",");
			fila = Integer.parseInt(fyc[0]);
			columna = Integer.parseInt(fyc[1]);
			tablero[fila][columna].setBackground(Color.RED);	
		}
	}

	/**
	 * Muestra graficamente que el juegador gano*/
	protected void ganar() {

		pintar(Color.green);
		Component[] componentes = contentPane.getComponents();
		for(int i=0; i<componentes.length; i++)
			componentes[i].setEnabled(false);
		cronometro.cancelar();
		JOptionPane.showMessageDialog(new JFrame(),"SOLUCION CORRECTA. FELICIDADES. \n TIEMPO DE JUEGO: "+cronometro.getTiempo() ,"Dialog",JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}



	protected void reDimensionar(JButton boton, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(boton.getWidth(), boton.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			boton.repaint();
		}
	}

	protected void reDimensionar(JLabel labels, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(35,65,  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			labels.repaint();
		}
	}

	/*
	 * Clase Reloj Cronometro, se encarga de crear el reloj del juego.
	 * */

	private class RejojCronometro{
		private Timer tiempo;
		private int horas,minutos,segundos;
		private JLabel[] arregloLabel;
		private boolean activado;
		private ImageIcon[] imagenes;


		public RejojCronometro(JLabel [] labels) {
			tiempo=new Timer();
			activado=false;
			segundos=minutos=horas=0;
			arregloLabel=labels;
			imagenes=new ImageIcon[10];

			for(int i=0; i<10; i++) {
				imagenes[i]=new ImageIcon(this.getClass().getResource("/ImagenesCrono/"+i+".png"));
			}

			for(int i=0; i<arregloLabel.length; i++) {
				if(i!=2 && i!=5) {
					arregloLabel[i].setIcon(imagenes[0]);
					reDimensionar(arregloLabel[i],imagenes[0]);
				}

			}
		}
		/*Inicia el cronometro del juego
		 * */
		public void iniciaCrono() {
			if(activado)
			{
				tiempo.cancel();
				tiempo=new Timer();
			}
			TimerTask tarea=new TimerTask() {
				@Override
				public void run() {
					if(segundos<59) {
						segundos++;
						arregloLabel[6].setIcon(imagenes[segundos/10]);

						reDimensionar(arregloLabel[6],imagenes[segundos/10]);

						arregloLabel[7].setIcon(imagenes[segundos%10]);
						reDimensionar(arregloLabel[7],imagenes[segundos%10]);
					}else {
						segundos=0;
						arregloLabel[6].setIcon(imagenes[segundos/10]);
						reDimensionar(arregloLabel[6],imagenes[segundos/10]);

						arregloLabel[7].setIcon(imagenes[segundos%10]);
						reDimensionar(arregloLabel[7],imagenes[segundos%10]);
						if(minutos<59) {
							minutos++;

							arregloLabel[3].setIcon(imagenes[minutos/10]);
							reDimensionar(arregloLabel[3],imagenes[minutos/10]);

							arregloLabel[4].setIcon(imagenes[minutos%10]);
							reDimensionar(arregloLabel[4],imagenes[minutos%10]);
						}else {


							minutos=0;

							arregloLabel[3].setIcon(imagenes[minutos/10]);
							reDimensionar(arregloLabel[3],imagenes[minutos/10]);

							arregloLabel[4].setIcon(imagenes[minutos%10]);
							reDimensionar(arregloLabel[4],imagenes[minutos%10]);
							if(horas<59) {

								horas++;

								arregloLabel[0].setIcon(imagenes[horas/10]);
								reDimensionar(arregloLabel[0],imagenes[horas/10]);

								arregloLabel[1].setIcon(imagenes[horas%10]);
								reDimensionar(arregloLabel[1],imagenes[horas%10]);
							}
							else {
								horas=0;

								arregloLabel[0].setIcon(imagenes[horas/10]);
								reDimensionar(arregloLabel[0],imagenes[horas/10]);

								arregloLabel[1].setIcon(imagenes[horas%10]);
								reDimensionar(arregloLabel[1],imagenes[horas%10]);
							}
						}

					}


				}



			};
			tiempo.schedule(tarea,0,1000);
			activado=true;
		}

		/*
		 * Resetea el tiempo de juego
		 * */

		public void reset() {
			segundos=horas=minutos=0;
			arregloLabel[3].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[3],imagenes[0]);

			arregloLabel[4].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[4],imagenes[0]);

			arregloLabel[0].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[0],imagenes[0]);

			arregloLabel[1].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[1],imagenes[0]);


			arregloLabel[6].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[6],imagenes[0]);

			arregloLabel[7].setIcon(imagenes[0]);
			reDimensionar(arregloLabel[7],imagenes[0]);

		}

		public void cancelar() {
			tiempo.cancel();
			tiempo=new Timer();
		}
		public String getTiempo() {
			String retorno=horas+":"+minutos+":"+segundos;
			return retorno;
		}
	}
}