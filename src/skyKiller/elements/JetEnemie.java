package skyKiller.elements;

import skyKiller.events.ExplodeJetEvent;
import skyKiller.events.MyEventMessage;
import skyKiller.forms.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by ahmad on 4/16/16.
 */
public class JetEnemie extends Component implements Runnable {
	private int x;
	private int y;
	private int dx;
	private final int DELAY_TIME = 30;
	private BufferedImage jetEnemieImage;
	private Game game;
	private boolean isRunninged;
	private boolean isExploded;

	public JetEnemie(int y, Game game){
		this.game = game;
		this.y = y;
		x = 0;
		dx = 2;

	}

	@Override
	public void run() {

		while (x < game.DEFAULT_WIDTH + 80){
			x += dx;
			if (Math.random() > 0.99)
				game.fireBomb(x,y);

			if (game.isCollision(get‌Boundry())){
				game.dispatchEvent(new ExplodeJetEvent(this, MyEventMessage.DO_EXPLODE_JET,x,y));
				break;
			}
			try {
				Thread.sleep(DELAY_TIME);
			} catch (InterruptedException e) {
				System.err.println("Exception in thread has been occured.");
				System.exit(-1);
			}
			game.repaint();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle2D get‌Boundry(){
		Rectangle2D res = new Rectangle2D.Double(x,y,game.getShooterBaseImageWidth(),game.getShooterBaseImageHeight());
		return res;
	}
}
