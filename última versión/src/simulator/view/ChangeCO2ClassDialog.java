package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog implements ActionListener{

	//Ventana de dialogo
	
	private int estadoCO2;
	private JPanel panelPrincipal;
	private JPanel panel2;
	private JPanel panelBotones;
	private JLabel textoPrincipal;
	private JLabel vehicleTexto;
	private JLabel CO2texto;
	private JLabel ticksTexto;
	private JButton botonOk;
	private JButton botonCancel;
	private  JComboBox<Vehicle> vehicleDesplegable;//Desplegable para las opciones de Vehicle
 	private JSpinner ticksDesplegable;//Desplegable para las opciones de ticks
 	private DefaultComboBoxModel<Vehicle> vehiculos;
 	
 	private DefaultComboBoxModel<Integer> co2;
 	private  JComboBox<Integer> co2Desplegable;//Desplegable para las opciones de CO2
 	
	public ChangeCO2ClassDialog(Frame frame) {
		super(frame,true);
		initGUI();
	}
	
	
	public void initGUI() {
		
		//Asignamos el título de la ventana
		setTitle("Change CO2 Class");
		
		//creamos los botones
		this.botonCancel = new JButton("Cancel");
		this.botonOk = new JButton("OK");
		
		//Quitamos la caja que recubre el texto dentro del boton
		this.botonCancel.setFocusable(false);
		this.botonOk.setFocusable(false);
		
		//creamos los paneles
		this.panelPrincipal = new JPanel();
		this.panelBotones = new JPanel();
		this.panel2 = new JPanel();
			
		//creamos los textos
		this.textoPrincipal = new JLabel("<html>Schedule an event to change the CO2 of a vehicle after<br> a given number of simulation ticks from now</html");	
		this.vehicleTexto = new JLabel("Vehicle");
		this.vehicleTexto.setHorizontalTextPosition(JLabel.CENTER);	
		this.CO2texto = new JLabel("CO2 class");
		this.CO2texto.setHorizontalTextPosition(JLabel.CENTER);
		this.ticksTexto = new JLabel("Ticks");
		this.ticksTexto.setHorizontalTextPosition(JLabel.CENTER);
		
		//creamos los desplegables
		this.vehiculos = new DefaultComboBoxModel<Vehicle>();
		this.co2 = new DefaultComboBoxModel<Integer>(); //(val inicial,val min,val max,incr o decr al pulsar)
		this.vehicleDesplegable = new JComboBox<Vehicle>(this.vehiculos);
		this.ticksDesplegable = new JSpinner(new SpinnerNumberModel(10, 1, 99999, 1));
		this.co2Desplegable = new JComboBox<Integer>(this.co2);
		
		this.vehicleDesplegable.setVisible(true);
		this.co2Desplegable.setVisible(true);
		
		this.ticksDesplegable.setMinimumSize(new Dimension(80, 30));
 		this.ticksDesplegable.setMaximumSize(new Dimension(200, 30));
 		this.ticksDesplegable.setPreferredSize(new Dimension(80, 30));
		
 		this.botonCancel.addActionListener(this);
 		this.botonOk.addActionListener(this);
 		
 		
		//añadir panelPrincipal al frame
		this.add(this.panelPrincipal);
		
		//Añadimos el texto principal
		panelPrincipal.add(this.textoPrincipal);
		
		//añadir al panel principal los paneles secundarios
		this.panelPrincipal.add(this.panel2);
		this.panelPrincipal.add(this.panelBotones);
		
		
		//Añadimos los desplegables	
		//vehiculos
		this.panel2.add(this.vehicleTexto);
		this.vehicleDesplegable.setVisible(true);
		this.panel2.add(this.vehicleDesplegable);
		
		//CO2
		this.panel2.add(this.CO2texto);
		this.co2Desplegable.setVisible(true);	
		this.panel2.add(this.co2Desplegable);
		
		//ticks
		this.panel2.add(this.ticksTexto);
		this.ticksDesplegable.setVisible(true);
		this.panel2.add(this.ticksDesplegable);
		
		//Añadimos los botones(Cancel y OK)
		//Cancel
		this.panelBotones.add(botonCancel);
		this.panelBotones.add(botonOk);
	
		//setResizable(false);
		setPreferredSize(new Dimension(500, 200));
		pack();
		setVisible(false);
	}

	
	public int dameEstado(RoadMap mapa) {
		for(Vehicle vehicle : mapa.getVehicles()) {
			this.vehiculos.addElement(vehicle);
		}
		for(int i = 0; i < 11; i++) {
			this.co2.addElement(i);
		}
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		this.setVisible(true);
		return this.estadoCO2;//0 si se ha pulsado en cancelar, 1 si OK
	}

	public int getTicksTexto() {
		return (Integer) this.ticksDesplegable.getValue();//devuelve lo seleccionado en el desplegable
	}


	public Vehicle getVehicleDesplegable() {
		return (Vehicle) this.vehiculos.getSelectedItem();//devuelve lo seleccionado en el desplegable
	}


	public int getCo2Desplegable() {
		return (Integer) this.co2Desplegable.getSelectedItem();//devuelve lo seleccionado en el desplegable
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.botonCancel) {
			this.estadoCO2 = 0;
			this.setVisible(false);
		}else if(e.getSource() == this.botonOk) {
			this.estadoCO2 = 1;
			this.setVisible(false);
		}
		
	}
	
	
	
	
	
}
