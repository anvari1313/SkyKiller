package skyKiller.elements;

import skyKiller.forms.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ahmad on 4/16/16.
 */
public class Tower extends JPanel {
	private int x;
	private int y;

	private int numOfHomes = 4;
	private final int TOWER_HEIGHT = 70;
	public static final int TOWER_WIDTH = 30;
	private BufferedImage []homeImages;

	public Tower(int x, Game game){
		homeImages = new BufferedImage[numOfHomes];
		setSize(TOWER_WIDTH,TOWER_HEIGHT);
		this.x = x;
		this.y = game.DEFAULT_HEIGHT - getHeight() - 20;
		setLocation(x,y);
		try {
			for (int i = 0; i < numOfHomes; i++) {
				homeImages[i] = ImageIO.read(new File("city.png"));
			}
		} catch (IOException e) {
			System.err.println("Image not found");
			System.exit(0);
		}
	}

	public void attack(){
		if (numOfHomes != 0) {
			numOfHomes--;
			repaint();
		}
	}

	public boolean isTowerDown(){
		if (numOfHomes == 0)
			return true;
		else
			return false;
	}
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (int i = 0; i < numOfHomes; i++) {
			graphics.drawImage(homeImages[i],0,TOWER_HEIGHT - homeImages[i].getHeight() + (-15 * i)   ,this);
		}
	}

	public Rectangle2D getBoundry(){
		Rectangle2D res = new Rectangle2D.Double(x,y,TOWER_WIDTH,TOWER_HEIGHT);
		return res;
	}
}
