package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import simulator.control.Controller;

public class MainWindow extends JFrame {

		private Controller _ctrl;
		
		private JTable tabla;
		private DefaultTableModel tableModel;
		
		public MainWindow(Controller ctrl) {
			super("Traffic Simulator");
			_ctrl = ctrl;
			initGUI();
		}

		private void initGUI() {
			JPanel mainPanel = new JPanel(new BorderLayout());//panel principal
			this.setContentPane(mainPanel);
						
			//añadimos al panel principal el panel de control(parte superior, con los botones...) y la statusBar(parte abajo, time...)
			mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
			mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
			
			
			//el siguiente panel, este irá dentro del panel principal, debajo del panel de control(dentro de este iran todas las tablas, mapas...)
			JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
			mainPanel.add(viewsPanel, BorderLayout.CENTER);

			//panel para colocar las tablas(dentro del ViewPanel, que lo separamos en dos, en el panel de mapas y de tablas)
			JPanel tablesPanel = new JPanel();
			tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
			viewsPanel.add(tablesPanel);

			//panel para colocar los dos mapas(ira dentro del viewPanel)
			JPanel mapsPanel = new JPanel();
			mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
			viewsPanel.add(mapsPanel);

			// tablas
			//panel donde ira la tabla de eventos, dentro de tablesPanel, que a su vez está dentro de viewPanel que a su vez esta en mainPanel
			JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
			eventsView.setPreferredSize(new Dimension(500, 200));//asignamos tamaño
			tablesPanel.add(eventsView);
			
			//panel donde ira la tabla de vehiculos, dentro de tablesPanel, que a su vez está dentro de viewPanel que a su vez esta en mainPanel
			JPanel panelVehiculos =  createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
			panelVehiculos.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(panelVehiculos);
			
			//panel donde ira la tabla de carreteras, dentro de tablesPanel, que a su vez está dentro de viewPanel que a su vez esta en mainPanel
			JPanel panelCarreteras =  createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
			panelCarreteras.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(panelCarreteras);
			
			//panel donde ira la tabla de cruces, dentro de tablesPanel, que a su vez está dentro de viewPanel que a su vez esta en mainPanel
			JPanel panelCruces = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
			panelCruces.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(panelCruces);
			
			// TODO add other tables
			// ...

			/*	
			JScrollPane scrollPane = new JScrollPane(tabla);
			scrollPane.setPreferredSize(new Dimension(400, 100));
			this.panelPrincipal.add(scrollPane);
			 */
			
			// mapas
			//mapView, ira dentro del panel ya creado para los mapas, que a su vez esta dentro de viewPanel y a su vez dentro de mainPanel
			JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
			mapView.setPreferredSize(new Dimension(500, 400));
			mapsPanel.add(mapView);
			
			//mapByRoadView, ira dentro del panel ya creado para los mapas, que a su vez esta dentro de viewPanel y a su vez dentro de mainPanel
			JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
			mapByRoadView.setPreferredSize(new Dimension(500, 400));
			mapsPanel.add(mapByRoadView);
			
			// TODO add a map for MapByRoadComponent
			// ...
			
			
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);//al darle a la x para cerar cierra, podría no dejarte cerrar (DO_NOTHING_ON_CLOSE)
			this.pack();//ajustar todo
			this.setVisible(true);
		}

		//creacion de tablas
		private JPanel createViewPanel(JComponent c, String title) {
			JPanel p = new JPanel( new BorderLayout() );
	        
			if(title.equals("Events")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Events", TitledBorder.LEFT, TitledBorder.TOP));
			}else if(title.equals("Vehicles")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Vehicles",TitledBorder.LEFT,TitledBorder.TOP));
			}else if(title.equals("Roads")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Roads",TitledBorder.LEFT,TitledBorder.TOP));
			}else if(title.equals("Junctions")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Junctions",TitledBorder.LEFT,TitledBorder.TOP));
			}else if(title.equals("Map")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Map",TitledBorder.LEFT,TitledBorder.TOP));
			}else if(title.equals("Map By Road")) {
				p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),"Map By Road",TitledBorder.LEFT,TitledBorder.TOP));
			}
			
			p.add(new JScrollPane(c));
			return p;
		}
	}
