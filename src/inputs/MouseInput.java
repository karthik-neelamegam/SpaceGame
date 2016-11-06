package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.Game.STATE;
import main.GameOver;
import main.Menu;

public class MouseInput implements MouseListener, MouseMotionListener {
	
	
	Game game;
	Menu menu = new Menu(game);
	GameOver gameOver = new GameOver(game);
	
	public MouseInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		
		if(menu.getPlayBtn().intersects(mX, mY, 1, 1)) {
			
			game.getP().setHealth(200);
			
			if(Game.state == STATE.GAME_OVER) { 
				game.setGameStartedAgain(true);

			}
			
			else Game.state = STATE.GAME;
		}
		
		if(Game.state != STATE.GAME && menu.getQuitBtn().intersects(mX, mY, 1, 1)) {
			System.exit(1);
		}
		
				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		if(Game.state == STATE.GAME) {
			game.mousePressed(e);
		}
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		if(Game.state == STATE.MENU) {
			for(int i = 0; i < menu.getHovered().length; i++) {
				if(menu.getBtns()[i].intersects(mX, mY, 1, 1)) {
					game.getMenu().setHovered(i, true);
				}
				else game.getMenu().setHovered(i, false);
	
			}
		}
		else if(Game.state == STATE.GAME_OVER) {
			for(int i = 0; i < menu.getHovered().length; i++) {
				if(menu.getBtns()[i].intersects(mX, mY, 1, 1)) {
					game.getGameOver().setHovered(i, true);
				}
				else game.getGameOver().setHovered(i, false);
	
			}
		}
	}

	
}
