/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private Player player = new Player();
	private Field field = new Field(FIELD_SIZE);
	private int numTrees = 0, numRocks = 10;
	private Random random = new Random();
	public boolean attackMode = false;
	
	public static boolean playing = false;  // whether the game is running
	private JLabel status;       // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = COURT_WIDTH;
	public static final int FIELD_SIZE = 600 + Player.SIZE;
	public static final int SQUARE_VELOCITY = 5;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 20; 
	
	private boolean[] keysDown = {false, false, false, false};

	public GameCourt(JLabel status){
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

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the square.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int ke = e.getKeyCode();
				if (ke == KeyEvent.VK_LEFT || ke == KeyEvent.VK_A) keysDown[0] = true;
				else if (ke == KeyEvent.VK_RIGHT || ke == KeyEvent.VK_D) keysDown[1] = true;
				else if (ke == KeyEvent.VK_DOWN || ke == KeyEvent.VK_S) keysDown[2] = true;
				else if (ke == KeyEvent.VK_UP || ke == KeyEvent.VK_W) keysDown[3] = true;
				else if (ke == KeyEvent.VK_SPACE) attackMode = true;
			}
			public void keyReleased(KeyEvent e){
				int ke = e.getKeyCode();
				if (ke == KeyEvent.VK_LEFT || ke == KeyEvent.VK_A) keysDown[0] = false;
				else if (ke == KeyEvent.VK_RIGHT || ke == KeyEvent.VK_D) keysDown[1] = false;
				else if (ke == KeyEvent.VK_DOWN || ke == KeyEvent.VK_S) keysDown[2] = false;
				else if (ke == KeyEvent.VK_UP || ke == KeyEvent.VK_W) keysDown[3] = false;
				else if (ke == KeyEvent.VK_SPACE) attackMode = false;
			}
		});

		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e){
				if (!attackMode || (attackMode && player.leftArm == null)) { 
					int me = e.getButton();
					field.addNature(new ClickAction(e.getX(),e.getY()));
					if (me == MouseEvent.BUTTON1) {
						Item clicked = field.nearLocation(e.getX()-field.pos_x,e.getY()- field.pos_y);
						if (clicked != null){
							clicked.pickUp();
							player.addItem(clicked);
							field.removeObj(clicked);
						}
					}
				} else if (attackMode && player.leftArm != null && e.getButton() == MouseEvent.BUTTON1){
						int range = player.leftArm.getDist();
						if (player.leftArm.isRange() && player.ammo != null){
							if (player.ammo instanceof Rock) {
								Rock r = new Rock(player.pos_x, player.pos_y, field);
								field.addItem(r);
								r.toss(20, e.getX(), e.getY());
								
							}
						}
				}
			}
			public void mouseReleased(MouseEvent e){
				
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
		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void start() {;
		
		for (int i = 0; i < numTrees; i++){
			field.addNature(new Tree(random.nextInt(field.width-Tree.size),
									 random.nextInt(field.height-Tree.size), field));
		}
		for (int i = 0; i < numRocks; i++){
			field.addItem(new Rock(random.nextInt(field.width-Rock.SIZE),
									 random.nextInt(field.height- Rock.SIZE), field));
		}
		
		field.addItem(new SlingShot(random.nextInt(field.width - SlingShot.xSIZE),
									random.nextInt(field.height - SlingShot.ySIZE),
									field));
		
		playing = true;
		
		status.setText("Location: ");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (playing) {
			//set field vels
			if (keysDown[0] == true) field.v_x = SQUARE_VELOCITY;
			if (keysDown[1] == true) field.v_x = -SQUARE_VELOCITY;
			if (keysDown[2] == true) field.v_y = -SQUARE_VELOCITY;
			if (keysDown[3] == true) field.v_y = SQUARE_VELOCITY;
			if (!keysDown[0] && !keysDown[1]) field.v_x = 0;
			if (!keysDown[2] && !keysDown[3]) field.v_y = 0;
			
			//assess collisions with nature and remove useless objects
			for (GameObj go: field.getNature()){
				if (go instanceof Tree) {
					if (((Tree)go).willIntersectPlayer()) {
						Direction d = ((Tree) go).treeRelationToPlayer();
						if (d != null) {
							if (d == Direction.UP || d == Direction.DOWN) field.v_y = 0;
							if (d == Direction.LEFT || d == Direction.RIGHT) field.v_x = 0;
						}
					}
				}
			}
			
			
			//move the field and its contents
			field.move();
			for (GameObj go: field.getNature()){
				go.clip();
			}

			//player stays static in center of field
			int playerX = (-field.pos_x + COURT_WIDTH/2) - FIELD_SIZE/2;
			int playerY = (-field.pos_y + COURT_HEIGHT/2) - FIELD_SIZE/2;
			status.setText("Location: " + playerX + ", " + playerY);
			repaint();
		} 
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Field getField() {
		return field;
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		field.draw(g);
		for (Item item: field.getItems()) item.draw(g);
		player.draw(g);
		for (Item item: field.getProjectiles()) {
			item.move();
		}
		for (GameObj go: field.getNature()) go.draw(g);
		field.removeClicks();
		if (attackMode && player.leftArm != null) {
			int dist = player.leftArm.getDist();
			g.drawOval(COURT_WIDTH/2-dist, COURT_HEIGHT/2-dist,2*dist,2*dist);
		}
			
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
