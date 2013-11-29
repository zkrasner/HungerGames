import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Rock extends GameObj{
	
	public static int SIZE = 10;
	private int fieldShiftX, fieldShiftY;
	private Field field;
	
	public Rock(int x, int y, Field field){
		super(0,0, x, y, SIZE, SIZE, GameCourt.WIDTH, GameCourt.HEIGHT);
		fieldShiftX = x;
		fieldShiftY = y;
		this.field = field;
	}

	@Override
	public void draw(Graphics g){
		g.setColor(Color.gray);
		g.fillOval(pos_x, pos_y, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawOval(pos_x, pos_y, SIZE, SIZE);
	}
	
	public void clip(){
		this.pos_x = field.pos_x + fieldShiftX;
		this.pos_y = field.pos_y + fieldShiftY;
	}
}
