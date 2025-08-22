package simulator.model;

public abstract class Event implements Comparable<Event> {

  private static long _counter = 0;

  protected int _time;//tiempo en el que el evento se tiene que ejecutar
  protected long _time_stamp;//para ver que evento se añadió antes

  Event(int time) {
    if ( time < 1 )
      throw new IllegalArgumentException("Invalid time: "+time);
    else {
      _time = time;
      _time_stamp = _counter++;
    }
  }
  public int getTime() {
    return _time;
  }
  
  @Override
  public int compareTo(Event o) {
	  if (this._time < o._time)
	        return -1;
	    
	    if (this._time > o._time)
	        return 1;
	    
	    if (this._time_stamp < o._time_stamp)
	        return -1;
	    
	    if (this._time_stamp > o._time_stamp)
	        return 1;
	    
	    return 0; 
  } 
  
  abstract void execute(RoadMap map);//ejecutar el evento
  public abstract String toString();
   
}