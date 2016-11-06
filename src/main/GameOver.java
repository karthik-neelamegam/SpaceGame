package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

public class GameOver extends Menu {

	
	public GameOver(Game game) {
		super(game);
	}

	@Override
	public void render(Graphics g) {
		try {
			title = loader.loadImage("/gameover.png");
		} catch (IOException e) {
			//doNothing
		}
		
		g.drawImage(title, 235, 50, null);
		
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		g.drawString("Score: " + game.getP().getScore(), (Game.WIDTH*Game.SCALE) / 2 - 250, 180);
		g.drawString("Wave reached: " + game.getWave(), (Game.WIDTH*Game.SCALE) / 2 - 10, 180);

		drawButtons(g);
	}

}
