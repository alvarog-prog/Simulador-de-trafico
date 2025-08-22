package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{
	
	public NewVehicleEventBuilder() {
		super("new_vehicle", "Event");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event create_instance(JSONObject data) {
		//vamos sacando todos los datos que tiene el JSON, en este caso el newVehicle que queremos crear
		int time = data.getInt("time");
		String id = data.getString("id");
		
		int clase = data.getInt("class");
		int maxspeed = data.getInt("maxspeed");
		//recorro itinerario y cojo los strings
		List<String> lista = new ArrayList<>();
		
		for(int i = 0; i < data.getJSONArray("itinerary").length(); i++) {
			lista.add(data.getJSONArray("itinerary").getString(i));
			
		}
		return new NewVehicleEvent(time, id, maxspeed, clase, lista);
	}

}
