package skyKiller.elements;

import skyKiller.events.ExplodeBombEvent;
import skyKiller.events.MyEventMessage;
import skyKiller.forms.Game;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;

/**
 * Created by ahmad on 4/16/16.
 */
public class Bomb extends Component implements Runnable {
	private int x;
	private int y;
	private int dy;
	private int r;
	private final int DELAY_TIME = 40;
	private Game game;

	public Bomb(int x,int y, Game game){
		this.game = game;
		this.x = x;
		this.y = y;
		dy = 10;
		r = 15;
	}
	@Override
	public void run() {
		while (y < game.DEFAULT_HEIGHT){
			y += dy;
			if (game.isCollision(get‌Boundry())){
				game.dispatchEvent(new ExplodeBombEvent(this, MyEventMessage.DO_EXPLODE_BOMB,x,y));
			}
			if (game.towerAttack(this)){
				game.dispatchEvent(new ExplodeBombEvent(this,MyEventMessage.DO_EXPLODE_BOMB,x,y));
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

	public int getR(){
		return r;
	}

	@Override
	public String toString() {
		return (new Integer(x)).toString() + " " + (new Integer(y)).toString();
	}

	public Rectangle2D get‌Boundry(){
		Rectangle2D res = new Rectangle2D.Double(x,y,r,r);
		return res;
	}
}
