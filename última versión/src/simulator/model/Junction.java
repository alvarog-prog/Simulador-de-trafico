package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	/*
	 *  conectan unas carreteras con otras, organizando el tráfico a través de semáforos.
	 *   Los semáforos permiten decidir qué vehı́culos pueden avanzar a la próxima carretera de su itinerario. 
	 *   Cada cruce tendrá asociada una colección de carreteras que llegan a él
	 *   , a las que denominaremos carreteras entrantes. 
	 *   Desde un cruce sólo se puede llegar directamente a otro cruce a través de una única carretera.
	 */
	
	private List<Road> listaCarreterasEntrantes = new ArrayList<>();
	private Map<Junction,Road> mapaCarreterasSalientes = new HashMap<>();
	private List<List<Vehicle>> listaColas = new ArrayList<>();
	private Map<Road,List<Vehicle>> CarreteraCola = new HashMap<>();
	private int semaforoEnVerde = 0;//inidice de la carretera entrante que tiene el semaforo en verde
	private int ultimoPasoSemaforo = 0;
	private LightSwitchingStrategy estrategiaSemaforo;// estrategia para cambiar de color los semáforos.
	private DequeuingStrategy estrategiaColas;//estrategia para eliminar vehı́culos de las colas.
	private int xCoor = 0;//para dibujar
	private int yCoor = 0;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(id);
		  if(xCoor < 0 || yCoor < 0) {
			  throw new IllegalArgumentException("Las coordenadas no pueden ser negativas");
		  }
		  if(lsStrategy == null || dqStrategy == null) {
			  throw new IllegalArgumentException("Estrategia nula");
		  }
		  this.estrategiaSemaforo = lsStrategy;
		  this.estrategiaColas = dqStrategy;
		  this.xCoor = xCoor;
		  this.yCoor = yCoor;
		  this.semaforoEnVerde = -1;
	}
	public List<List<Vehicle>> getListaColas() {
		return listaColas;
	}
	@Override
	void advance(int time) {
	    // Si hay un semáforo en verde, procesamos los vehículos
	    if (semaforoEnVerde > -1) {
	        List<Vehicle> cola = listaColas.get(semaforoEnVerde);
	        List<Vehicle> vehiculosQueAvanzan = estrategiaColas.dequeue(cola);

	        while (!vehiculosQueAvanzan.isEmpty()) {
	            Vehicle v = vehiculosQueAvanzan.remove(0);
	            v.moveToNextRoad();
	            cola.remove(v);
	        }
	    }

	    
	    // Determinamos el próximo semáforo en verde
	    
	    int nuevoSemaforo = 0;
	    nuevoSemaforo = estrategiaSemaforo.chooseNextGreen(listaCarreterasEntrantes, listaColas, semaforoEnVerde, ultimoPasoSemaforo, time);
	    

	    // Si el semáforo debe cambiar, actualizamos los valores
	    if (nuevoSemaforo != semaforoEnVerde) {
	        ultimoPasoSemaforo = time;
	        semaforoEnVerde = nuevoSemaforo;
	    }
	}

	public int getX() {
		return xCoor;
	}
	public int getY() {
		return yCoor;
	}
	public List<Road> getListaCarreteras() {
		return listaCarreterasEntrantes;
	}
	public int getSemaforoEnVerde() {
		return semaforoEnVerde;
	}
	
	public String getSemVerdeString() {//si todos estan en rojo mostramos NONE
		String semaforo = null;
		int n = this.getSemaforoEnVerde();
		if(n != -1) semaforo = this.listaCarreterasEntrantes.get(n).getId();//si no estan todos en rojo, ponemos el id de la carretera con semaforo en verde
		else semaforo = "NONE";
		return semaforo;
	}

	public void setSemaforoEnVerde(int semaforoEnVerde) {
		this.semaforoEnVerde = semaforoEnVerde;
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		   JSONObject json = new JSONObject();
	        json.put("id", this._id);
	        if(this.semaforoEnVerde != -1)
	            json.put("green",this.listaCarreterasEntrantes.get(this.semaforoEnVerde).getId());
	        else {//todos los semáforos están en rojo
	            json.put("green","none");//mostramos none
	        }
	        //debemos mostrar la lista de colas,entonces creamos un array
	        JSONArray array = new JSONArray();
	        json.put("queues",array);
	        for(Road r : this.listaCarreterasEntrantes) {
	            JSONObject roads = new JSONObject();
	            array.put(roads);
	            roads.put("road",r.getId());
	            //de cada carretera debemos mostrar sus vehículos
	            JSONArray array2 = new JSONArray();//creamos un array para los vehículos
	            roads.put("vehicles",array2);
	            for(Vehicle v : this.CarreteraCola.get(r)) {
	                array2.put(v.getId());//mostramos el id de cada uno.
	            }
	        }
	        return json;
	}
	public void addIncommingRoad(Road r) {
		 if(r.getDest() != this) 
	            throw new IllegalArgumentException("El cruce destino no coincide con el actual");

	        this.listaCarreterasEntrantes.add(r);
	        List<Vehicle> cola = new LinkedList<>();

	        this.listaColas.add(cola);

	        this.CarreteraCola.put(r, cola);
    }


    public void addOutGoingRoad(Road r) {
    	 Junction j = r.getSrc();
         if(this.equals(j) && !this.mapaCarreterasSalientes.containsKey(j))
             this.mapaCarreterasSalientes.put(r.getDest(), r);
         else {
             throw new IllegalArgumentException("Excepcion");
         }    
    }
	public Road roadTo(Junction junction) {
		return this.mapaCarreterasSalientes.get(junction);
		
	}
	public void enter(Vehicle v) {
        Road r = v.getRoad();
        List<Vehicle> q = this.CarreteraCola.get(r);
        q.add(v);
    }
}
