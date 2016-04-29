package skyKiller.forms;

import skyKiller.elements.*;
import skyKiller.events.*;
import skyKiller.threads.JetSender;
import skyKiller.threads.Repainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ahmad on 4/16/16.
 */
public class Game extends Form {
	private BufferedImage shooterBaseImage;
	private BufferedImage jetEnemieImage;
	private int shooterBaseImageX;
	private int shooterBaseImageY;
	private double mousePositionX;
	private double mousePositionY;
	private int shooterTubeLen;
	private double shooterTubeX;
	private double shooterTubeY;
	private double shooterAngle;
	private double dAlpha;
	private ArrayList<JetEnemie> enemies;
	private ArrayList<Bomb> bombs;
	private ArrayList<Bullet> bullets;
	private ArrayList<Tower> towers;
	private boolean isLeftHanded;
	private Line2D lin;
	private int level;
	private ScoreCounter scoreCounter;
	private int homeAttacked;
	private String playerName;
	private JetSender jetSender;

	public Game(boolean isLeftHanded,int level,String playerName){
		super();
		homeAttacked = 0;
		this.level = level;
		shooterTubeLen = 50;
		this.isLeftHanded = isLeftHanded;
		setLayout(null);
		setBackground(Color.WHITE);
		initElement();
		assignListener();
		putTowers();
		setBackground(Color.BLUE);
		(new Repainter(this)).start();
		putInCenter();
		this.playerName = playerName;
		shooterAngle = Math.PI / 2;
		rotateShooterTube();
	}

