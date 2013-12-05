import java.awt.Color;
import java.awt.Graphics;


public class Rock extends Ammo{
	
	public static int weight = 3;
	private Field field;
	public static int SIZE = 10;
	
	public Rock(int x, int y, Field field){
		super(x, y, SIZE, SIZE, weight);
		this.field = field;
	}

	@Override
	public void draw(Graphics g){
		if (!isHeld()) {
			g.setColor(Color.gray);
			g.fillOval(x + field.pos_x, y + field.pos_y, SIZE, SIZE);
			g.setColor(Color.black);
			g.drawOval(x + field.pos_x, y + field.pos_y, SIZE, SIZE);
		} else {
			g.setColor(Color.gray);
			g.fillOval(x, y, SIZE, SIZE);
			g.setColor(Color.black);
			g.drawOval(x, y, SIZE, SIZE);
		}
	}
	
	@Override
	public void toss(int airTime, int finalX, int finalY){
		super.toss(airTime, finalX, finalY);
		field.addProjectile(this);
	}

}
