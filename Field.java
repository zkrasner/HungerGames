package hungerGames;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Field extends GameObj{

	private final int size;
	//public int v_x = 0, v_y = 0;
	private ArrayList<GameObj> nature = new ArrayList<GameObj>();
	
	public Field() {
		super(0,0, 0, 0, 1000, 1000, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		this.size = 1000;
	}
	
	public Field(int size) {
		super(0, 0, 0, 0, size, size, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
		this.size = size;
	}
	
	public int getsize(){
		return size;
	}
	
	public void updateVels(GameObj go){
		go.v_x = this.v_x;
		go.v_y = this.v_y;
	}
	
	public void addNature(GameObj go) {
		updateVels(go);
		nature.add(go);
	}
	
	public ArrayList<GameObj> getNature(){
		return nature;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(40, 120, 0));
		g.fillRect(pos_x, pos_y, size, size);
		for (int i = 0; i <= size; i += 50){
			g.setColor(Color.black);
			g.drawLine(i+pos_x, pos_y, i+pos_x, size+pos_y);
			g.drawLine(pos_x, i+pos_y, size+pos_x, i+pos_y);
		}
	}
	
	public void move(){
		pos_x += v_x;
		pos_y += v_y;

		for (GameObj go: nature){
			go.clip();
		}
		
		clip();
	}
	
	public void clip(){
		if (pos_x >= GameCourt.COURT_WIDTH/2-Player.SIZE/2) 
			pos_x = GameCourt.COURT_WIDTH/2-Player.SIZE/2;
		else if (pos_x < -size+GameCourt.COURT_WIDTH/2+Player.SIZE/2) 
			pos_x = -size+GameCourt.COURT_WIDTH/2+Player.SIZE/2;

		if (pos_y >= GameCourt.COURT_WIDTH/2-Player.SIZE/2) 
			pos_y = GameCourt.COURT_WIDTH/2-Player.SIZE/2;
		else if (pos_y < -size+GameCourt.COURT_WIDTH/2+Player.SIZE/2)
			pos_y = -size+GameCourt.COURT_WIDTH/2+Player.SIZE/2;
	}
}
