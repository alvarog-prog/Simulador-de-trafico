package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss", "LightSwitchingStrategy");
		// TODO Auto-generated constructor stub

	}

	//por parametro me entrara la estructura JSON
	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		//si no tiene time se le pasa un 1
		if(!data.has("timeslot")) {
			return  new RoundRobinStrategy(1);
		}	
		return new RoundRobinStrategy(data.getInt("timeslot"));
	}

}
