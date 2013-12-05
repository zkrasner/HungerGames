import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Field extends GameObj{

	private final int size;
	//public int v_x = 0, v_y = 0;
	private ArrayList<GameObj> nature = new ArrayList<GameObj>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Item>	projectiles = new ArrayList<Item>();
	
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
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void addProjectile(Item item) {
		projectiles.add(item);
	}
	
	public ArrayList<GameObj> getNature(){
		return nature;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public ArrayList<Item> getProjectiles(){
		return projectiles;
	}
	
	public Item nearLocation(int x, int y) {
		Item go = null;
		for (Item g: items){
			if (Math.abs(g.x -x) <= 20 && Math.abs(g.y - y) <= 20) {
				go = g;
			}
		}
		return go;
	}
	
	public void removeClicks(){
		Iterator<GameObj> gos = nature.iterator();
		while (gos.hasNext()){
			GameObj go = gos.next();
			if (go instanceof ClickAction){
				if (((ClickAction)(go)).getDisappear() == 0) {
					gos.remove();
				}
			}
		}
	}
	
	public void removeObj(GameObj go){
		nature.remove(go);
	}
	
	public void removeObj(Item item){
		items.remove(item);
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
