package hungerGames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Player extends GameObj{

	private int health;
	public static int SIZE = 50;
	
	public Player(int courtWidth, int courtHeight) {
		super(0, 0, courtWidth/2 - SIZE/2, courtHeight/2 - SIZE/2, SIZE, SIZE, courtWidth, courtHeight);
		health = 100;
	}
	
	public Player(Point location, int courtWidth, int courtHeight) {
		super(0, 0, location.x, location.y, SIZE, SIZE, courtWidth, courtHeight);
		health = 100;
	}
	
	public int getHealth() {
		return health;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawRect(10, 10, 101, 13);
		g.setColor(Color.red);
		g.fillRect(11, 11, health, 12);
		g.setColor(Color.BLACK);
		g.fillOval(pos_x, pos_y, width, height);
		g.drawString("Health", 40, 22);
	}
	
	
}
