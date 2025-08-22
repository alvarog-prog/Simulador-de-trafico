package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{
    private List<Pair<String,Weather>> ws = new ArrayList<>();

	public SetWeatherEventBuilder() {
		super("set_weather", "Event");
		// TODO Auto-generated constructor stub
	}

	@Override
    protected Event create_instance(JSONObject data) {
		//vamos sacando todos los datos que tiene el JSON, en este caso el newJunction que queremos crear
        int time = data.getInt("time");
        for(int i = 0; i < data.getJSONArray("info").length(); i++) {
        	// para cada vuelta del array guardamos la pareja de id de carretera y el weather
            String id = data.getJSONArray("info").getJSONObject(i).getString("road");
            String temporal = data.getJSONArray("info").getJSONObject(i).getString("weather");

            //value.of nos permite transformar un string en su correspondiente enum(en este caso de tipo weather)
            Weather weather = Weather.valueOf(temporal.toUpperCase());
            Pair<String,Weather> pair = new Pair<String, Weather>(id,weather);
            //insertamos en la posicion i
            ws.add(i, pair);
        }
        return new SetWeatherEvent(time,ws);
    }

}
