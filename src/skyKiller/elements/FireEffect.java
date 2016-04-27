package skyKiller.elements;

import skyKiller.events.EraseFireEffectEvent;
import skyKiller.events.MyEventMessage;
import skyKiller.forms.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ahmad on 4/18/16.
 */
public class FireEffect extends JPanel implements Runnable {
	private final int FIRE_EFFECT_LIFE_TIME = 500;
	private final int FIRE_EFFECT_WIDTH = 80;
	private final int FIRE_EFFECT_HEIGHT = 80;
	private Image image;
	private Game game;

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.drawImage(image, 0, 0,40,40, this);
	}

	public FireEffect(Game game,int x, int y){
		x -= 40;
		y -= 40;
		this.game = game;
		setSize(FIRE_EFFECT_WIDTH,FIRE_EFFECT_HEIGHT);
		setLocation(x,y);
		image =  Toolkit.getDefaultToolkit().createImage("explosion.gif");
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(FIRE_EFFECT_LIFE_TIME);
		} catch (InterruptedException e) {
			System.err.println("Thread exception has occurred.");
			System.exit(-1);
		}
		game.dispatchEvent(new EraseFireEffectEvent(this, MyEventMessage.DO_ERASE_FIRE_EFFECT));
	}
}
