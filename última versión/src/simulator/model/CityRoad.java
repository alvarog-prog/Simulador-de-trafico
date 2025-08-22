package simulator.model;

public class CityRoad extends Road{

	

	CityRoad(String id, Junction srcJun, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJun, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
    public void reduceTotalContamination() {
        int x = 0;
        if(this.getWeather() == Weather.WINDY || 
                this.getWeather() == Weather.STORM) {
            x = 10;
        }else {
            x = 2;
        }
        this.contTotal = this.contTotal - x;
        // si es negativa se setea a 0
        if(this.contTotal < 0) {
            this.setContTotal(0);
        }
    }

	

	@Override
	public void updateSpeedLimit() {
		this.setLimActVel(this.getMaxSpeed());
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {	
		int f = v.getContClass();
		int s = this.getSpeedLimit();
		return ((11-f)*s)/11;
		
	}

}
