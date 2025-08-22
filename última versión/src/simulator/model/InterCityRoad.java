package simulator.model;

public class InterCityRoad extends Road{
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination() {
		int tc = this.getTotalCO2();
		int valor = intTiempoInter(); //devuelve en funcion de la condicion metereologica
		int x = (((100-valor)*tc)/100);//formula proporcionada
		this.setContTotal(x);

	}

	@Override
	public void updateSpeedLimit() {
		if(this.supera()) { // si contaminacion total es mayor a contlimit
			int velocidadMax = this.getMaxSpeed()/2;//Si la contaminación total es mayor al límite, 
            //el nuevo limite de velocidad serña la mitad de la velocidad máxima
			this.setLimActVel(velocidadMax);
		}else {
			//en otro caso el Límite será directamente la velocidad máxima.
			this.setLimActVel(this.getMaxSpeed());
		}
	}
	// funcion auxiliar
	private boolean supera() {
			// TODO Auto-generated method stub
			boolean supera = false;
			if(this.contTotal > this.getContLimit()) {
				supera = true;
			
			}
			return supera;
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		int velocidad = 0;
		int max = this.getSpeedLimit();
		if(this.getWeather().equals(Weather.STORM)) { // si el tiempo es Storm aplico la formula proporcionada			
			velocidad = (max*8)/10;		
		}else { // sino la igualo al limite de la velocidad
			velocidad = max;
		}
		return velocidad;
	}
	private int intTiempoInter() {
		  int x = 0;
	        if(this.getWeather().equals(Weather.SUNNY)) {
	            x = 2;
	        }else if(this.getWeather().equals(Weather.CLOUDY)) {
	            x = 3;
	        }else if(this.getWeather().equals(Weather.RAINY)) {
	            x = 10;
	        }else if(this.getWeather().equals(Weather.WINDY)) {
	            x = 15;
	        }else if(this.getWeather().equals(Weather.STORM)) {
	            x = 20;
	        }
	        return x;
	}
	

	

	
}
