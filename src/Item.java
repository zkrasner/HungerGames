import java.awt.Graphics;

public abstract class Item {
	
	private boolean held, projectile;
	public int x, y, weight, xSize, ySize, airTime, vx, vy;
	
	
	public Item(int x, int y, int xSize, int ySize, int weight) {
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		this.weight = weight;
		held = false;
		projectile = false;
		airTime = 0;
		vx = 0; 
		vy = 0;
	}

	public boolean isHeld() {
		return held;
	}

	public void pickUp() {
		this.held = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWeight() {
		return weight;
	}

	public void updateInventLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void toss(int airTime, int finalX, int finalY){
		projectile = true;
		this.airTime = airTime;
		this.vx = -2;
		this.vy = -2;
		System.out.println("fired");
	}

	public boolean isProjectile(){
		return projectile;
	}
	
	public void move(){
		this.x += vx;
		this.y += vy;
		airTime--;
	}
	
	public void draw(Graphics g){}
}
