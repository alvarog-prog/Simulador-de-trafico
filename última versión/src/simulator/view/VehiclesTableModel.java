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
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	//Controlador
	private Controller controller;
	
	//Columnas
	private String[] columnas = {"id","Location","Itinerary","CO2 Class","Max. Speed","Speed", "Total CO2", "Distance"};
	
	
	//Filas
	private List<Vehicle> listaVehiculos;
			
	public VehiclesTableModel(Controller ctr) {
		this.controller = ctr;
		this.listaVehiculos = new ArrayList<Vehicle>();
		this.controller.addObserver(this);
	}
	
	
	@Override
	public int getRowCount() {
		return this.listaVehiculos.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		if(columnIndex <= 7 || columnIndex >= 0) {
			if(columnIndex == 0) {//primera columna id del vehiculo
				obj = this.listaVehiculos.get(rowIndex).getId();
			}else if(columnIndex == 1) {//segunda columna location
				obj = this.listaVehiculos.get(rowIndex).getLocation();
				VehicleStatus estado = this.listaVehiculos.get(rowIndex).getStatus();//seleccionamos el estado del vehiculo
				StringBuilder texto = new StringBuilder();
				if(estado.equals(VehicleStatus.PENDING)) {
					texto.append("Pending");
				}else if(estado.equals(VehicleStatus.WAITING)) {
					texto.append("Waiting:" + this.listaVehiculos.get(rowIndex).getSrcJunction());
				}else if(estado.equals(VehicleStatus.TRAVELING)) {//si el vehiculo viaja muestra su carretera actual y su posicion
					texto.append(this.listaVehiculos.get(rowIndex).getRoad() + ": " + this.listaVehiculos.get(rowIndex).getLocation());
				}else if(estado.equals(VehicleStatus.ARRIVED)) {
					texto.append("Arrived");
				}
				obj = texto.toString();
				
			}else if(columnIndex == 2) {//tercera columna el itinerario
				obj = this.listaVehiculos.get(rowIndex).getItinerary();
			}else if(columnIndex == 3) {//cuarta columna clase contaminacion
				obj = this.listaVehiculos.get(rowIndex).getContClass();
			}else if(columnIndex == 4) {//quinta columna max speed
				obj = this.listaVehiculos.get(rowIndex).getMaxSpeed();
			}else if(columnIndex == 5) {//sexta columna speed
				obj = this.listaVehiculos.get(rowIndex).getSpeed();
			}else if(columnIndex == 6) {//septima columna contaminacion total
				obj = this.listaVehiculos.get(rowIndex).getTotalCO2();
			}else {//octava columna(ultima) distancia total recorrida
				obj = this.listaVehiculos.get(rowIndex).getDistTotalRec();
			}
			
		}else {
			JOptionPane.showConfirmDialog(null,"Columna Incorrecta","ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return obj;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaVehiculos(map.getVehicles());
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		fireTableStructureChanged();
		this.setListaVehiculos(map.getVehicles());
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaVehiculos(map.getVehicles());
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		fireTableStructureChanged();
		this.setListaVehiculos(map.getVehicles());
	}


	public void setListaVehiculos(List<Vehicle> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
	}
	
	@Override
	 public String getColumnName(int column) {
		return this.columnas[column]; 
	 }

}
