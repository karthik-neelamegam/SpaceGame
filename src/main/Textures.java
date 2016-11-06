package main;

import java.awt.image.BufferedImage;

public class Textures {

	public BufferedImage[] player = new BufferedImage[3];
	public BufferedImage[] bullet = new BufferedImage[1];
	public BufferedImage[] enemy = new BufferedImage[2];
	public BufferedImage[] explosion = new BufferedImage[5];
			
	private SpriteSheet ss;
	public Textures(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	private void getTextures() {
		for(int i = 0; i < player.length; i++) {
			player[i] = ss.grabImage(0, i, 64, 64);
		}
		
		for(int i = 0; i < bullet.length; i++) {
			bullet[i] = ss.grabImage(2, i, 64, 32);
		}
		
		for(int i = 0; i < enemy.length; i++) {
			enemy[i] = ss.grabImage(6, i, 64, 36);
		}
		
		for(int i = 0; i < explosion.length; i++) {
			explosion[i] = ss.grabImage(4, i, 64, 64);
		}
	}
	
}
