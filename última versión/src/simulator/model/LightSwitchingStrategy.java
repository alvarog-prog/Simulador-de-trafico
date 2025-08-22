package simulator.model;

import java.util.List;

public interface LightSwitchingStrategy {
	int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime);
	/*
	 * Estas estrategias nos servirán para decidir cuál de las 
	 * carreteras entrantes al cruce pondrá su semáforo en verde	 * 
	 */
	
}
