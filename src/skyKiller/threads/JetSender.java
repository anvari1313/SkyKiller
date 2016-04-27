package skyKiller.threads;

import skyKiller.forms.Game;
import skyKiller.forms.Menu;

import javax.swing.*;

/**
 * Created by ahmad on 4/21/16.
 */
public class JetSender extends Thread {
	private Game game;
	private int level;
	private final int JET_SENDING_INTERVAL = 300;
	private final double JET_SENDING_THRESHOLD_BALANCER = 0.2;
	public JetSender(Game game, int level){
		this.game = game;
		this.level = level;
	}
	@Override
	public void run() {
		while (true){
			double rnd = Math.random();
			double threshold = level / ((double)(Menu.NUM_OF_LEVELS)) - JET_SENDING_THRESHOLD_BALANCER;
			if (rnd < threshold) {
				int jetYPosition = (int)((Math.random() * 100) + 50);
				game.addEnimie(jetYPosition);
			}
			try {
				Thread.sleep(JET_SENDING_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void changeLevel(int level){
		if (level > Menu.NUM_OF_LEVELS){
			JOptionPane.showMessageDialog(null,"YOU WINNNNNNNNNNNNNN");
		}else
			this.level = level;
	}
}
