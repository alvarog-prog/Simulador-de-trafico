package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event>{
	
	public NewCityRoadEventBuilder() {
		super("new_city_road", "Event");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event create_instance(JSONObject data) {
		//vamos sacando todos los datos que tiene el JSON, en este caso el newCityRoad que queremos crear
		int time = data.getInt("time");
		String id = data.getString("id");
		String j1 = data.getString("src");
		String j2 = data.getString("dest");
		int length = data.getInt("length");
		int co2 = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		String temporal= data.getString("weather");
		
		Weather w = Weather.valueOf(temporal.toUpperCase());
		

		return new NewCityRoadEvent(time, id, j1, j2, length, co2, maxspeed, w);
	}

}
