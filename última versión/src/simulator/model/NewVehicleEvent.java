package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
    private int time;
    private String id;
    private int maxSpeed;
    private int contClass;
    private List<String> itinerary;
    private List<Junction> junctions = new ArrayList<>();


    public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
          super(time);
          if(time < 0)
              throw new IllegalArgumentException("time es negativo");
          if(maxSpeed < 0)
              throw new IllegalArgumentException("maxSpeed no es positiva");
          if(0 > contClass || 10 < contClass)
              throw new IllegalArgumentException("ContClass no esta entre 0 y 10");

          this.id = id;
          this.maxSpeed = maxSpeed;
          this.contClass = contClass;
          this.itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
    	// metemos en la lista de junctions que se le pasa por parametro a vehicle todos los juncions que se muestran sus ids en el itinerary
    	for(String string : itinerary) {
    		junctions.add(map.getJunction(string));
    	}
         Vehicle v1 = new Vehicle(this.id,this.maxSpeed,this.contClass,this.junctions);
         map.addVehicle(v1);
         v1.moveToNextRoad();
    }

	@Override
	public String toString() {
		return "New Vehicle '"+this.id+"'";
	}

}
