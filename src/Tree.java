import java.awt.Color;
import java.awt.Graphics;

public class Tree extends GameObj{
	
	public static int size = 150, stumpWidth = 60, stumpHeight = 150;
	private int fieldShiftX, fieldShiftY;
	private Field field;
	

	public Tree (int pos_x, int pos_y, Field f) {
		super(0,0,pos_x, pos_y, stumpWidth, stumpHeight,GameCourt.COURT_WIDTH,GameCourt.COURT_HEIGHT);
		fieldShiftX = pos_x;
		fieldShiftY	= pos_y;
		this.field = f;
	}
	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(60,50,0));
		g.fillRect(pos_x+size/2 -stumpWidth/2, pos_y+size-4*stumpHeight/5, stumpWidth, stumpHeight);
		g.setColor(Color.black);
		g.drawRect(pos_x+size/2 -stumpWidth/2, pos_y+size-4*stumpHeight/5, stumpWidth, stumpHeight);
		
		g.setColor(Color.green);
		g.fillOval(pos_x, pos_y, size, size);
		g.setColor(Color.black);
		g.drawOval(pos_x, pos_y, size, size);
	}
	
	public void clip(){
		this.pos_x = field.pos_x + fieldShiftX;
		this.pos_y = field.pos_y + fieldShiftY;
	}
	
	public boolean willIntersectPlayer(){
		int next_x = pos_x + field.v_x + size/2 - stumpWidth/2;
		int next_y = pos_y + field.v_y + size-4*stumpHeight/5;
		int playerX = GameCourt.COURT_WIDTH/2;
		int playerY = GameCourt.COURT_HEIGHT/2;
		return (next_x < playerX + Player.SIZE/2 &&
				next_x + stumpWidth > playerX  - Player.SIZE/2 && 
				next_y < playerY + Player.SIZE/2 && 
				next_y + stumpHeight > playerY - Player.SIZE/2);
		
	}
	
	public Direction treeRelationToPlayer() {
		Direction d = null;
		int next_x = pos_x + size/2 - stumpWidth/2;
		int next_y = pos_y + size-4*stumpHeight/5;
		int playerX = GameCourt.COURT_WIDTH/2;
		int playerY = GameCourt.COURT_HEIGHT/2;
		
		int tolerance = GameCourt.SQUARE_VELOCITY;
		
		if (playerX + Player.SIZE/2 < next_x + tolerance) d = Direction.RIGHT;
		if (playerX - Player.SIZE/2 > next_x + stumpWidth - tolerance) d = Direction.LEFT;
		if (playerY + Player.SIZE/2 < next_y + tolerance) d = Direction.DOWN;
		if (playerY - Player.SIZE/2 > next_y + stumpHeight - tolerance) d = Direction.UP;
		
		return d;
	}
	
}