	public int getLevel(){
		return level;
	}
	private void initElement(){
		mousePositionX = 0;
		shooterAngle = Math.PI / 2;
		dAlpha = Math.PI / 30;

		double shooterAngle = Math.acos((mousePositionX - (DEFAULT_WIDTH / 2.0)) / (DEFAULT_WIDTH / 2.0));
		shooterTubeX = shooterTubeLen * Math.cos(shooterAngle);
		shooterTubeY = shooterTubeLen * Math.sin(shooterAngle);
		lin = new Line2D.Double(DEFAULT_WIDTH / 2, shooterBaseImageY, shooterTubeX, shooterTubeY);
		try {
			shooterBaseImage = ImageIO.read(new File("shooter.png"));
			jetEnemieImage = ImageIO.read(new File("jet.png"));
		} catch (IOException e) {
			System.err.println("File not found");
			System.exit(0);
		}
		enemies = new ArrayList<>();
		bombs = new ArrayList<>();
		bullets = new ArrayList<>();
		towers = new ArrayList<>();

		jetSender = new JetSender(this,level);
		jetSender.start();

		shooterBaseImageX = (super.DEFAULT_WIDTH - shooterBaseImage.getWidth()) / 2;
		shooterBaseImageY = super.DEFAULT_HEIGHT - shooterBaseImage.getHeight();
		scoreCounter = new ScoreCounter(this);
		add(scoreCounter);
	}
	public synchronized void addEnimie(int y){
		JetEnemie en = new JetEnemie(y,this);
		(new Thread(en)).start();
		enemies.add(en);
	}
	private void putTowers(){
		Tower tower;
		for (int i = 0; i < DEFAULT_WIDTH / Tower.TOWER_WIDTH; i++) {
			tower = new Tower(i * Tower.TOWER_WIDTH,this);
			towers.add(tower);
			add(tower);
		}
	}
	private void assignListener(){
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
					shooterAngle += dAlpha;
				if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
					shooterAngle -= dAlpha;
				if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
					fireShooterTube();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
					promptForExit();

				rotateShooterTube();
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {

			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent mouseEvent) {
				mousePositionX = DEFAULT_WIDTH / 2.0 - mouseEvent.getX();
				mousePositionY = shooterBaseImageY - mouseEvent.getY();
				shooterAngle= Math.acos(mousePositionX / (DEFAULT_WIDTH / 2.0));
				rotateShooterTube();
				repaint();
				if (mouseEvent.getModifiers() == 16 && (isLeftHanded))
					fireShooterTube();
				else if (mouseEvent.getModifiers() == 4 && (!isLeftHanded))
					fireShooterTube();
			}

			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
				mousePositionX = DEFAULT_WIDTH / 2.0 - mouseEvent.getX();
				mousePositionY = shooterBaseImageY - mouseEvent.getY();
				shooterAngle= Math.acos(mousePositionX / (DEFAULT_WIDTH / 2.0));
				rotateShooterTube();
				repaint();
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == 1 && (isLeftHanded))
					fireShooterTube();
				else if (mouseEvent.getButton() == 3 && (!isLeftHanded))
					fireShooterTube();
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
	}


	public synchronized boolean towerAttack(Bomb bomb){
		for (int i = 0; i < towers.size(); i++) {
			if (towers.get(i).isTowerDown()) {
				continue;
			}
			if(isCollied(towers.get(i).getBoundry(),bomb.get‌Boundry())) {
				homeAttacked++;
				towers.get(i).attack();
				bombs.remove(bomb);
				add(new FireEffect(this,bomb.getX(),bomb.getY()));
				return true;
			}
		}
		if (homeAttacked == 4 * towers.size()) {
			JOptionPane.showMessageDialog(this, playerName + "You loose :(");
			System.exit(0);
		}
		return false;
	}
	private synchronized void fireShooterTube(){
		Bullet bullet = new Bullet((int)shooterTubeX,(int)shooterTubeY,Math.cos(shooterAngle),Math.sin(shooterAngle),this);
		(new Thread(bullet)).start();
		bullets.add(bullet);
	}

	private void rotateShooterTube(){
		if (shooterAngle < Math.PI / 6)
			shooterAngle = Math.PI / 6;
		else if (shooterAngle > 5 * Math.PI / 6)
			shooterAngle = 5 * Math.PI / 6;
		shooterTubeX = shooterTubeLen * Math.cos(shooterAngle);
		shooterTubeY = shooterTubeLen * Math.sin(shooterAngle);
		shooterTubeX = DEFAULT_WIDTH / 2 - shooterTubeX;
		shooterTubeY = shooterBaseImageY - shooterTubeY;
		lin = new Line2D.Double(DEFAULT_WIDTH / 2, shooterBaseImageY, shooterTubeX, shooterTubeY);
	}
	public synchronized void fireBomb(int x, int y){
		Bomb b = new Bomb(x,y,this);
		(new Thread(b)).start();
		bombs.add(b);
	}

	private synchronized boolean isCollied(Rectangle2D border1, Rectangle2D border2){
		return border1.intersects(border2);
//
//		if (((border1.getX() > border2.getX()) && (border1.getX() < border2.getX() + border2.getWidth())) &&
//				((border1.getY() > border2.getY()) && (border1.getY() < border2.getY() + border2.getHeight())))
//			return true;
//		if (((border1.getX() + border1.getWidth() > border2.getX()) && (border1.getX() + border1.getWidth() < border2.getX() + border2.getWidth())) &&
//				((border1.getY() > border2.getY()) && (border1.getY() < border2.getY() + border2.getHeight())))
//			return true;
//		if (((border1.getX() > border2.getX()) && (border1.getX() < border2.getX() + border2.getWidth())) &&
//				((border1.getY() + border1.getHeight() > border2.getY()) && (border1.getY() + border1.getHeight() < border2.getY() + border2.getHeight())))
//			return true;
//		if (((border1.getX() + border1.getWidth() > border2.getX()) && (border1.getX() + border1.getWidth() < border2.getX() + border2.getWidth())) &&
//				((border1.getY() + border1.getHeight() > border2.getY()) && (border1.getY() + border1.getHeight() < border2.getY() + border2.getHeight())))
//			return true;
//
//		return false;
	}
	public synchronized boolean isCollision(Rectangle2D border){
		int bulletsSize = bullets.size();
		boolean res = false;
		for (int i = 0; i < bulletsSize; i++) {
			res |= isCollied(bullets.get(i).get‌Boundry(),border);
		}

		return res;
	}
	@Override
	public void paint(Graphics graphics) {
		super.paintComponents(graphics);
		//fireEffect.paintImmediately(fireEffect.getX(),fireEffect.getY(),fireEffect.getWidth(),fireEffect.getHeight());

		graphics.drawImage(shooterBaseImage, shooterBaseImageX, shooterBaseImageY, this);
		//for (JetEnemie enemie: enemies) {
		for (int j = 0; j < enemies.size(); j++) {
			graphics.drawImage(jetEnemieImage,enemies.get(j).getX(),enemies.get(j).getY(),this);
		}

		Graphics2D g2 = (Graphics2D) graphics;
		for (int i = 0; i < bombs.size(); i++) {
			g2.fill((new Ellipse2D.Double(bombs.get(i).getX(),bombs.get(i).getY(),bombs.get(i).getR(),bombs.get(i).getR())));
		}
		for (int i = 0; i < bullets.size(); i++) {
			g2.fill((new Ellipse2D.Double(bullets.get(i).getX(),bullets.get(i).getY(),bullets.get(i).getR(),bullets.get(i).getR())));
		}
		g2.draw(lin);

	}

	public void nextLevel(){
		//JOptionPane.showMessageDialog(null,"Level Up :)");
		jetSender.changeLevel(++level);
	}

	@Override
	protected synchronized void processComponentEvent(ComponentEvent componentEvent) {
		if (componentEvent.getID() == MyEventMessage.DO_EXPLODE_JET) {
			scoreCounter.attackJet();
			enemies.remove(((ExplodeJetEvent) componentEvent).getSource());
			add(new FireEffect(this,((ExplodeJetEvent) componentEvent).getX(),((ExplodeJetEvent) componentEvent).getY()));
		}
		if (componentEvent.getID() == MyEventMessage.DO_ERASE_BULLET)
			bullets.remove(((EraseBulletEvent) componentEvent).getSource());
		if (componentEvent.getID() == MyEventMessage.DO_EXPLODE_BOMB) {
			scoreCounter.attackBomb();
			bombs.remove(((ExplodeBombEvent) componentEvent).getSource());
			add(new FireEffect(this,((ExplodeBombEvent) componentEvent).getX() - 30,((ExplodeBombEvent) componentEvent).getY() - 30));
		}
		if (componentEvent.getID() == MyEventMessage.DO_ERASE_FIRE_EFFECT)
			remove(((Component) ((EraseFireEffectEvent)componentEvent).getSource()));
	}

	public int getShooterBaseImageHeight(){
		return shooterBaseImage.getHeight();
	}

	public int getShooterBaseImageWidth(){
		return shooterBaseImage.getWidth();
	}
}
