package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	//Controlador
	private Controller controller;
	
	//las columnas son: Time y Desc.
	private String[] columnas= {"Time", "Desc."};
	
	//para la descripcion
	private List<Event> listaEventos;
	
	public EventsTableModel(Controller ctr) {
		this.controller = ctr;
		this.listaEventos = new ArrayList<Event>();
		this.controller.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.listaEventos.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
	
		if(columnIndex <= 1 && columnIndex >= 0) {
			if(columnIndex == 1) {//descripcion eventos
				obj = this.listaEventos.get(rowIndex).toString();//descripcion del evento
			}else {//time
				obj = this.listaEventos.get(rowIndex).getTime();//tiempo que tarda en ejecutarse el evento
			}
			
		}else {
			JOptionPane.showConfirmDialog(null,"Columna Incorrecta","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		return obj;
	}


	@Override
	 public String getColumnName(int column) {
		return this.columnas[column]; 
	 }

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.listaEventos = new ArrayList<>(events);
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		this.listaEventos = new ArrayList<>(events);
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.listaEventos = new ArrayList<>(events);
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		this.listaEventos = new ArrayList<>(events);
		fireTableStructureChanged();
	}

}
