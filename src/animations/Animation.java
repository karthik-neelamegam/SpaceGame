package animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int speed;
	private int frames;
	private int index;
	private int count = -1;
	private boolean fin = false;
	
	private BufferedImage[] sprite;

	private BufferedImage currentImg;

	// 13 frame animation
	public Animation(int speed, BufferedImage[] sprite) {
		this.speed = speed;
		this.sprite = sprite;
		frames = sprite.length;
		index = speed + 1;
	}

	
	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			count++;
			nextFrame();
		}
	}


	public void nextFrame() {
		
		if(count < frames) {
			currentImg = sprite[count];
		}
		else if(count == frames) {
			fin = true;
			count = 0;
			currentImg = sprite[count];
		}
			
	}

	public void drawAnimation(Graphics g, double x, double y, int offset) {
		g.drawImage(currentImg, (int) x - offset, (int) y, null);
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getFrames() {
		return frames;
	}


	public void setFrames(int frames) {
		this.frames = frames;
	}


	public boolean isFin() {
		return fin;
	}


	public void setFin(boolean fin) {
		this.fin = fin;
	}


}
