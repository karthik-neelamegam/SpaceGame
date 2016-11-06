package main;

import interfaces.EntityA;
import interfaces.EntityB;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import animations.Animation;

public class Enemy extends GameObject implements EntityB{
	
	Random r = new Random();

	private double velY = r.nextInt(10) + 1;
	private int width, height;
	
	private Textures tx;
	public Game game;
	public Controller c;
		
	Animation anim;
	Animation explode;
	
	private boolean killed = false;
	
	public Enemy(double x, double y, Textures tx, Game game, Controller c) {
		super(x, y);
		this.tx = tx;
		this.game = game;
		this.c = c;
		
		anim = new Animation(5, this.tx.enemy);
		explode = new Animation(2, this.tx.explosion);
		
		width = tx.enemy[0].getWidth();
		height = tx.enemy[0].getHeight();
	}
	
	public void update() {
		
		EntityA entA;
		
		for(int i = 0; i < c.geteA().size(); i++) {
			entA = c.geteA().get(i);

			if(Physics.Collision(this, entA)) {
				if(game.isDestroyBullet())	
					c.removeEntity(entA);
					killed = true;
					
			}
		}
		
		if(Physics.Collision(this, game.getP()) && !killed) {
			if(!game.isInvincible()) 
				game.getP().setHealth(game.getP().getHealth() - 10);
			killed = true;
		}
		
		if(killed) {
			
			explode.runAnimation();
			
			if(explode.isFin()) {
				game.getP().setScore(game.getP().getScore() + 10);
				c.removeEntity(this);
				game.setEnemyKilled(game.getEnemyKilled()+1);
			}
		}
				
		else if(!killed) {
			y += velY;
			
			anim.runAnimation();
			
			if(y > Game.HEIGHT*Game.SCALE) {
				game.getP().setScore(game.getP().getScore() - 1);
				y = 0;
				velY = r.nextInt(10) + 1;
				x = r.nextInt(Game.WIDTH*Game.SCALE - width);
			}
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < c.geteA().size(); i++) {
			EntityA tempEntA = c.geteA().get(i);

			if(Physics.Collision(this, tempEntA)) {
				killed = true;
			}
		}
		
		if(killed) {
			explode.drawAnimation(g, x, y, 0);
			if(explode.isFin()) {
				return;
			}
		}
		else
			anim.drawAnimation(g, x, y, 0);

			
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
