package simulator.model;

public class NewJunctionEvent extends Event{
	private String id;
	private LightSwitchingStrategy light;
	private DequeuingStrategy deque;
	private int xCoor;
	private int yCoor;
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(time);
		  this.id = id;
		  this.light = lsStrategy;
		  this.deque= dqStrategy;
		  this.xCoor = xCoor;
		  this.yCoor = yCoor;
		}
	@Override
	void execute(RoadMap map) {
		Junction junction = new Junction(id, light, deque, xCoor, yCoor);//crea el cruce
		map.addJunction(junction);//lo añadunos al mapa de carreteras
	}
	@Override
	public String toString() {
		return "New Junction '"+ this.id +"'";//esto se mostrara al añadir en la cola
	}

}
