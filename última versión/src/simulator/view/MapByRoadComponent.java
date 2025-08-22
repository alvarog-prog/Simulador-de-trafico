package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	
	//Colores
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _BLACK = Color.BLACK;
	private static final int _JRADIUS = 10;
	
	//rutas contaminacion
	private static final String cont0 = "cont_0.png";
	private static final String cont1 = "cont_1.png";
	private static final String cont2 = "cont_2.png";
	private static final String cont3 = "cont_3.png";
	private static final String cont4 = "cont_4.png";
	private static final String cont5 = "cont_5.png";
	
	//rutas condiciones meteorológicas
	private static final String rain = "rain.png";
	private static final String sun = "sun.png";
	private static final String wind = "wind.png";
	private static final String cloud = "cloud.png";
	private static final String storm = "storm.png";
	
	
	//Coche emoticono
	private Image coche;
	
	//mapa
	private RoadMap _map;
	
	public MapByRoadComponent(Controller ctr) {
		initGUI();
		ctr.addObserver(this);
		setPreferredSize(new Dimension (300, 200));//Indicacion del enunciado
	}
	
	private void initGUI() {
		this.coche = this.loadImage("car.png");
	}

	//misma forma que en la clase MapComponent
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2D.setColor(_BG_COLOR);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g2D);
		}
		
	}


	private void drawMap(Graphics g) {
		//Coordenadas enunciado
		int x1 = 50;
		int x2 = getWidth() - 100;
		int y;
		int i = 0;
		for (Road r : _map.getRoads()) {
			y=(i+1)*50;
			
			//Necesitamos el identificador de la carretera
			g.setColor(_BLACK);
			g.drawString(r.getId(),x1-30,y + _JRADIUS/2);
			
			//necesitamos la linea de cruce a cruce
			g.drawLine(x1, y, x2, y);
			
			
			//Dibujamos los cruces
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS, y - _JRADIUS/2,_JRADIUS,_JRADIUS);
			
			
			// choose a color for the arrow depending on the traffic light of the road
			Color colorCruce = _RED_LIGHT_COLOR;
			int idx = r.getDest().getSemaforoEnVerde();
			if (idx != -1 && r.equals(r.getDest().getListaCarreteras().get(idx))) {
				colorCruce = _GREEN_LIGHT_COLOR;
			}
						
			g.setColor(colorCruce);
			g.fillOval(x2 - _JRADIUS, y - _JRADIUS/2,_JRADIUS,_JRADIUS);
			
			
			//nombres de los cruces(Origen y Destino)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().toString(),x1, y - _JRADIUS);
			g.drawString(r.getDest().toString(),x2,y - _JRADIUS);
			
			g.setColor(_BLACK);
			
			
			//Cargamos imagen de condicion meteorológica
			Image weather = null;
			if(Weather.CLOUDY.equals(r.getWeather())) weather = this.loadImage(cloud);
			else if(Weather.SUNNY.equals(r.getWeather()))  weather = this.loadImage(sun);
			else if(Weather.RAINY.equals(r.getWeather()))  weather = this.loadImage(rain);
			else if(Weather.STORM.equals(r.getWeather()))  weather = this.loadImage(storm);
			else if(Weather.WINDY.equals(r.getWeather()))  weather = this.loadImage(wind);
			
			//Dibujamos imagen obtenida
			g.drawImage(weather,x2 + 15,y-_JRADIUS*2,35,35,this);	
			
			
			//calcular nivel de contaminacion
			int A = r.getTotalCO2();
			int B = r.getContLimit();
			int C = (int) Math.floor(Math.min((double) A/(1.0 + (double) B),1.0) / 0.19);
			
			//Cargamos imagen de contaminacion
			Image contaminacion = null;
			if(C == 0) contaminacion = this.loadImage(cont0);
			else if(C == 1) contaminacion = this.loadImage(cont1);
			else if(C == 2) contaminacion = this.loadImage(cont2);
			else if(C == 3) contaminacion = this.loadImage(cont3);
			else if(C == 4) contaminacion = this.loadImage(cont4);
			else if(C == 5) contaminacion = this.loadImage(cont5);
			else JOptionPane.showConfirmDialog(null,"Error en el nivel de contaminacion","ERROR",JOptionPane.ERROR_MESSAGE);
			
			//dibujamos la imagen obtenida
			g.drawImage(contaminacion, x2 + 55, y-_JRADIUS*2,32,32, this);
			
			if(!r.getVehicles().isEmpty()) {
				for(Vehicle vehiculo : r.getVehicles()) {
					int location = vehiculo.getLocation();
					int length = r.getLength();
					//Coordenada x del coche
					int x = x1 + (int) ((x2 - x1) * ((double) location / (double) length));
					
					//id del coche
					g.setColor(_GREEN_LIGHT_COLOR);
					g.drawString(vehiculo.getId(),x, y - _JRADIUS - 5);
					
					//Volvemos al color negro
					g.setColor(_BLACK);
					
					//dibujo del coche
					g.drawImage(this.coche, x, y - _JRADIUS - 3,16,16, this);		
				}
			}
			//sumamos el índice para que cambie y
			i++;
		}
		
	}


	//igual que en MapComponent
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}
	
	
	//Al igual que en la clase MapComponent
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
