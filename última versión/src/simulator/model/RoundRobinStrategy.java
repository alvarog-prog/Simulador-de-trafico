package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{
	private int timeSlot;//el número de "ticks" consecutivos durante los cuales la carretera puede tener el semáforo en verde
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	//currGreen: el ı́ndice (en la lista roads) de la carretera que tiene el semáforo en verde
	/*
	 * El valor -1 en currGreen se utiliza para indicar que todos los semáforos están en rojo.
	 */
	
	//lastSwitchingTime: paso de la simulación en el cual el semáforo para la carretera currGreen se cambió de rojo a verde
	//currTime: paso actual de la simulacion
	
	/*
	 * El método devuelve el ı́ndice de la carretera (en la lista roads) que tiene que poner su semáforo a verde
	 *  si es el misma que currGreen, entonces, el cruce no considerará el cambio. 
	 */
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
			
		if(roads.isEmpty())
            return -1;
        if(currGreen == -1)//el sem. de todas las carreteras entrantes en rojo, pone en verde el primer semaforo de la lista de roads
            return 0;
        if(currTime - lastSwitchingTime < this.timeSlot)
            return currGreen;//lo deja tal cual estan
        
        return (currGreen+1)%roads.size();
        
	}

}
