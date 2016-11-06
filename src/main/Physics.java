package main;

import interfaces.EntityA;
import interfaces.EntityB;

public class Physics {

	public static boolean Collision(EntityA entA, EntityB entB) {
		
		if(entA.getRect().intersects(entB.getRect()))
			return true;
		
		
		return false;
	}
	
	public static boolean Collision(EntityB entB, EntityA entA) {
	
		if(entA.getRect().intersects(entB.getRect()))
			return true;
		
		return false;
	}
}
