package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator trafic;
	private Factory<Event> factoria;
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		  // TODO complete
		if(sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("exception");
		}
		this.trafic = sim;
		this.factoria = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		//jo es una estructura JSON asociada a dicho evento
		if (jo.has("events")){
			for(int i = 0; i < jo.getJSONArray("events").length(); i++) {
				//extraigo cada evento de jo y los voy creando desde la factoria de eventos
				this.trafic.addEvent(this.factoria.create_instance(jo.getJSONArray("events").getJSONObject(i)));
			}
		}else {
			throw new IllegalArgumentException("No incluye eventos.");
		}
		

	}
	
	public void run(int n, OutputStream out) {
		
		PrintStream salida = new PrintStream(out);
		salida.println("{");
		salida.println("\"states\" : [");
		
		for (int i = 0; i < n; i++)
		{
			trafic.advance();
			salida.print(trafic.report().toString());
			if (i < n)
			{
				salida.println(",");
			}
		}
		
		salida.println("]");
		salida.println("}");
		
	}
	public void reset() {
		trafic.reset();
	}
	
	public void addObserver(TrafficSimObserver o){
		this.trafic.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		this.trafic.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		this.trafic.addEvent(e);
	}
	
	public void run(int n) {
		for(int i = 0; i < n; i++) {
			this.trafic.advance();
		}
		
	}
}
