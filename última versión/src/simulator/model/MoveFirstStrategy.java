package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> lista = new ArrayList<>();
		// movemos el primero 
		if(!q.isEmpty())
			lista.add(q.getFirst());
		
		
		return lista;
	}

}
