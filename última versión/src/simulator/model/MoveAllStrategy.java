package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {	
		// movemos toda la lista q
		List<Vehicle> lista = new ArrayList<>();
		for(Vehicle v : q) {
			lista.add(v);
		}
		return lista;
	}

}
