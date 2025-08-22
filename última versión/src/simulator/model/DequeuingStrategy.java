package simulator.model;

import java.util.List;

public interface DequeuingStrategy {
	List<Vehicle> dequeue(List<Vehicle> q);
	
	/*
	 *  eliminan vehı́culos de las carreteras entrantes cuyo semáforo esté a verde
	 */
	//Implementaremos dos estrategias para sacar elementos de la cola
}
