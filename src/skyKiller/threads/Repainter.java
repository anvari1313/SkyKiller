package skyKiller.threads;

import skyKiller.forms.Game;

/**
 * Created by ahmad on 4/17/16.
 */
public class Repainter extends Thread {
	private Game game;
	public Repainter(Game game){
		this.game = game;
	}
	@Override
	public void run() {
//		while (true) {
//			game.repaint();
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
}
