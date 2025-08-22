package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event{
	private  List<Pair<String, Integer>> lista = new ArrayList<>();
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs)  {
		  super(time);
		  
		  if(cs.equals(null)) {
			  throw new IllegalArgumentException("exception");
		  }
		  
		  this.lista = cs;
		  
	}
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Vehicle v = null;
		boolean encontrado = false;
		for( Pair<String,Integer> w : lista) {
			 v = map.getVehicle(w.getFirst());
			 if(v != null) {
				 v.setContaminationClass(w.getSecond());
				 encontrado = true;
			 }
		}
		if(!encontrado) {
			throw new IllegalArgumentException("excepcion");
		}
	}
	  @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder("Change Weather: [");
	        for (int i = 0; i < this.lista.size(); i++) {
	            sb.append("(")
	              .append(this.lista.get(i).getFirst())
	              .append(", ")
	              .append(this.lista.get(i).getSecond())
	              .append(")");
	            if (i < this.lista.size() - 1) {
	                sb.append(", ");
	            }
	        }
	        sb.append("]");
	        return sb.toString();
	    }
	
}
