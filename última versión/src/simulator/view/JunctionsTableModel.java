package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel  implements TrafficSimObserver{

	//Controlador
	private Controller controller;
	
	//Columnas
	private String[] columnas = {"id","Green","Queues"};
	
	//Filas
	private List<Junction> listaCruces;
	
	
	public JunctionsTableModel(Controller ctr) {
		this.controller = ctr;
		this.listaCruces = new ArrayList<Junction>();
		this.controller.addObserver(this);
	}
	
	
	@Override
	public int getRowCount() {
		return this.listaCruces.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		if(columnIndex <= 2 && columnIndex >= 0) {
			if(columnIndex == 0) obj = this.listaCruces.get(rowIndex).getId();
			else if(columnIndex == 1) obj = this.listaCruces.get(rowIndex).getSemVerdeString();//si estan todos en rojo ponemos NONE
			else if(columnIndex == 2) {//colas de las carreteras 
				String cola = "";
				for(Road r : this.listaCruces.get(rowIndex).getListaCarreteras()) {
					cola += r.getId() + ":" + r.getVehicles().toString() + " ";
				}
				obj = cola;
			}
		}else {
			JOptionPane.showConfirmDialog(null, "Columna no valida","ERROR",JOptionPane.ERROR_MESSAGE);
		}	
		return obj;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.setListaCruces(map.getJunctions());
		fireTableStructureChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		this.setListaCruces(map.getJunctions());
		fireTableStructureChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.setListaCruces(map.getJunctions());
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		this.setListaCruces(map.getJunctions());
		fireTableStructureChanged();
	}

	
	public void setListaCruces(List<Junction> listaCruces) {
		this.listaCruces = listaCruces;
	}

	@Override
	 public String getColumnName(int column) {
		return this.columnas[column]; 
	 }
}
