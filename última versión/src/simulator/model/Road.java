package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	/*
	 * viajan los vehı́culos,
	 *  y que controlan la velocidad de los mismos para reducir la contaminación, etc.
	 */
	
	
	private Junction cruceOrigen;
	private Junction cruceDestino;
	private int Long = 0;//En metros
	private int MaxSpeed = 0;//Velocidad máxima permitida en esta carretera
	private int LimActVel = 0;
	private int ContLimit = 0;
	protected int contTotal = 0;
	private Weather condAmbientales;
	private List<Vehicle> Vehicles = new ArrayList<>();//lista de vehiculos que estan circulando por la carretera
	
	
	Road(String id, Junction srcJun, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {		
		super(id);
        if(maxSpeed <= 0) {
        	throw new IllegalArgumentException("max speed no es positiva");
        }
        if(contLimit < 0) {
        	throw new IllegalArgumentException("contLimit no es positiva");
        }
        if(length <= 0) {
        	throw new IllegalArgumentException("length no es positiva");
        }
        if(srcJun == null) {
        	throw new IllegalArgumentException("El cruce origen es nulo");
        }
		if(destJunc == null) {
			throw new IllegalArgumentException("El cruce destino es nulo");
		}
		if(weather == null) {
			throw new IllegalArgumentException("El tiempo no puede ser nulo");
		}
		this.cruceOrigen = srcJun;
		this.cruceDestino = destJunc;
		this.MaxSpeed = maxSpeed;
		this.ContLimit = contLimit;
		this.Long = length;
		this.setWeather(weather);
		this.cruceDestino.addIncommingRoad(this);
		this.cruceOrigen.addOutGoingRoad(this);
		this.LimActVel = this.MaxSpeed;
	}
	
	public abstract void reduceTotalContamination();
	public abstract void updateSpeedLimit();
	public abstract int calculateVehicleSpeed(Vehicle v);
	
	public int getContLimit() {
		return ContLimit;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(this.Vehicles);
	}

	public Junction getSrc() {
		return cruceOrigen;
	}

	public void setCruceOrigen(Junction cruceOrigen) {
		this.cruceOrigen = cruceOrigen;
	}

	public Junction getDest() {
		return cruceDestino;
	}

	public void setCruceDestino(Junction cruceDestino) {
		this.cruceDestino = cruceDestino;
	}

	public int getTotalCO2() {
		return contTotal;
	}
	
	public void setContTotal(int contTotal) {
		this.contTotal = contTotal;
	}

	public Weather getWeather() {
		return condAmbientales;
	}

	public void setCondAmbientales(Weather condAmbientales) {
		this.condAmbientales = condAmbientales;
	}

	

	public int intTiempoCity() {
		int tiempo = 0;
		if(this.condAmbientales == Weather.WINDY || this.condAmbientales == Weather.STORM) {
			tiempo = 10;
		}
		else {
			tiempo = 2;
		}
		return tiempo;
	}
	

	public int getLength() {
		return Long;
	}


	public void setLong(int l) {
		Long = l;
	}
	public void enter(Vehicle v) {
		if(v.entrarCarretera()) {	
			this.Vehicles.add(v);
		}else {
			throw new IllegalArgumentException("el vehiculo" + v._id + "no puede entrar en la carretera " + this._id);
		}
		
	}
	public void exit(Vehicle v) {//abandonar la carretera
		this.Vehicles.remove(v);
	}
	public void setWeather(Weather w) {
		if(w != null) {
			this.condAmbientales = w;
		}else {
			//lanza excepcion
			throw new IllegalArgumentException("La condicion metereologica es nula");
		}
	}
	public void addContamination(int c) {
		if(c >= 0) {
			this.contTotal += c;
		}else {
			//lanza excepcion
			throw new IllegalArgumentException("la contaminacion es negativa");
		}
	}
	
	
	public int getMaxSpeed() {
		return this.MaxSpeed;
	}

	public void setVelMax(int velMax) {
		MaxSpeed = velMax;
	}

	public int getSpeedLimit() {
		return this.LimActVel;
	}

	public void setLimActVel(int limActVel) {
		LimActVel = limActVel;
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
		  this.reduceTotalContamination();
	        this.updateSpeedLimit();
	        for(Vehicle v : Vehicles) {
	            asignaVelocidad(v);
	            v.advance(time);
	        }
	        //Vehicles.sort(Comparator.comparing(Vehicle::getLocation).reversed()); 
	        List<Vehicle> listaVehiclesNueva = new ArrayList<>(); //iremos ordenando los vehiculos en esta nueva lista
	        int aux = 0;
	        while(this.Vehicles.size() > 0) {
	            int ind = 0;
	            for(int i = 0; i < this.Vehicles.size(); i++) {
	                if(aux < this.Vehicles.get(i).getLocation()) {
	                    aux = this.Vehicles.get(i).getLocation();
	                    ind = i;
	                }
	            }
	            listaVehiclesNueva.add(this.Vehicles.get(ind));
	            this.Vehicles.remove(ind);
	        }
	        if(!listaVehiclesNueva.isEmpty())
	            this.Vehicles = listaVehiclesNueva;//los volvemos a meter en la lista de vehiculos
	}
	
	private void asignaVelocidad(Vehicle v) {
        int velocidad = this.calculateVehicleSpeed(v);
        // si la velocidad supera a la maxima la ponemos como la maxima
        if(velocidad >= v.getMaxSpeed()) {
        	velocidad = v.getMaxSpeed();
        }
        
        v.setVelAct(velocidad);
    }
	
	
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub

		JSONObject json = new JSONObject();	
		json.put("id", this._id);
		json.put("speedlimit", this.LimActVel);
		json.put("weather", this.condAmbientales.toString());
		json.put("co2", contTotal);
		JSONArray jArray = new JSONArray();
		json.put("vehicles", jArray);
		for (Vehicle vehicle : Vehicles)
		{
			jArray.put(vehicle.getId());
		}
		return json;
	}
	public void sumaContaminacion(int cont) {
		this.contTotal += cont;
	}


}
