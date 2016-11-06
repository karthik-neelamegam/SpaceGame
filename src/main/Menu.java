package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu {

	Game game;
	
	public Rectangle playBtn = new Rectangle((Game.WIDTH * Game.SCALE) / 4, 200, (Game.WIDTH * Game.SCALE) / 2, 90);
	public Rectangle helpBtn = new Rectangle((Game.WIDTH * Game.SCALE) / 4, 310, (Game.WIDTH * Game.SCALE) / 2, 90);
	public Rectangle optBtn = new Rectangle((Game.WIDTH * Game.SCALE) / 4, 420, (Game.WIDTH * Game.SCALE) / 2, 90);
	public Rectangle quitBtn = new Rectangle((Game.WIDTH * Game.SCALE) / 4, 530, (Game.WIDTH * Game.SCALE) / 2, 90);

	public Rectangle[] Btns = {playBtn, helpBtn, optBtn, quitBtn};
	
	BufferedImageLoader loader = new BufferedImageLoader();
	BufferedImage title;
	
	protected boolean[] hovered = new boolean[4];
	
	//private boolean[] pressed = new boolean[4];

	private boolean playHov = false, helpHov = false, optHov = false, quitHov = false;

	
	public Menu(Game game) {
		this.game = game;
	}
	
	public void render(Graphics g) {
		
		try {
			title = loader.loadImage("/title.png");
		} catch (IOException e) {
			//doNothing
		}
		
		g.drawImage(title, 200, 50, null);
		
		drawButtons(g);
		
	}
	
	public void drawButtons(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(hovered[0]) g.setColor(new Color(255, 106, 0));
		else g.setColor(Color.yellow);
		g2.fill(playBtn);
		g.setColor(Color.BLACK);
		g.setFont(new Font("AR DESTINE", Font.BOLD, 70));
		g.drawString("PLAY", playBtn.x + 30, playBtn.y + 65);
		
		if(hovered[1]) g.setColor(Color.gray);
		else g.setColor(Color.lightGray);
		g2.fill(helpBtn);
		g.setColor(Color.BLACK);
		g.setFont(new Font("AR DESTINE", Font.PLAIN, 70));
		g.drawString("HELP", helpBtn.x + 30, helpBtn.y + 65);
		
		if(hovered[2]) g.setColor(Color.gray);
		else g.setColor(Color.lightGray);
		g2.fill(optBtn);
		g.setColor(Color.BLACK);
		g.setFont(new Font("AR DESTINE", Font.PLAIN, 70));
		g.drawString("OPTIONS", optBtn.x + 30, optBtn.y + 65);
		
		if(hovered[3]) g.setColor(Color.gray);
		else g.setColor(Color.lightGray);
		g2.fill(quitBtn);
		g.setColor(Color.BLACK);
		g.setFont(new Font("AR DESTINE", Font.PLAIN, 70));
		g.drawString("QUIT", quitBtn.x + 30, quitBtn.y + 65);
	}
	
	public Rectangle getPlayBtn() {
		return playBtn;
	}

	public Rectangle getHelpBtn() {
		return helpBtn;
	}

	public Rectangle getOptBtn() {
		return optBtn;
	}


	public Rectangle getQuitBtn() {
		return quitBtn;
	}
	
	public boolean isPlayHov() {
		return playHov;
	}

	public void setPlayHov(boolean playHov) {
		this.playHov = playHov;
	}

	public boolean isHelpHov() {
		return helpHov;
	}

	public void setHelpHov(boolean helpHov) {
		this.helpHov = helpHov;
	}

	public boolean isOptHov() {
		return optHov;
	}

	public void setOptHov(boolean optHov) {
		this.optHov = optHov;
	}

	public boolean isQuitHov() {
		return quitHov;
	}

	public void setQuitHov(boolean quitHov) {
		this.quitHov = quitHov;
	}

	public boolean[] getHovered() {
		return hovered;
	}

	public void setHovered(int index, boolean hovered) {
		this.hovered[index] = hovered;
	}

	public Rectangle[] getBtns() {
		return Btns;
	}

	public void setBtns(Rectangle[] btns) {
		Btns = btns;
	}

}
