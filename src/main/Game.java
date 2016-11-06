package main;

import inputs.KeyInput;
import inputs.MouseInput;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Space Game";

	private boolean running = false;
	private Thread t1;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private BufferedImageLoader loader;
	
	private Player p;
		private Rectangle remHealthBar;
		private Rectangle totalHealthBar;
	private Controller c;
	private Textures tx;
	private Menu menu;
	private GameOver gameOver;
	
	public static enum STATE{MENU, GAME, GAME_OVER};
	public static STATE state = STATE.MENU;
	private boolean gameStartedAgain = false;
	public int enemyCount = 1;
	public int enemyKilled = 0;
	private int wave = 1;
	
	private boolean isShooting = false;
	
	private boolean destroyBullet = true;	
	private boolean invincible = false;
	private	boolean[] invArr = new boolean[2];
	
	public void init() {
		requestFocus();
		loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			background = loader.loadImage("/background.png");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput(this));
		addMouseMotionListener(new MouseInput(this));
		
		menu = new Menu(this);
		gameOver = new GameOver(this);
		tx = new Textures(this);
		c = new Controller(this, tx);
		p = new Player(WIDTH*SCALE/2, HEIGHT*SCALE/2, tx);

		totalHealthBar = new Rectangle(10, 10, p.getINITIAL_HEALTH(), 50);
		remHealthBar = new Rectangle(10, 10, p.getHealth(), 50);

		c.addEnemy(enemyCount);
		
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		t1 = new Thread(this);
		t1.start();
	}

	private synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(1);
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		final double AMOUNT_OF_TICKS = 60.0;
		final double NS_PER_TICK = 1000000000 / AMOUNT_OF_TICKS;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime);
			lastTime = currentTime;
			if (delta >= NS_PER_TICK) {
				update();
				delta -= NS_PER_TICK;
				updates++;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}

		stop();
	}

	private void update() {
		
		if(gameStartedAgain) {
			
			gameStartedAgain = false;

			c.geteB().clear();
			c.geteA().clear();

			enemyCount = 1;
			enemyKilled = 0;
			p.setScore(0);
			wave = 1;

			c.addEnemy(enemyCount);
			
			state = STATE.GAME;

		}
		
		if(state == STATE.GAME){
			p.update();
			c.update();
			
			remHealthBar.setBounds(10, 10, p.getHealth(), 50);
			
			if(enemyKilled >= enemyCount) {
				wave++;
				enemyCount = (wave*(wave+1)) / 2;
				enemyKilled = 0;
				System.out.println(enemyCount);
				c.addEnemy(enemyCount);
			}
			
			if(invArr[0] && invArr[1]) destroyBullet = false;
			
		
		}
	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		
		if(state == STATE.GAME){
			p.render(g);
			c.render(g);
			
			g.setColor(Color.RED);
			g2.fill(totalHealthBar);
			g.setColor(Color.GREEN);
			g2.fill(remHealthBar);
			g.setColor(Color.white);
			g2.draw(totalHealthBar);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(("Score:" + p.getScore()), WIDTH*SCALE- 150, 50);
			g.drawString(("Wave " + wave), WIDTH*SCALE- 350, 50);
		}
		
		else if(state == STATE.MENU){
			menu.render(g);
		}
		
		else if(state == STATE.GAME_OVER) {
			gameOver.render(g);
		}
		g.dispose();
		g2.dispose();
		bs.show();
	}

	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(state == STATE.GAME){
			if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				p.setVelRX(10);
			}
			
			if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				p.setVelLX(10);
			}
	
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				p.setVelUY(10);
			}
	
			if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				p.setVelDY(10);
			}
			
			if(key == KeyEvent.VK_SPACE && !isShooting) {
				c.addEntity(new Bullet(p.getX(), p.getY(), tx, this, c));
				p.setScore(p.getScore() - 1);
				isShooting = true;
			}
			
			
			if(key == KeyEvent.VK_CONTROL) invArr[0] = true;
			
			if(key == KeyEvent.VK_5) invArr[1] = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			p.setVelRX(0);
		}
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			p.setVelLX(0);
		}

		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			p.setVelUY(0);
		}

		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			p.setVelDY(0);
		}
		
		if(key == KeyEvent.VK_SPACE) {
			isShooting = false;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(!isShooting){
			c.addEntity(new Bullet(p.getX(), p.getY(), tx, this, c));
			p.setScore(p.getScore() - 1);
		}
		
	}
	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame f = new JFrame(game.TITLE);
		f.add(game);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		game.start();
	
		}
	

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	
	public GameOver getGameOver() {
		return gameOver;
	}

	public void setGameOver(GameOver gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameStartedAgain() {
		return gameStartedAgain;
	}

	public void setGameStartedAgain(boolean gameStartedAgain) {
		this.gameStartedAgain = gameStartedAgain;
	}

	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public Player getP() {
		return p;
	}

	public Controller getC() {
		return c;
	}

	public void setC(Controller c) {
		this.c = c;
	}

	public boolean isDestroyBullet() {
		return destroyBullet;
	}

	public void setDestroyBullet(boolean destroyBullet) {
		this.destroyBullet = destroyBullet;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

}
