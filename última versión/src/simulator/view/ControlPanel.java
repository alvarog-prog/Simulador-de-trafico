package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver,ActionListener{
	//barra de botones
	private JToolBar tools;
	
	//botones
	private JButton botonCarga;
	private JButton botonWeather;
	private JButton botonCO2;
	private JButton botonParada;
	private JButton botonRun;
	private JButton botonSalida;
	
	//textos
	private JLabel textoTicks;
	
	//ajustador de ticks
	private JSpinner ticks;
	
	//booleano para controlar la parada de la ejecucion
	private boolean parada = true;
		
	private List<Pair<String,Integer>> listaIdCont;
	private List<Pair<String,Weather>> listaRoadTiempo;
	
	
	private JFileChooser fichero;
	
	private InputStream input;
	private Controller controller;
	private RoadMap map;
	
	private int time;
	
	public ControlPanel(Controller _ctrl) {
		this.controller = _ctrl;
		this.initGUI();
		this.listaIdCont = new ArrayList<>();
		this.listaRoadTiempo = new ArrayList<>();
		this.controller.addObserver(this);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.time = time;
		this.map = map;
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		this.time = time;
		this.map = map;
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.time = time;
		this.map = map;	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		this.time = time;
		this.map = map;
	}

	
	public void initGUI() {
		
		//Barra de Herramientas
		this.tools = new JToolBar();
		this.setLayout(new BorderLayout());
		this.add(this.tools,BorderLayout.PAGE_START);//West izq, East derecha
		
		//Fichero
		this.fichero = new JFileChooser();
		
		//Botones
		this.botonCarga = new JButton();
		this.botonCO2 = new JButton();
		this.botonWeather = new JButton();
		this.botonParada = new JButton();
		this.botonRun = new JButton();
		this.botonSalida = new JButton();
		

		//Desplegable
		this.ticks = new JSpinner(new SpinnerNumberModel(10, 1, 99999, 1));
		
		
		//llamada a creacion botones
		this.botonCargaFichero();
		this.botonCO2();
		this.botonWeather();
		this.botonRun();
		this.botonParada();
		this.botonTicks();
		this.botonSalir();
			
	}
	
	
	//Creacion de los botones	
	public void botonCargaFichero() {
		this.botonCarga.setToolTipText("Carga los archivos de datos a la aplicación");
		ImageIcon imagen = new ImageIcon("./resources/icons/open.png");
 		this.botonCarga.setIcon(imagen);
 		this.botonCarga.addActionListener(this);
 		this.ruta();
 		tools.add(this.botonCarga);
 		tools.addSeparator();
	}
	
	public void botonCO2() {
		this.botonCO2.setToolTipText("Cambio de la clase de contaminación de un vehículo");//ayuda cuando pones el cursor sobre el boton	
 		ImageIcon imagen = new ImageIcon("./resources/icons/co2class.png");//creacion de una imagen, indicando donde esta guardada
 		this.botonCO2.setIcon(imagen);//la incorporamos en el boton
 		this.botonCO2.addActionListener(this);//para cuando presionemos el boton tenga algun efecto
 		tools.add(this.botonCO2);//añadimos el boton a la barra de herramientas
	}
	
	public void botonWeather() {
		this.botonWeather.setToolTipText("Cambio de las condiciones atmosfericas de una carretera");//ayuda cuando pones el cursor sobre el boton	
		ImageIcon imagen = new ImageIcon("./resources/icons/weather.png");
		this.botonWeather.setIcon(imagen);
		this.botonWeather.addActionListener(this);
		tools.add(this.botonWeather);
		tools.addSeparator();
	}
	
	public void botonParada() {
		this.botonParada.setToolTipText("se detiene la ejecucion");//ayuda cuando pones el cursor sobre el boton
		ImageIcon imagen = new ImageIcon("./resources/icons/stop.png");
		this.botonParada.setIcon(imagen);		
		this.botonParada.addActionListener(this);
		tools.add(this.botonParada);
		tools.addSeparator();
	}

	public void botonRun() {
		this.botonRun.setToolTipText("se inicia la ejecucion");//ayuda cuando pones el cursor sobre el boton
		ImageIcon imagen = new ImageIcon("./resources/icons/run.png");
		this.botonRun.setIcon(imagen);		
		this.botonRun.addActionListener(this);
		tools.add(this.botonRun);
	}
	
	public void botonTicks() {
		this.textoTicks = new JLabel("Ticks:");
		tools.add(this.textoTicks);
		tools.add(this.ticks);
		tools.addSeparator();
		this.tools.add(Box.createHorizontalGlue());//crea el espacio entre los ticks y el boton de salida
	}
	
	public void botonSalir() {
		this.botonSalida.setToolTipText("se cierra la aplicacion");//ayuda cuando pones el cursor sobre el boton	
		ImageIcon image = new ImageIcon("./resources/icons/exit.png");
		this.botonSalida.setIcon(image);
		this.botonSalida.addActionListener(this);
	    tools.add(this.botonSalida);
	}
	
	//Buscar el boton accionado
	@Override
	public void actionPerformed(ActionEvent e) {//al pulsar algun boton
		//primero hay que encontrar cual es el boton pulsado
		if(e.getSource() == this.botonCarga)this.load();
		else if(e.getSource() == this.botonCO2) {
			this.changeCO2();
		}else if(e.getSource() == this.botonWeather) {
			this.changeWeather();
		}else if(e.getSource() == this.botonParada) {
			this.parada();
		}else if(e.getSource() == this.botonSalida) {
			this.salida();
		}else if(e.getSource() == this.botonRun) {
			this.parada = false;
			this.activarBarraHerramientas(false);//hasta que no termine la ejecucion indicada no se puede pulsar ningun otro boton salvo parar
			this.run_sim((int) this.ticks.getValue());//simule el numero de ticks indicados en el desplegable general
		}
	}
	
	//ruta de busqueda para cargar los ficheros
	private void ruta() {
		File ruta = new File("./resources/examples/");
		fichero.setCurrentDirectory(ruta);
 		fichero.setMultiSelectionEnabled(false);//esta false asi que solo podemos seleccionar un archivo a la vez	
	}
	
	//Cargar fichero(boton de carga presionado)	
	private void load() {
		int selection = fichero.showOpenDialog(this.getParent());	
		if (selection == JFileChooser.APPROVE_OPTION) {
			// El usuario seleccionó un archivo y pulsó "Abrir"
			try {
				input = new FileInputStream(fichero.getSelectedFile()); // obtenemos el fichero seleccionado
				controller.reset();
				controller.loadEvents(input);
			}catch (FileNotFoundException exception) {
				JOptionPane.showMessageDialog(null,"Error en la carga","ERROR",JOptionPane.ERROR_MESSAGE);
			}	
		}else {
			JOptionPane.showMessageDialog(null, "El usuario ha seleccionado cancelar","CANCEL",JOptionPane.INFORMATION_MESSAGE);
			
		}
	}

	//cambiar clase de contaminacion de un vehiculo (BotonCO2 presionado)
	public void changeCO2() {
		ChangeCO2ClassDialog co2Dialog = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));//creamos dialogo
		int estadoCO2 = co2Dialog.dameEstado(this.map);	//0 si se ha pulsado en cancelar, 1 si OK
		if(estadoCO2 != 0) {//si el usuario le da a OK(hay que aplicar el nuevo evento)
			Pair<String,Integer> par = new Pair<String,Integer>(co2Dialog.getVehicleDesplegable().getId(),//el vehiculo seleccionado por usuario queremos su id
					co2Dialog.getCo2Desplegable());//el co2 seleccionado en el desplegable
			this.listaIdCont.add(par);
			this.controller.addEvent(new SetContClassEvent(time + co2Dialog.getTicksTexto(),this.listaIdCont));//añadimos el evento creado
			
		}	
	}
	
	//cambiar clima de una carretera (BotonWeather presionado)
	public void changeWeather() {
		ChangeWeatherDialog weatherDialog = new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));//creamos dialogo
		int estado = weatherDialog.dameEstado(this.map);//0 si se ha pulsado en cancelar, 1 si OK
		if(estado != 0) {//si el usuario le da a OK(hay que aplicar el nuevo evento)
			Pair<String,Weather> par = new Pair<String,Weather>(weatherDialog.getRoadsDesplegable().getId(),//nos devuelve el id de la carretera seleccionada en el desplegable
					weatherDialog.getWeatherDesplegable());//nos devuelve el clima seleccionado
			try {
				this.listaRoadTiempo.add(par);
				this.controller.addEvent(new SetWeatherEvent(time + weatherDialog.getTicks(),this.listaRoadTiempo));//añadimos el evento
			}catch(Exception e) {
				JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(ControlPanel.this), "Ha ocurrido un error al cambiar el tiempo (" + e + ")");
			}
		}
	}
	
	//Inicio de la ejecucion (BotonRun presionado)
	private void run_sim(int n) {
		if (n > 0 && !this.parada) {//n es el numero de ticks que se quiere simular
			try {
					this.controller.run(1);//recorre todos los advance y ejecutara los eventos 
	         		SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				activarBarraHerramientas(true);
				// TODO show error message
				this.parada = true;
				// TODO enable the toolbar
			}
		} else {
			this.parada = true;//paramos la ejecucion
			activarBarraHerramientas(true);//volvemos a tener la opcion de pulsar otros botones de la barrera de herramientas 
	                // TODO enable the toolbar
		}
	}
	
	
	//Parada de la ejecución (BotonParada presionado)
	public void parada() {
		this.parada = true;
	}
	
	//Salir de la ejecución (BotonSalida presionado)
	private void salida() {
		int respuesta = JOptionPane.showConfirmDialog(null,"¿seguro que quieres cerrar el programa?","SALIR",JOptionPane.YES_NO_OPTION);
		//JOptionPane.YES_NO_OPTION nos muestra dos botones de si o no
		//si es si es 0, si es no es 1
		if(respuesta == 0) System.exit(0);
		else JOptionPane.showMessageDialog(null, "Regresando al programa...");
	}
	
	//activar toolBar
	private void activarBarraHerramientas(boolean b) {//se activan o desactivan todos los botones de la barra de herramientas
		this.botonSalida.setEnabled(b);
		this.botonCO2.setEnabled(b);
		this.botonRun.setEnabled(b);
		this.botonCarga.setEnabled(b);
		this.botonWeather.setEnabled(b);
	}

}
