package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog implements ActionListener{
	
	//Paneles
	private JPanel panelPrincipal;
	private JPanel panelBotones;
	private JPanel panelOpciones;
	
	//botones
	private JButton botonCancel;
	private JButton botonOk;
	
	//textos
	private JLabel textoPrincipal;
	private JLabel textoWeather;
	private JLabel textoRoad;
	private JLabel textoTicks;
	
	//Desplegables
	private DefaultComboBoxModel<Road> carreteras;
	private DefaultComboBoxModel<Weather> condAtmos;
	private JComboBox<Road> roadsDesplegable;
	private JComboBox<Weather> weatherDesplegable;
	private JSpinner ticks;
	
	private int estado = 0;
	
	
	public ChangeWeatherDialog(Frame frame) {
		super(frame,true);
		this.initGUI();
	}
	
	
	public void initGUI() {
		
		//Título de la ventana
		setTitle("Change Road Weather");
		
		//creamos paneles
		this.panelPrincipal = new JPanel();
		this.panelBotones = new JPanel();
		this.panelOpciones = new JPanel();
		
		//creamos botones
		this.botonCancel = new JButton("Cancel");
		this.botonOk = new JButton("OK");
			
		//creamos desplegables
		this.carreteras = new  DefaultComboBoxModel<Road>();
		this.condAtmos = new  DefaultComboBoxModel<Weather>(); 
		this.roadsDesplegable = new JComboBox<Road>(this.carreteras);
		this.weatherDesplegable = new JComboBox<Weather>(this.condAtmos);
		this.ticks = new JSpinner(new SpinnerNumberModel(10, 1, 300, 1));//(val inicial,val min,val max,incr o decr al pulsar))
		
		this.ticks.setMinimumSize(new Dimension(80, 30));
 		this.ticks.setMaximumSize(new Dimension(200, 30));
 		this.ticks.setPreferredSize(new Dimension(80, 30));
		
		
		//creamos los textos
		this.textoPrincipal = new JLabel("<html>schedule an event to change the weather of a road after a given number of <br> simulation ticks from now</html>");
		this.textoPrincipal.setAlignmentX(CENTER_ALIGNMENT);
		this.textoWeather = new JLabel("Weather",JLabel.CENTER);
		this.textoRoad = new JLabel("Road",JLabel.CENTER);
		this.textoTicks = new JLabel("Ticks",JLabel.CENTER);
		
		//Añadimos el panel al frame
		this.add(this.panelPrincipal);
		
		
		//mostramos y configuramos los desplegables
		
		//Desplegable de Roads
		this.panelOpciones.add(this.textoRoad);
		this.roadsDesplegable.setVisible(true);
		this.panelOpciones.add(this.roadsDesplegable);
		
		//Desplegable de Weather
		this.panelOpciones.add(this.textoWeather);
		this.weatherDesplegable.setVisible(true);
		this.panelOpciones.add(this.weatherDesplegable);
		
		//Ticks
		this.panelOpciones.add(this.textoTicks);
		this.ticks.setVisible(true);
		this.panelOpciones.add(this.ticks);
		
		//para ejecutar actionPerformed
		this.botonCancel.addActionListener(this);
		this.botonOk.addActionListener(this);
			
		//botones cancelar y Ok
		//Cancel
		this.panelBotones.add(this.botonCancel);
		
		//OK
		this.panelBotones.add(this.botonOk);
				
		//Ajustamos posición del texto principal
		this.textoPrincipal.setHorizontalAlignment(JLabel.CENTER);
		this.panelBotones.setAlignmentX(CENTER_ALIGNMENT);	
		this.panelOpciones.setAlignmentX(CENTER_ALIGNMENT);
							
		//añadimos al panel principal
		this.panelPrincipal.add(this.textoPrincipal);
		this.panelPrincipal.add(this.panelOpciones);
		this.panelPrincipal.add(this.panelBotones);
		
		//setResizable(false)
		setPreferredSize(new Dimension(500, 200));		
		pack();
		setVisible(false);
	}


	public int getTicks() {
		return (Integer) ticks.getValue();
	}

	
	public Road getRoadsDesplegable() {
		return (Road) roadsDesplegable.getSelectedItem();
	}


	public Weather getWeatherDesplegable() {
		return (Weather) weatherDesplegable.getSelectedItem();
	}


	public int dameEstado(RoadMap map) {
	
		for(Road carretera : map.getRoads()) {
			this.carreteras.addElement(carretera);
		}
		
		for(Weather weather : Weather.values()) {
			this.condAtmos.addElement(weather);
		}
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
 		setVisible(true);
		return this.estado;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.botonCancel) {
			this.estado = 0;
			this.setVisible(false);
		}else if(e.getSource() == this.botonOk) {
			this.estado = 1;
			this.setVisible(false);
		}
		
	}
	
}


