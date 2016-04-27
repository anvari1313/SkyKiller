package skyKiller.elements;

import skyKiller.events.EraseBulletEvent;
import skyKiller.events.MyEventMessage;
import skyKiller.forms.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by ahmad on 4/16/16.
 */
public class Bullet extends Component implements Runnable {
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int r;
	private int bulletAcceleration;
	private Game game;

	private final int DELAY_TIME = 30;
	public Bullet(int x, int y, double dx, double dy, Game game){
		this.x = x;
		this.y = y;
		this.r = 15;
		this.bulletAcceleration = 10;
		this.dx = (int)(dx * bulletAcceleration);
		this.dy = (int)(dy * bulletAcceleration);
		this.game = game;
	}
	@Override
	public void run() {

		while (y > 0 && x > 0){
			y -= dy;
			x -= dx;

			try {
				Thread.sleep(DELAY_TIME);
			} catch (InterruptedException e) {
				System.err.println("Exception in thread has been occured.");
				System.exit(-1);
			}
			game.repaint();
		}
		game.dispatchEvent(new EraseBulletEvent(this, MyEventMessage.DO_ERASE_BULLET));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getR(){return r;}

	public Rectangle2D get‌Boundry(){
		Rectangle2D res = new Rectangle2D.Double(x,y,r,r);
		return res;
	}
}
