package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String,Weather>> lista;//lista de parejas


    public SetWeatherEvent(int time, List<Pair<String,Weather>> lista) {
          super(time);
          if(lista.equals(null)) 
        	  throw new IllegalArgumentException("Exception");
          this.lista = lista;
    }

    @Override
    void execute(RoadMap map) {
    	Road carretera = null;
    	boolean encontrado = false;
    	for(Pair<String, Weather> pareja : lista) {
    		// saco la carretera con el id de la lista de parejas que sacamos del create instance
    			carretera = map.getRoad(pareja.getFirst());
    			if(carretera != null) {
    				// si existe le seteo la weather
    				carretera.setCondAmbientales(pareja.getSecond());
    				encontrado = true;
    			}
    			
    	
    	}
    	if (!encontrado) {
    		 throw new IllegalArgumentException("Exception");
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


