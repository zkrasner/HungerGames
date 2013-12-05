
public abstract class Weapon extends Item{

	private boolean range, twoHanded;
	private final int damage, dist;
	
	public Weapon (int x, int y,int xSize, int ySize, int weight, boolean range, int dist, boolean twoHanded, int damage) {
		super (x,y,xSize, ySize,weight);
		this.range = range;
		this.dist = dist;
		this.twoHanded = twoHanded;
		this.damage = damage;
	}

	public boolean isRange() {
		return range;
	}
	
	public boolean isTwoHanded() {
		return twoHanded;
	}

	public int getDamage() {
		return damage;
	}
	
	public int getDist() {
		return dist;
	}
}
