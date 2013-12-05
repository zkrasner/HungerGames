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
public class EquipmentPanel extends JPanel {

	public static final int EQUIP_WIDTH = 200;
	public static final int EQUIP_HEIGHT = 600;
	public static final int INTERVAL = 35; 
	private final Field field;
	private final Player player;
	private Item selected = null;
	
	public EquipmentPanel(JLabel status, final Field field, final Player player){
		
		this.field = field;
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
		
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e){
				String clicked = clickLocation(e.getX(), e.getY());
				if (clicked != null) { 
					if (clicked.equalsIgnoreCase("Left Arm") && player.leftArm != null) {
						selected = player.leftArm;
						System.out.println("letadf");
					} else if (clicked.equalsIgnoreCase("Ammo") && player.ammo != null) {
						selected = player.ammo;
						System.out.println("letadf");
					}
				}
			}
			public void mouseReleased(MouseEvent e){
				if (e.getButton()==MouseEvent.BUTTON1||e.getButton()==MouseEvent.BUTTON3||e.isControlDown()) {
					int row = ((e.getY()-10)/25);
					int col = ((e.getX()+InventoryPanel.INVENT_WIDTH+GameCourt.COURT_WIDTH-10)/25);
					//Check if something is selected from the equipment display
					if (selected != null) {
						//if released within the inventory, move the item to that spot
						if (row < 16 && row >= 0 && col < 8 && col >=0) {
							if (player.ammo != null && selected.equals(player.ammo)) {
								for (int i = 0; i < player.ammoCount; i++) {
									Item newAmmo = null;
									if (player.ammo instanceof Rock) {
										newAmmo = new Rock(0,0,field);
										newAmmo.pickUp();
									}
									if (newAmmo != null) player.addItem(newAmmo);
								}
								player.ammo = null;
								player.ammoCount = 0;
							} else {
								player.addItem(selected);
								removeEquipment(selected);
							}
						selected = null;
						//if the mouse is released somewhere in the Equipment Panel
						} else if (addEquipment(selected,
								e.getX(),e.getY())) {
							player.removeItem(selected);
							if (selected instanceof Ammo && e.getButton() == MouseEvent.BUTTON3 || e.isControlDown()) {
								for (int i = 0; i < player.itemDisplay.length; i++) {
									for (int j = 0; j < player.itemDisplay[0].length; j++) {
										if (selected instanceof Rock && player.itemDisplay[i][j] instanceof Rock) {
											addEquipment(selected,
													e.getX(),e.getY());
											player.itemDisplay[i][j] = null;
										}
									}
								}
							}
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
	public boolean addEquipment(Item item, int x, int y) {
		boolean placed = false;
		
		String location = clickLocation(x,y);
		int startHeight = 50;
		if (item != null && location != null) {
			if (location.equals("Ammo") && item instanceof Ammo) {
				if (player.ammo == null) {
					item.updateInventLoc(EQUIP_WIDTH/2-68 -item.xSize/2, startHeight+240 - item.ySize/2);
					player.ammo = item;
					player.ammoCount++;
					placed = true;
				} else if (item instanceof Rock && player.ammo instanceof Rock){
					player.ammoCount++;
					placed = true;
				}
			} 
			if (location.equals("Left Arm") && item instanceof Weapon) {
				if (player.leftArm == null){
					item.updateInventLoc(EQUIP_WIDTH/2-68-item.xSize/2, startHeight+105 - item.ySize/2);
					player.leftArm = (Weapon)item;
					placed = true;
				}
			}
			if (location.equals("Right Arm") && item instanceof Weapon) {
				if (player.leftArm == null){
					item.updateInventLoc(EQUIP_WIDTH/2-68-item.xSize/2, startHeight+105 - item.ySize/2);
					player.leftArm = (Weapon)item;
					placed = true;
				} else if (!player.leftArm.isTwoHanded()) {
					item.updateInventLoc(EQUIP_WIDTH/2+69-item.xSize/2, startHeight+105 - item.ySize/2);
					player.rightArm = (Weapon)item;
					placed = true;
				}
			}
		}
		return placed;
	}
	
	public boolean removeEquipment(Item item) {
		boolean removed = false;
		
		if (item.equals(player.ammo)) {
			player.ammo = null;
			player.ammoCount = 0;
			removed = true;
		} else if (item.equals(player.leftArm)) {
			player.leftArm = null;
			removed = true;
		}
		return removed;
	}
	
	public static String clickLocation(int x, int y) {
		String equip = null;
		int startHeight = 50;
		if (x>=EQUIP_WIDTH/2-25 && x<=EQUIP_WIDTH/2+25 && 
				y>=startHeight && y<=startHeight+50) equip = "Head";
		if (x>=EQUIP_WIDTH/2-37 && x<=EQUIP_WIDTH/2+37 && 
				y>=startHeight+55 && y<=startHeight+155) equip = "Body";
		if (x>=EQUIP_WIDTH/2-93 && x<=EQUIP_WIDTH/2-43 && 
				y>=startHeight+55 && y<=startHeight+155) equip = "Left Arm";
		if (x>=EQUIP_WIDTH/2-88 && x<=EQUIP_WIDTH/2-38 && 
				y>=startHeight+215 && y<=startHeight+265) equip = "Ammo";
		if (x>=EQUIP_WIDTH/2+44 && x<=EQUIP_WIDTH/2+94 && 
				y>=startHeight+55 && y<=startHeight+155) equip = "Right Arm";
		if (x>=EQUIP_WIDTH/2-37 && x<=EQUIP_WIDTH/2+37 && 
				y>=startHeight+160 && y<=startHeight+235) equip = "Leg";
		if (x>=EQUIP_WIDTH/2-37 && x<=EQUIP_WIDTH/2+37 && 
				y>=startHeight+240 && y<=startHeight+265) equip = "Feet";
		return equip;
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		int startHeight = 50;
		//Head
		g.drawRect(EQUIP_WIDTH/2 - 25, startHeight, 50, 50);
		//Body
		g.drawRect(EQUIP_WIDTH/2 - 37, startHeight + 55, 75, 100);
		//Left Arm
		g.drawRect(EQUIP_WIDTH/2 - 93, startHeight + 55, 50, 100);
		g.drawString("Primary", EQUIP_WIDTH/2 - 93, startHeight + 50);
		if (player.leftArm != null) player.leftArm.draw(g);
		g.setColor(Color.black);
		//Ammo
		g.drawString("Ammo", EQUIP_WIDTH/2 - 88, startHeight + 210);
		g.drawRect(EQUIP_WIDTH/2 - 93, startHeight + 215, 50, 50);
		if (player.ammo != null) player.ammo.draw(g);
		g.setColor(Color.white);
		if (player.ammoCount != 0) g.drawString(""+player.ammoCount,EQUIP_WIDTH/2-90,startHeight+260);
		g.setColor(Color.black);
		//Right Arm
		g.drawRect(EQUIP_WIDTH/2 + 44, startHeight + 55, 50, 100);
		g.drawString("Second", EQUIP_WIDTH/2+46, startHeight + 50);
		if (player.leftArm != null && player.leftArm.isTwoHanded()) {
			g.drawLine(EQUIP_WIDTH/2 + 44, startHeight + 55, EQUIP_WIDTH/2 + 94, startHeight + 155);
			g.drawLine(EQUIP_WIDTH/2 + 44, startHeight + 155, EQUIP_WIDTH/2 + 94, startHeight + 55);
		}
		//Legs
		g.drawRect(EQUIP_WIDTH/2 - 37, startHeight + 160, 75, 75);
		//Feet
		g.drawRect(EQUIP_WIDTH/2 - 37, startHeight + 240, 75, 25);
		
		//Minimap
		g.setColor(new Color(40, 120, 0));
		g.fillRect(10, EQUIP_HEIGHT - EQUIP_WIDTH + 10, EQUIP_WIDTH-20, EQUIP_WIDTH-20);
		g.setColor(Color.black);
		g.drawRect(10, EQUIP_HEIGHT - EQUIP_WIDTH + 10, EQUIP_WIDTH-20, EQUIP_WIDTH-20);
		
		double scale = GameCourt.FIELD_SIZE / (EQUIP_WIDTH-20);
		int xShift = -(int)(field.pos_x/scale);
		int yShift = -(int)(field.pos_y/scale);
		if (xShift <= 0) xShift = 0;
		else if (xShift >= EQUIP_WIDTH-20-(int)(600/scale)) xShift = EQUIP_WIDTH-20-(int)(600/scale);
		if (yShift <= 0) yShift = 0;
		else if (yShift >= EQUIP_WIDTH-20-(int)(600/scale)) yShift = EQUIP_WIDTH-20-(int)(600/scale);
		g.drawRect(10 + xShift, (EQUIP_HEIGHT - EQUIP_WIDTH + 10) + yShift,
				   (int)(600/scale), (int)(600/scale));
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(EQUIP_WIDTH,EQUIP_HEIGHT);
	}
}
