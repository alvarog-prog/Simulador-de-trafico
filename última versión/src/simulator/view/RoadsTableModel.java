package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	//Controlador
	private Controller controller;
	
	//Columnas
	private String[] columnas = {"id","Length","Weather","Max. Speed","Speed Limit","Total CO2","CO2 Limit"};
	
	//Filas
	private List<Road> listaCarreteras;
	
	
	public RoadsTableModel(Controller ctr) {
		this.controller = ctr;
		this.listaCarreteras = new ArrayList<Road>();
		this.controller.addObserver(this);
	}
	
	
	@Override
	public int getRowCount() {
		return this.listaCarreteras.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		
		if(columnIndex >= 0 && columnIndex <= 6) {
			if(columnIndex == 0) obj = this.listaCarreteras.get(rowIndex).getId();
			else if(columnIndex == 1) obj = this.listaCarreteras.get(rowIndex).getLength();
			else if(columnIndex == 2) obj = this.listaCarreteras.get(rowIndex).getWeather();
			else if(columnIndex == 3) obj = this.listaCarreteras.get(rowIndex).getMaxSpeed();
			else if(columnIndex == 4) obj = this.listaCarreteras.get(rowIndex).getSpeedLimit();
			else if(columnIndex == 5) obj = this.listaCarreteras.get(rowIndex).getTotalCO2();
			else if(columnIndex == 6) obj = this.listaCarreteras.get(rowIndex).getContLimit();
		}else {
			JOptionPane.showConfirmDialog(null,"Columna no valida", "ERROR",JOptionPane.ERROR_MESSAGE);
		}
		return obj;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaCarreteras(map.getRoads());
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		fireTableStructureChanged();
		this.setListaCarreteras(map.getRoads());
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaCarreteras(map.getRoads());
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaCarreteras(map.getRoads());
	}

	public void setListaCarreteras(List<Road> listaCarreteras) {
		this.listaCarreteras = listaCarreteras;
	}
	
	@Override
	 public String getColumnName(int column) {
		return this.columnas[column]; 
	 }
}
