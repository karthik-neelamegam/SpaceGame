package main;

import interfaces.EntityA;
import interfaces.EntityB;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

	private LinkedList<EntityA> eA = new LinkedList<EntityA>();
	private LinkedList<EntityB> eB = new LinkedList<EntityB>();


	EntityA entA;
	EntityB entB;
	
	public Game game;
	
	Random r = new Random();

	private Textures tx;
	public Controller(Game game, Textures tx) {
		this.game = game;
		this.tx = tx;
	}
	
	public void addEnemy(int enemyCount) {
		for(int i = 0; i < enemyCount; i++) {
			addEntity(new Enemy(r.nextInt(Game.WIDTH*Game.SCALE - tx.enemy[0].getWidth()), -10, tx, game, this));
		}
	}
	
	public void update() {
		
		//EntityA
		for (int i = 0; i < eA.size(); i++) {
			entA = eA.get(i);
			
			entA.update();
		}
		
		//EntityB
		for (int i = 0; i < eB.size(); i++) {
			entB = eB.get(i);
			
			entB.update();
		}

	}

	public void render(Graphics g) {
		
		//EntityA
		for (int i = 0; i < eA.size(); i++) {
			entA = eA.get(i);
			
			entA.render(g);
		}
		
		
		//EntityB
		for (int i = 0; i < eB.size(); i++) {
			entB = eB.get(i);
			
			entB.render(g);
		}
	}
	
	public void addEntity(EntityA ent) {
		eA.add(ent);
	}
	
	public void addEntity(EntityB ent) {
		eB.add(ent);
	}
	
	

	public void removeEntity(EntityA ent) {
		eA.remove(ent);
	}
	
	public void removeEntity(EntityB ent) {
		eB.remove(ent);
	}
	
	public LinkedList<EntityA> geteA() {
		return eA;
	}

	public LinkedList<EntityB> geteB() {
		return eB;
	}
	
	public void seteA(LinkedList<EntityA> eA) {
		this.eA = eA;
	}

	public void seteB(LinkedList<EntityB> eB) {
		this.eB = eB;
	}


}
