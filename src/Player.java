import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Player extends GameObj{

	private int health, stamina, speed, strength;
	public static int SIZE = 50;
	private ArrayList<Item> items = new ArrayList<Item>();
	public Item[][] itemDisplay = new Item[16][8];
	public Item head, body, legs, feet, ammo;
	public Weapon leftArm, rightArm;
	public int ammoCount;
	
	public Player() {
		super(0, 0, GameCourt.COURT_WIDTH/2 - SIZE/2, GameCourt.COURT_HEIGHT/2 - SIZE/2,
				SIZE, SIZE, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		health = 100;
		stamina = 20;
		speed = 5;
		strength = 10;
	}
	
	public Player(int x, int y) {
		super(0, 0, x,y, SIZE, SIZE, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		health = 100;
		stamina = 20;
		speed = 5;
		strength = 10;
	}
	
	public int getStamina() {
		return stamina;
	}

	public int getSpeed() {
		return speed;
	}

	public int getStrength() {
		return strength;
	}

	public int getHealth() {
		return health;
	}
	
	public boolean addItem(Item go){
		boolean placed = false;
		for (int row = 0; row < itemDisplay.length; row++) {
			for (int col = 0; col < itemDisplay[0].length; col++) {
				if (!placed && itemDisplay[row][col] == null) {
					go.updateInventLoc(22 + 25*col - go.xSize/2, 22 + 25*row - go.ySize/2);
					itemDisplay[row][col] = go;
					placed = true;
				}	
			}
		}
		if (placed) items.add(go);
		return placed;
	}
	
	public boolean removeItem(Item item) {
		boolean removed = false;
		if (item != null) {
			for (int row = 0; row < itemDisplay.length; row++) {
				for (int col = 0; col < itemDisplay[0].length; col++) {
					if (itemDisplay[row][col] != null && itemDisplay[row][col].equals(item)) 
						itemDisplay[row][col] = null;
					removed = true;
				}
			}
		}
		return removed;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(pos_x, pos_y, width, height);
	}
	
	
}
