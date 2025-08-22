package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{
	private List<Pair<String,Integer>> cs = new ArrayList<>();
	
	public SetContClassEventBuilder() {
		super("set_cont_class", "Event");
	}

	@Override
    protected Event create_instance(JSONObject data) {//Preguntar
         int time = data.getInt("time");
         
         for(int i = 0; i < data.getJSONArray("info").length(); i++) {//recorremos la lista de parejas
             String idVehiculo = data.getJSONArray("info").getJSONObject(i).getString("vehicle");

             int contClass = data.getJSONArray("info").getJSONObject(i).getInt("class");
             //creo pareja con los datos de la iteracion
             Pair<String,Integer> pair = new Pair<String, Integer>(idVehiculo,contClass);
             // meto cada pareja en la posicion i
             cs.add(i, pair);
         }

        return new SetContClassEvent(time,cs);
		
    }


}
