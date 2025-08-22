package simulator.model;

public abstract class NewRoadEvent extends Event{
	protected String id;
	protected String srcJun;
	protected String destJunc;
	protected int length;
	protected int co2limit;
	protected int maxSpeed;
	protected Weather weather;
	public NewRoadEvent(int time, String id,String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		  super(time);
		  this.id = id;
		  this.srcJun = srcJun;
		  this.destJunc = destJunc;
		  this.length = length;
		  this.co2limit =co2Limit;
		  this.maxSpeed =maxSpeed;
		  this.weather = weather;
	}


	@Override
	void execute(RoadMap map) { // clase padre a si que entrara en las hijas
		
	}


	@Override
	abstract public String toString();

}
