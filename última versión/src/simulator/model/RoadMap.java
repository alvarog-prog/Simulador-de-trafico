package simulator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {

private List<Junction> listaCruces= new ArrayList<>();
private List<Road> listaCarreteras=  new ArrayList<>();
private List<Vehicle> listaVehiculos=  new ArrayList<>();
private Map<String,Junction> mapaCruces = new HashMap<>();
private Map<String,Road> mapaCarreteras = new HashMap<>();
private Map<String,Vehicle> mapaVehiculos =  new HashMap<>();

	public RoadMap() {		
		
	}
	
	public void addJunction(Junction j) {	
		if(this.getJunction(j.getId()) != null) 
			throw new IllegalArgumentException("ya existe ese cruce");
			
		this.listaCruces.add(j);//añade el cruce a la lista de cruces
		this.mapaCruces.put(j.getId(), j);	//modifica el mapa
	}
	
	public void addRoad(Road r) {
		if(this.mapaCarreteras.containsKey(r.getId())) {
			throw new IllegalArgumentException("error");
		}
		this.listaCarreteras.add(r);//añade la carretera a la lista de carreteras
		this.mapaCarreteras.put(r.getId(), r);//modifica el mapa
	}
	
	public void addVehicle(Vehicle v) {
		
        if(this.getVehicle(v.getId()) == null) {//no existe otro vehiculo con el mismo id
        	for (int i = 0; i < v.getItinerary().size()-1; i++){
    			if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i+1)) == null)
    				throw new IllegalArgumentException("El itineario es nulo.");
    			//si entra aquí no  existen carreteras que conecten los cruces consecutivos de su itinerario.
    		}
            
            this.listaVehiculos.add(v);
            this.mapaVehiculos.put(v.getId(), v);
        }else {
            throw new IllegalArgumentException("Vehiculo ya existe");
        }
    }

	
	public Junction getJunction(String id) {		
		return this.mapaCruces.get(id);
	}
	
	public Road getRoad(String id) {	
	  return this.mapaCarreteras.get(id);
	}
	  
	public Vehicle getVehicle(String id) {
		return this.mapaVehiculos.get(id);
	 }
	  
	 public List<Junction> getJunctions(){
	     return listaCruces;
	  }
	  	
	  public List<Road> getRoads(){
	      return listaCarreteras;
	  }
	  
	  public List<Vehicle> getVehicles(){
	        return listaVehiculos;
	  }
	  
	  void reset() {
			  this.limpiaCarreteras();
			  this.limpiaCruces();
			  this.limpiaVehiculos();
			  this.mapaCarreteras.clear();
			  this.mapaCruces.clear();
			  this.mapaVehiculos.clear();
	  
	  //Limpiar mapas
	  } 
	
	private void limpiaVehiculos() {
			for(int i = 0; i < this.listaVehiculos.size(); i++) {
					this.listaVehiculos.remove(i);
			}
	}
	private void limpiaCruces() {
		for(int i = 0; i < this.listaCruces.size(); i++) {
			this.listaCruces.remove(i);
		}
	}
	private void limpiaCarreteras() {
		for(int i = 0; i < this.listaCarreteras.size(); i++) {
			this.listaCarreteras.remove(i);
		}
	}
	


	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		JSONArray arrayRoad = new JSONArray();
		json.put("roads", arrayRoad);
		for(Road r : listaCarreteras) {
			arrayRoad.put(r.report());
		}
		JSONArray arrayJunctions = new JSONArray();
		json.put("junctions", arrayJunctions);
		for(Junction j : listaCruces) {
			arrayJunctions.put(j.report());
		}
		JSONArray arrayVehicles = new JSONArray();
		json.put("vehicles", arrayVehicles);
		for(Vehicle v : listaVehiculos) {
			arrayVehicles.put(v.report());
		}
		
		return json;
	}
}