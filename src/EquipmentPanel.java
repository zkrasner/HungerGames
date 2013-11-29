package hungerGames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class InventoryPanel extends JPanel {

	public static final int INVENT_WIDTH = 200;
	public static final int INVENT_HEIGHT = 600;
	public static final int INTERVAL = 35; 
	private Field field;
	
	public InventoryPanel(JLabel status, Field field){
		
		this.field = field;
		
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
		//Equipement visuals
		g.drawString("Equipment", INVENT_WIDTH/2 -30, 20);
		g.drawRect(INVENT_WIDTH/2 - 25, 40, 50, 50);
		g.drawRect(INVENT_WIDTH/2 - 40, 100, 80, 80);
		g.drawRect(INVENT_WIDTH/2 - 90, 100, 40, 100);
		g.drawRect(INVENT_WIDTH/2 + 50, 100, 40, 100);
		g.drawRect(INVENT_WIDTH/2 - 40, 190, 80, 100);
		g.drawRect(INVENT_WIDTH/2 - 40, 300, 80, 30);
		
		//Minimap
		g.setColor(new Color(40, 120, 0));
		g.fillRect(10, INVENT_HEIGHT - INVENT_WIDTH + 10, INVENT_WIDTH-20, INVENT_WIDTH-20);
		g.setColor(Color.black);
		g.drawRect(10, INVENT_HEIGHT - INVENT_WIDTH + 10, INVENT_WIDTH-20, INVENT_WIDTH-20);
		
		double scale = GameCourt.FIELD_SIZE / (INVENT_WIDTH-20);
		int xShift = -(int)(field.pos_x/scale);
		int yShift = -(int)(field.pos_y/scale);
		if (xShift <= 0) xShift = 0;
		else if (xShift >= INVENT_WIDTH-20-(int)(600/scale)) xShift = INVENT_WIDTH-20-(int)(600/scale);
		if (yShift <= 0) yShift = 0;
		else if (yShift >= INVENT_WIDTH-20-(int)(600/scale)) yShift = INVENT_WIDTH-20-(int)(600/scale);
		g.drawRect(10 + xShift, (INVENT_HEIGHT - INVENT_WIDTH + 10) + yShift,
				   (int)(600/scale), (int)(600/scale));
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(INVENT_WIDTH,INVENT_HEIGHT);
	}
}
