import java.awt.Color;
import java.awt.Graphics;


public class ClickAction extends GameObj{

	private int disappear;
	
	public ClickAction(int x, int y) {
		super(0, 0, x, y, 40, 40, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		disappear = 20;
	}
	
	public void tickTock(){
		if (disappear > 0) disappear -= 2;
	}
	
	public int getDisappear(){
		return disappear;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		
		g.fillRect(pos_x+20-2*disappear, pos_y+height/2-23, 4*(disappear-10), 6);
		g.fillRect(pos_x+width/2-23, pos_y+20-2*disappear, 6, 4*(disappear-10));
		tickTock();
	}

}
