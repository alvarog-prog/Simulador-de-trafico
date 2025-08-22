package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss","LightSwitchingStrategy");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		if(!data.has("timeslot")) {
			return new MostCrowdedStrategy(1);
		}
		int time = data.getInt("timeslot");	//Cogemos la variable timeslot que viene dentro del JSON
		return  new MostCrowdedStrategy(time);
	}

}
