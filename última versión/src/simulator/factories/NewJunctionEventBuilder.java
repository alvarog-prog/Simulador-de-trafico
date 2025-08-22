package simulator.factories;

import java.util.List;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;
import simulator.model.NewJunctionEvent;
import simulator.model.RoundRobinStrategy;

public class NewJunctionEventBuilder extends Builder<Event>{

	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	public NewJunctionEventBuilder() {
		super("new_junction", "Event");
		// TODO Auto-generated constructor stub
	}
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "Event");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}
	
	@Override
	protected Event create_instance(JSONObject data) {
		//vamos sacando todos los datos que tiene el JSON, en este caso el newJunction que queremos crear
		
		int time = data.getInt("time");
		String id = data.getString("id");
		//estrategias con factorias para poder llamar al create instance otra vez
		LightSwitchingStrategy l = this.lssFactory.create_instance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dq = this.dqsFactory.create_instance(data.getJSONObject("dq_strategy"));
		
		//coordenadas
		int coorX = data.getJSONArray("coor").getInt(0);
		int coorY = data.getJSONArray("coor").getInt(1);

		return  new NewJunctionEvent(time,id,l,dq,coorX,coorY);
		
	}

}
