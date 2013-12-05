import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class SlingShot extends Weapon{

	public static int xSIZE = 20, ySIZE = 20, dist = 150;
	private Field field;
	
	public SlingShot(int x, int y, Field field) {
		super(x,y,xSIZE, ySIZE,5,true, dist, true,5);
		this.field = field;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(237,204,104));
		if (!isHeld()) {
			g.fillRect(x + field.pos_x + xSize/3, y + field.pos_y+ySize/4, xSIZE/3, 3*ySIZE/4);
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(3));
			g2.drawLine(x+field.pos_x+2+xSize/3, y+field.pos_y+ySize/4,x+field.pos_x,y+field.pos_y);
			g2.drawLine(x+field.pos_x+2+xSize/3, y+field.pos_y+ySize/4,x+field.pos_x+xSIZE-3,y+field.pos_y);
			g2.setStroke(new BasicStroke(1));
			
		} else {
			g.fillRect(x+xSize/3+2, y+ySize/4+1, xSIZE/3, 3*ySIZE/4);
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(3));
			g2.drawLine(x+4+xSize/3, y+ySize/4+1,x+2,y+1);
			g2.drawLine(x+4+xSize/3, y+ySize/4+1,x+xSIZE-1,y+1);
			g2.setStroke(new BasicStroke(1));
		}
	}
}
