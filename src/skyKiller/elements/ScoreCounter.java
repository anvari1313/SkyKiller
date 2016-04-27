package skyKiller.elements;

import skyKiller.forms.Form;
import skyKiller.forms.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ahmad on 4/21/16.
 */
public class ScoreCounter extends JPanel {
	private final int JET_ATTACK_SCORE = 1000;
	private final int BOMB_ATTACK_SCORE = 100;
	private JLabel label;
	private int value;
	private Game game;
	private final int LEVEL_THRESHOLD = 10000;

	public ScoreCounter(Game game){
		value = 0;
		setSize(150,50);
		setLocation(game.getWidth() - getWidth(),0);
		//setLocation(150,40);
		label = new JLabel();
		label.setForeground(Color.BLUE);
		label.setFont(new Font(label.getFont().getName(),Font.PLAIN,16));
		add(label);
		this.game = game;
		showValue();
	}
	public void attackJet(){
		value += JET_ATTACK_SCORE;
		showValue();
	}
	public void attackBomb(){
		value += BOMB_ATTACK_SCORE;
		showValue();
	}
	private void showValue(){
		if (value > LEVEL_THRESHOLD) {
			game.nextLevel();
			value = 0;
		}
		label.setText("Level : " + (new Integer(game.getLevel())).toString() + "-" + (new Integer(value)).toString());
	}
}
