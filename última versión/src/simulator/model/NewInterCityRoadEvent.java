package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		  super(time, id, srcJun, destJunc, length, co2Limit,maxSpeed , weather);
		 
		}

	@Override
    void execute(RoadMap map) {
        InterCityRoad carreteraNueva = new InterCityRoad(this.id,map.getJunction(this.srcJun),map.getJunction(this.destJunc),this.maxSpeed,this.co2limit,this.length,this.weather);
        map.addRoad(carreteraNueva);
    }

	@Override
	public String toString() {
		return "New InterCityRoad '"+this.id+"'";
	}

}
