package simulator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private RoadMap mapaCarreteras;//se almacenan todos los objetos de la simulacion
	private Queue<Event> colaEventos;
	private List<TrafficSimObserver> listaObservadores;
	private int tiempoPaso = 0;
	
	public TrafficSimulator(RoadMap mapaCarreteras, Queue<Event> colaEventos, int tiempoPaso) {		
		this.mapaCarreteras = mapaCarreteras;
		this.colaEventos = colaEventos;
		this.tiempoPaso = tiempoPaso;
	}
	
	public TrafficSimulator() {
		this.mapaCarreteras = new RoadMap();
		this.listaObservadores = new ArrayList<TrafficSimObserver>();
		this.colaEventos =  new PriorityQueue<Event>();
		
	}
	public void addEvent(Event e) {
		if(e._time <= this.tiempoPaso) 
			throw new IllegalArgumentException();
		
		colaEventos.add(e);//añade a la cola
		
		for(TrafficSimObserver observador : this.listaObservadores) {
			observador.onEventAdded(mapaCarreteras, colaEventos, e, tiempoPaso);
		}
		
	}
	
	
	public void advance() {
			this.tiempoPaso++;
		
			/*
			for (TrafficSimObserver obs : this.listaObservadores) {
				obs.onAdvance(mapaCarreteras, this.colaEventos, this.tiempoPaso);
	 		}
			*/
			
			
			//recorro la lista hasta que este vacia mientras que el tiempo de la primera posicion de la lista sea igual que tiempo paso
			while (this.colaEventos.size() > 0 && this.colaEventos.peek().getTime() == this.tiempoPaso){
				//.peek() obtenemos el primer elemento de la cola sin eliminarlo
				
				Event e = this.colaEventos.element();//devuelve el primer elemento pero si esta vacía lanza excepcion
	            e.execute(mapaCarreteras);
	            this.colaEventos.remove();
		    }
			for(Junction j : this.mapaCarreteras.getJunctions()) {//llamar metodo advance de todos los cruces
				j.advance(tiempoPaso);
			}
			for(Road r : this.mapaCarreteras.getRoads()) {//llamar al metodo advance de todas las carreteras
				r.advance(tiempoPaso);
			}
			//(RoadMap map, Collection<Event> events, int time);
			
			for(TrafficSimObserver observador : this.listaObservadores) {
				observador.onAdvance(mapaCarreteras, colaEventos, tiempoPaso);
			}
	}
	public void reset() {
		
	
		this.colaEventos.clear();
		this.mapaCarreteras.reset();
		this.tiempoPaso = 0;
		
		for(TrafficSimObserver observador : this.listaObservadores) {
			observador.onReset(mapaCarreteras, colaEventos, tiempoPaso);
		}
		
		
	}
	
	
	
	public JSONObject report() {
		JSONObject json = new JSONObject();
		
		json.put("time", this.tiempoPaso);
		// llamo al report de road map
		json.put("state", mapaCarreteras.report());
		
		return json;
    }

	
	//Cambiar
	@Override
	public void addObserver(TrafficSimObserver o) {
		if (!this.listaObservadores.contains(o))this.listaObservadores.add(o);
 		o.onRegister(mapaCarreteras,this.colaEventos, this.tiempoPaso);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if (this.listaObservadores.contains(o))this.listaObservadores.remove(o);
		
	}
		
}
	
	
	
	
	
	

