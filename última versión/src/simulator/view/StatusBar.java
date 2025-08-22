package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	
	//textos
	private JLabel time;
	private JLabel eventoAdd;
	private JLabel tiempoJuego;
	
	//Contolador
	private Controller controller;
	
	
	public StatusBar(Controller _ctrl) {
		initGUI();
		this.controller = _ctrl;
		this.controller.addObserver(this);
	}
	
	public void initGUI() {
		this.time = new JLabel("Time:",JLabel.LEFT);
		this.tiempoJuego = new JLabel("");
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(time);
		this.add(tiempoJuego);		
		this.eventoAdd = new JLabel("");
		this.add(eventoAdd);
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		String tiempo = "" + time;
		this.tiempoJuego.setText(tiempo);
		this.eventoAdd.setText("");
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {//cuando se añada un evento
		String tiempo ="" + time;
		this.tiempoJuego.setText(tiempo);
		this.eventoAdd.setText(e.toString());//se muestra el nombre de el evento añadido
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		String tiempo ="" + time;
		this.tiempoJuego.setText(tiempo);
		this.eventoAdd.setText("");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		String tiempo = "" + time;
		this.tiempoJuego.setText(tiempo);
		this.eventoAdd.setText("Welcome!");
	}

}
