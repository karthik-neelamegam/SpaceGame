package main;

import interfaces.EntityA;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game.STATE;
import animations.Animation;

public class Player extends GameObject implements EntityA{
	
	public final int INITIAL_HEALTH = 200;
	public int health = INITIAL_HEALTH;
	
	public int score = 0;
	
	private double velRX = 0, velLX = 0, velUY = 0, velDY = 0;
	private int width, height;
	private Textures tx;
	
	private Animation anim;
	
	public Player(double x, double y, Textures tx) {
		super(x, y);
		this.tx = tx;
		anim = new Animation(5, this.tx.player);
		width = tx.player[0].getWidth();
		height = tx.player[0].getHeight();
	}
	
	public void update() {
		x+=velRX;
		x-=velLX;
		y-=velUY;
		y+=velDY;
		
		if(x <= 0)
			x = 0;
		if(x >= Game.WIDTH*Game.SCALE - width/1.5)
			x = Game.WIDTH*Game.SCALE - width/1.5;
		
		if(y <= 0)
			y = 0;
		if(y >= Game.HEIGHT*Game.SCALE - width/1.5)
			y = Game.HEIGHT*Game.SCALE - width/1.5;
		
		if(health == 0) {
			Game.state = STATE.GAME_OVER;
		}
		
		anim.runAnimation();
	}
	
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}
	
	
	public void setVelRX(double velX) {
		this.velRX = velX;
	}
	
	public void setVelUY(double velY) {
		this.velUY = velY;
	}
	
	public void setVelLX(double velX) {
		this.velLX = velX;
	}
	
	public void setVelDY(double velY) {
		this.velDY = velY;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getINITIAL_HEALTH() {
		return INITIAL_HEALTH;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
