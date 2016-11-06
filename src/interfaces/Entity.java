package interfaces;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Entity {
	
	public void update();
	public void render(Graphics g);
	
	public Rectangle getRect();
}
