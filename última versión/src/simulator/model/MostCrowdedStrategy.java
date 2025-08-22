package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
    private int timeSlot;

    public MostCrowdedStrategy(int timeSlot) {
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
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
    	int ind = 0;
        if(roads.isEmpty())
            return -1;
        if(currGreen == -1){//todos los semáforos están en rojo
            //hacemos búsqueda de la cola más larga
            int aux = 0;
            for(int i = 0; i < qs.size(); i++) {
                if(aux < qs.get(i).size()) {
                    aux = qs.get(i).size();
                    ind = i;
                }
            }
            return ind;//pone en verde el sem de la carretera entrante con la cola mas larga
        }
        
        if(currTime - lastSwitchingTime <this.timeSlot)
            return currGreen;//lo deja como están
        
        int aux2 = 0;
        for(int i = (currGreen + 1) % qs.size(); i > 0; i--) {
            if(aux2 < qs.get(i).size()) {
                aux2 = qs.get(i).size();
                ind = i;
            }
        }
            return ind;//pone a verde el semáforo de la carretera entrante con la cola más larga, realizando una búsqueda circular
    }
}
