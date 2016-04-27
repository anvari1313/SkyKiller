package skyKiller.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ahmad on 4/16/16.
 */
public class Menu extends Form {
	private JTextField playerNameJText;
	private JLabel playerNameJLable;
	private JPanel modeSelectingJPanel;
	private JRadioButton rightHandModeJRadio;
	private JRadioButton leftHandModeJRadio;
	private JSlider hardnessSelectingJSLider;
	private JButton playJButton;
	private JButton exitJButton;
	private ButtonGroup modeSelectingGroup;
	private boolean isLeftHanded;
	public static final int NUM_OF_LEVELS = 3;
	private String playerName;

	public Menu(){
		super();
		setSize(300,320);
		putInCenter();
		setLayout(null);
		isLeftHanded = true;
		playerNameJLable = new JLabel("Enter your name : ");
		playerNameJText = new JTextField();
		modeSelectingGroup = new ButtonGroup();
		modeSelectingJPanel = new JPanel();
		rightHandModeJRadio = new JRadioButton("Right Hand",true);
		leftHandModeJRadio = new JRadioButton("Left Hand");

		hardnessSelectingJSLider = new JSlider(JSlider.HORIZONTAL,1,NUM_OF_LEVELS,NUM_OF_LEVELS / 2);
		playJButton = new JButton("Play");
		exitJButton = new JButton("Exit");
		putUIElements();
		assigingListener();
	}

	private void putUIElements(){
		playerNameJLable.setLocation(10,30);
		playerNameJLable.setSize(new Dimension(160,30));
		add(playerNameJLable);
		add(playerNameJText);
		playerNameJText.addFocusListener(new FocusEffector());
		playerNameJText.setLocation(170,30);
		playerNameJText.setSize(new Dimension(120,30));
		rightHandModeJRadio.addFocusListener(new FocusEffector());
		rightHandModeJRadio.addMouseListener(new HoverEffector());
		modeSelectingGroup.add(rightHandModeJRadio);
		leftHandModeJRadio.addFocusListener(new FocusEffector());
		leftHandModeJRadio.addMouseListener(new HoverEffector());
		modeSelectingGroup.add(leftHandModeJRadio);
		modeSelectingJPanel.add(rightHandModeJRadio);
		modeSelectingJPanel.add(leftHandModeJRadio);
		modeSelectingJPanel.setSize(new Dimension(100,80));
		modeSelectingJPanel.setLocation((getWidth() - modeSelectingJPanel.getWidth())/2,70);
		add(modeSelectingJPanel);
		JLabel easeyJLabel = new JLabel("Easy");
		JLabel mediumJLabel = new JLabel("Medium");
		JLabel hardJLabel = new JLabel("Hard");
		easeyJLabel.setSize(new Dimension(50,30));
		mediumJLabel.setSize(new Dimension(60,30));
		hardJLabel.setSize(new Dimension(40,30));
		easeyJLabel.setLocation(0,getHeight()/2-15);
		mediumJLabel.setLocation((getWidth() - mediumJLabel.getWidth())/2,getHeight()/2-15);
		hardJLabel.setLocation(getWidth() - hardJLabel.getWidth(),getHeight()/2-15);
		add(easeyJLabel);
		add(mediumJLabel);
		add(hardJLabel);
		add(hardnessSelectingJSLider);
		hardnessSelectingJSLider.setLocation(0,getHeight()/2+10);
		hardnessSelectingJSLider.setSize(new Dimension(getWidth(),50));
		playJButton.addFocusListener(new FocusEffector());
		playJButton.addMouseListener(new HoverEffector());
		add(playJButton);
		playJButton.setSize(new Dimension(getWidth() / 2 ,50));
		exitJButton.addFocusListener(new FocusEffector());
		exitJButton.addMouseListener(new HoverEffector());
		add(exitJButton);
		exitJButton.setSize(new Dimension(getWidth() / 2 ,50));
		playJButton.setLocation(0,getHeight() - playJButton.getHeight());
		exitJButton.setLocation(getWidth() /2,getHeight() - exitJButton.getHeight());
	}

	private void assigingListener(){
		exitJButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				promptForExit();
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});

		playJButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if ("".equals(playerNameJText.getText())) {
					JOptionPane.showMessageDialog(null,
							"Don't forget to enter your name !!", APP_TITLE,
							JOptionPane.WARNING_MESSAGE);
					playerNameJText.requestFocus();
				}else{
					startGame();
				}
			}

			@Override
			public void mousePressed(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseEvent) {

			}
		});
		playJButton.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
					if ("".equals(playerNameJText.getText())) {
						JOptionPane.showMessageDialog(null,
								"Don't forget to enter your name !!", APP_TITLE,
								JOptionPane.WARNING_MESSAGE);
						playerNameJText.requestFocus();
					}else{
						startGame();
					}
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {

			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {

			}
		});

		leftHandModeJRadio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == 1)
					isLeftHanded = true;
			}
		});

		rightHandModeJRadio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == 1)
					isLeftHanded = false;
			}
		});
	}

	private void startGame(){
		this.hideForm();
		Game game = new Game(isLeftHanded,hardnessSelectingJSLider.getValue(),playerName);
		game.showForm();
	}
}
class FocusEffector implements java.awt.event.FocusListener{

	@Override
	public void focusGained(FocusEvent focusEvent) {
		((Component) focusEvent.getSource()).setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void focusLost(FocusEvent focusEvent) {
		((Component) focusEvent.getSource()).setBackground(Color.WHITE);
	}
}

class HoverEffector implements  MouseListener{

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
		((Component) mouseEvent.getSource()).setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		((Component) mouseEvent.getSource()).setBackground(Color.WHITE);
	}
}