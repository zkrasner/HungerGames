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

	public static final int INVENT_WIDTH = 220;
	public static final int INVENT_HEIGHT = 600;
	public static final int INTERVAL = 35; 
	private Player player;

	public InventoryPanel(JLabel status, Player player){

		this.player = player;
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
		g.setColor(Color.black);
		for (int i = 0; i <= 2*INVENT_HEIGHT/3; i += 25){
			g.setColor(Color.black);
			//Vertical
			g.drawLine(i+10, 10, i+10, 2*INVENT_HEIGHT/3 + 10);
			//Horizontal
			g.drawLine(10, i+10, INVENT_WIDTH-10, i+10);
		}
		//Health Bar
		int startY = 2*INVENT_HEIGHT/3+20;
		g.setColor(Color.black);
		g.fillRect(10, startY, player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.red);
		g.fillRect(10, startY, player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.black);
		g.drawRect(10, startY, INVENT_WIDTH-20, (2/3)*INVENT_HEIGHT + 30);
		
		//Stamina Bar
		startY += 40;
		g.setColor(Color.black);
		g.fillRect(10, startY, player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.blue);
		g.fillRect(10, startY,player.getHealth()*(INVENT_WIDTH-20)/100, (2/3)*INVENT_HEIGHT + 30);
		g.setColor(Color.black);
		g.drawRect(10, startY, INVENT_WIDTH-20, (2/3)*INVENT_HEIGHT + 30);
		
		
	}

	public Dimension getPreferredSize(){
		return new Dimension(INVENT_WIDTH,INVENT_HEIGHT);
	}
}
