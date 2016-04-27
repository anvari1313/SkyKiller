package skyKiller.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ahmad on 4/16/16.
 */
public class Form extends JFrame {
	public final int DEFAULT_WIDTH = 800;
	public final int DEFAULT_HEIGHT = 600;
	public final String APP_TITLE = "Sky Killer";
	private int screenHeight;
	private int screenWidth;
	public Form(){
		setTitle(APP_TITLE);
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		screenHeight = ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		screenWidth = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{
				promptForExit();
			}
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
					promptForExit();
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {

			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {

			}
		});
	}

	public void promptForExit(){
		String ObjButtons[] = {"Yes","No"};
		int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit game?",APP_TITLE,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		if(PromptResult==JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
	}
	public void showForm(){
		setVisible(true);
	}

	public void  hideForm(){
		setVisible(false);
	}

	public void putInCenter(){
		setLocation((screenWidth - getWidth())/2,(screenHeight - getHeight())/2);
	}
}
