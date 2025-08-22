package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{

	/*
		viajan a través de carreteras y contaminan emitiendo CO2. 
		Cada vehı́culo tendrá un itinerario, que consistirá en la secuencia 
		de cruces por los que tiene que pasar.
	*/
	
	
	private int MaxSpeed;
	private int Speed = 0;
	private Road carretera;//carretera sobre la que el coche esta circulando 
	private int Location = 0; //distancia desde el comienzo de la carretera
	private int ContClass;// Grado de contaminación entre 0 y 10
	private int ContTotal;
	private int DistTotalRec;//distnacia total recorrida por el vehiculo 
	private List<Junction> itinerary; //seccion de cruces por los que tiene que pasar 
	private VehicleStatus estadoVehic = VehicleStatus.PENDING;
	private int indice = 0;
	private Junction srcJunction = null;
	private Junction desJunction = null;
	
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if(maxSpeed <= 0) {
        	throw new IllegalArgumentException("max speed no es positiva");
        }
        if(contClass < 0 || contClass > 10) {
        	throw new IllegalArgumentException("cont no esta entre 0 y 10");
        }
        if(itinerary.size() < 2) {
        	throw new IllegalArgumentException("itinerary es menor que dos");
        }
        this.MaxSpeed = maxSpeed;
        this.ContClass = contClass;
       this. itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));

	}
	 
	
	 public void setContClass(int contClass) {
		 if(contClass > 10 || contClass < 0) {
			 throw new IllegalArgumentException("ex");
		 }
		ContClass = contClass;
	}

	public void setVelAct(int velAct) {
		this.Speed= velAct;
	}

	public int getSpeed() {
		return Speed;
	}
	public int getMaxSpeed() {
		return MaxSpeed;
	}
	
	public Road getRoad() {
		return carretera;
	}
	public int getLocation() {
		return Location;
	}
	public int getContClass() {
		return ContClass;
	}
	public int getTotalCO2() {
		return ContTotal;
	}
	public int getDistTotalRec() {
		return DistTotalRec;
	}

	public List<Junction> getItinerary() {
		return Collections.unmodifiableList(new ArrayList<>(itinerary));
	}

	public VehicleStatus getStatus() {
		return estadoVehic;
	}
	
	 
	 
	void setSpeed(int s) {
	    if (this.estadoVehic == VehicleStatus.TRAVELING) { // Solo puede cambiarse si está viajando
	        if (s < 0) {
	            throw new IllegalArgumentException("La velocidad no puede ser negativa");
	        }
	        this.Speed = Math.min(s, this.MaxSpeed); // cogera el minimo entre el que le pasa y max speed, para que no supere a max speed
	    }
	}
	
	/*
	public void moveToNextRoad() {
		  switch (this.estadoVehic) { // en el caso de que sea pending o waiting
	        case PENDING, WAITING -> {
	            if (this.carretera != null) this.carretera.exit(this); // si la carretera es null

	            if (this.indice == this.itinerary.size() - 1) {
	              hallegado(); // paro el vehiculo 
	            } else {
	               noHaLlegado(); // movemos el vehiculo de carretera
	            }	
	        }
	        default -> throw new IllegalArgumentException("No se permite ese estado");
	    }
	 }
	 */
	private void hallegado() {
		this.carretera = null;
		this.Location = 0;
		this.setEstadoVehic(VehicleStatus.ARRIVED);
		
	}
	
	
	
	public void moveToNextRoad() {//mueve el vehı́culo a la siguiente carretera
		if(this.estadoVehic != VehicleStatus.PENDING && this.estadoVehic != VehicleStatus.WAITING) {	
			throw new IllegalArgumentException("Estado erroneo para moverse a otra carretera.");
		}	
	
		if(carretera != null) {//si estamos en alguna carretera
			carretera.exit(this);//la abandonamos
		}

		if(this.indice == this.itinerary.size() - 1) {//se verifica si el vehiculo ha llegado al último cruce de su itinerario
			carretera = null;
			this.estadoVehic = VehicleStatus.ARRIVED;
			this.Location = 0;
		}
		else {//mover a la siguiente carretera del itinerario(el vehiculo no esta en el ultimo cruce del itinerario)
			srcJunction = this.itinerary.get(this.indice);//cruce actual
			desJunction = this.itinerary.get(this.indice + 1);//siguiente cruce
			this.Location = 0;
			Road sigCarretera = srcJunction.roadTo(desJunction);//obtengo la carretera que conecta el cruce actual con el siguiente
			sigCarretera.enter(this);//entro a esa carretera
			carretera = sigCarretera;
			this.estadoVehic = VehicleStatus.TRAVELING;//el vehiculo esta en movimiento
		}
	}
	
	
	
	private void noHaLlegado() {

		this.Location = 0;	
		Road road = itinerary.get(this.indice).roadTo(itinerary.get(this.indice + 1));
		road.enter(this);
		this.carretera = road;
		this.setEstadoVehic(VehicleStatus.TRAVELING);
	}

	 public void setEstadoVehic(VehicleStatus estadoVehic) {
		this.estadoVehic = estadoVehic;
	}

	void setContaminationClass(int c) {
		 if(0 <= c && c <= 10) {
			 this.ContClass = c;
		 }
		 else {
			 throw new IllegalArgumentException("c No esta entre 0 y 10");
		 }
	 }
	 
	 @Override
	 void advance(int time) {
		
		 if( this.estadoVehic == VehicleStatus.TRAVELING) {
			  // guardo la vieja
			 int localizacionVieja = this.Location;
			 
			 //la nueva localizacion sera igual al minimo entre la localizacion actual + la velocidad actual y la longitud de la carretera
			 //Math.min selecciona el valor minimo
			 this.Location = Math.min(this.Location + this.Speed, carretera.getLength());
			 // si es mayor que la longitud de la carretera
			 if(this.Location > carretera.getLength()) {
				 this.Location = carretera.getLength();
			 }
			 // calculo la contaminacion con la formula
			 int contaminacion = this.ContClass * (this.Location - localizacionVieja);
			 // actualizo la distacnia recorrida
			  this.DistTotalRec += (this.Location - localizacionVieja);
			  // agrego la contaminacion a la contaminacion del vebiculo y la contaminacion de la carretera
			 this.ContTotal = this.ContTotal + contaminacion;
			 carretera.addContamination(contaminacion);
			 // si el vehiculo ha llegado al final de la carretera se frena y se le pone en la cola del cruce destino y se actualiza su estado
			 
			 if(this.Location >= carretera.getLength()) {
				 carretera.getDest().enter(this);//el vehiculo entra a la cola del cruce correspondiente
					this.setEstadoVehic(VehicleStatus.WAITING);
					this.Speed = 0;
					this.indice++; 
			 }
		 }else { // si no esta traveling se le pone la velocidad a 0
			 this.Speed = 0;
		 }
			
	 }

	 // funcion auxiliar
	public boolean entrarCarretera() {
		boolean entra = false;
		 if(this.Location == 0 && this.Speed == 0) {
			 entra = true;
		 }
		return entra;
	}
	
	@Override
	public JSONObject report() {//muestra
		JSONObject json = new JSONObject();
		json.put("id", this._id);
		json.put("speed", this.Speed);
		json.put("distance", DistTotalRec);
		json.put("co2", ContTotal);
		json.put("class", this.ContClass);
		json.put("status", this.estadoVehic.toString());
		
		if (estadoVehic != VehicleStatus.PENDING && estadoVehic != VehicleStatus.ARRIVED){	
			json.put("road", carretera.getId());
			json.put("location", Location);
			
		}	
		return json;
	}
	
	
	public Junction getSrcJunction() {
		return srcJunction;
	}


	public Junction getDesJunction() {
		return desJunction;
	}


}
