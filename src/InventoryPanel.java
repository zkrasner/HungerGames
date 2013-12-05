import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class InventoryPanel extends JPanel {

	public static final int INVENT_WIDTH = 220;
	public static final int INVENT_HEIGHT = 600;
	public static final int INTERVAL = 35; 
	private final Player player;
	public static Item selected = null;
	private final EquipmentPanel ep;

	public InventoryPanel(JLabel status, final Player player, final EquipmentPanel ep){

		this.player = player;
		this.ep = ep;
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called 
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);
		
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e){
				int me = e.getButton();
				if (me == MouseEvent.BUTTON1 || me == MouseEvent.BUTTON3 || 
						(SwingUtilities.isLeftMouseButton(e) && e.isControlDown())) {
					int row = ((e.getY()-10)/25);
					int col = ((e.getX()-10)/25);
					if (row < 16 && col < 8) selected = player.itemDisplay[row][col];
				}
			}
			
			public void mouseReleased(MouseEvent e){
				if (e.getButton()==MouseEvent.BUTTON1||e.getButton()==MouseEvent.BUTTON3||e.isControlDown()) {
					int row = ((e.getY()-10)/25);
					int col = ((e.getX()-10)/25);
					//Check if something is selected from the inventory
					if (selected != null) {
						//if released within the inventory, move the item to that spot
						if (row < 16 && row >= 0 && col < 8 && col >=0) {
							if (player.itemDisplay[row][col] == null) {
								selected.updateInventLoc(22+25*col-selected.xSize/2,
										22+25*row-selected.ySize/2);
								player.removeItem(selected);
								player.itemDisplay[row][col] = selected;
							} 
							selected = null;
						//if the mouse is released somewhere in the Equipment Panel
						} else if (ep.addEquipment(selected,
								e.getX()-INVENT_WIDTH-GameCourt.COURT_WIDTH,e.getY())) {
							player.removeItem(selected);
							if (selected instanceof Ammo && (e.getButton() == MouseEvent.BUTTON3 || e.isControlDown())) {
								for (int i = 0; i < player.itemDisplay.length; i++) {
									for (int j = 0; j < player.itemDisplay[0].length; j++) {
										if (selected instanceof Rock && player.itemDisplay[i][j] instanceof Rock) {
											ep.addEquipment(selected,
													e.getX()-INVENT_WIDTH-GameCourt.COURT_WIDTH,e.getY());
											player.itemDisplay[i][j] = null;
										}
									}
								}
							}
							selected = null;
						} else {
							selected = null;
						}
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	public void start() {

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	void tick(){
		if (GameCourt.playing) {

			repaint();
		} 
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		for (int i = 0; i <= 2*INVENT_HEIGHT/3; i += 25){
			//Vertical
			g.drawLine(i+10, 10, i+10, 2*INVENT_HEIGHT/3 + 10);
			//Horizontal
			g.drawLine(10, i+10, INVENT_WIDTH-10, i+10);
		}
		//Health Bar
		g.drawString("Health:" + player.getHealth(),10, 2*INVENT_HEIGHT/3 + 30);
		int startY = 2*INVENT_HEIGHT/3+40;
		g.setColor(Color.black);
		g.fillRect(10, startY, player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.red);
		g.fillRect(10, startY, player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.black);
		g.drawRect(10, startY, INVENT_WIDTH-20, (2/3)*INVENT_HEIGHT + 30);
		
		//Stamina Bar
		g.drawString("Stamina:" + player.getStamina(), 10, 2*INVENT_HEIGHT/3 + 90);
		startY += 60;
		g.setColor(Color.black);
		g.fillRect(10, startY, player.getStamina()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.blue);
		g.fillRect(10, startY,player.getStamina()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.black);
		g.drawRect(10, startY, INVENT_WIDTH-20, (2/3)*INVENT_HEIGHT + 30);
		
		for (int row = 0; row < player.itemDisplay.length; row++) {
			for (int col = 0; col < player.itemDisplay[0].length; col++) {
				Item i = player.itemDisplay[row][col];
				if (i != null) {
					i.draw(g);
				}
			}
		}
	}
	
	

	public Dimension getPreferredSize(){
		return new Dimension(INVENT_WIDTH,INVENT_HEIGHT);
	}
}
