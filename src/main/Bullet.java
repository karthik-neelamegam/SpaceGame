package main;

import interfaces.EntityA;

import java.awt.Graphics;
import java.awt.Rectangle;

import animations.Animation;

public class Bullet extends GameObject implements EntityA{
	
	private double velY = 20;
	private int width, height;
	
	private Textures tx;
	public Game game;
	Animation anim;
	public Controller c;
	
	public Bullet(double x, double y, Textures tx, Game game, Controller c) {
		super(x, y);
		
		this.tx = tx;
		this.game = game;	
		this.c = c;
		
		anim = new Animation(5, this.tx.bullet);
		
		width = tx.bullet[0].getWidth();
		height = tx.bullet[0].getHeight();
	}
	
	public void update() {
		
		y -= velY;
			
		anim.runAnimation();
	
	}
	
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
}
