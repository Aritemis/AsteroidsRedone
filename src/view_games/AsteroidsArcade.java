/**
 *	@author Ariana Fairbanks
 */

package view_games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import control.AsteroidsControl;
import model_abstracts.Point;
import model_enum.StarType;
import model_enum.States;
import model_game.Asteroid;
import model_game.Bullet;
import model_game.Ship;
import model_game.Star;
import view.Animation;
import view.AsteroidsFrame;
import view_images.Images;

public class AsteroidsArcade extends JPanel implements Animation
{
	private static final long serialVersionUID = 4338632920192192267L;
	private AsteroidsControl base;
	private SpringLayout theLayout;
	private Ship ship;
	private ArrayList<Asteroid> asteroidList;
	private ArrayList<Star> stars;
	private int collideCount;
	private int lives;
	private boolean invincible;
	private Timer repaintTimer;
	private ActionListener repainter;
	private AsteroidsFrame frame;
	private Point shipPosition;
	
	private boolean collide;
	private int level;
	private int score;
	private final int baseScore;

	public AsteroidsArcade(AsteroidsControl base)
	{
		this.base = base;
		frame = base.getFrame();
		theLayout = new SpringLayout();
		this.setFocusable(true);
		this.requestFocus();
		shipPosition = new Point(400,300);
		ship = new Ship(shipPosition, 270);
		this.addKeyListener(ship);
		level = 0;
		score = 0;
		baseScore = 5;
		asteroidList = new ArrayList<Asteroid>();
		resetGame();
		
		
		setUpLayout();
		setUpListeners();
		setUpTimers();
		startTimers();
	}
	
	private void setUpLayout()
	{
		this.setLayout(theLayout);
		setBackground(Color.BLACK);
	}
	
	private void setUpListeners()
	{

	}

	private void setUpTimers()
	{
		repainter = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				repaint();
			}
		};
		repaintTimer = new Timer(12, repainter);
		repaintTimer.setRepeats(true);
	}
	
	public void startTimers()
	{
		repaintTimer.start();
	}

	public void stopTimers()
	{
		repaintTimer.stop();
	}
	
	private void resetGame()
	{
		ship.resetShip();
		ship.setPosition(shipPosition);
		level++;
		asteroidList = base.generateAsteroids(asteroidList, level, baseScore);
		stars = base.createStars(new ArrayList<Star>(), 100, StarType.FAST);
		stars = base.createStars(stars, 50, StarType.STANDARD);
		stars = base.createStars(stars, 25, StarType.SLOW);
		collideCount = 0;
		lives = 3;
		invincible = false;
		AsteroidsControl.limbo = false;
		AsteroidsControl.reset = false;
		AsteroidsControl.paused = false;
		collide = false;
	}
	
	private void paintStars(Graphics brush)
	{
		for (Star star : stars)
		{
			star.paint(brush, ship.getRotation());
		}
	}
	
	private void paintAsteroidsAndBullets(Graphics brush, Color shipColor)
	{
		List<Bullet> shots = ship.getBullets();
		List<Bullet> removeList = new ArrayList<Bullet>();
		List<Asteroid> splodePlz = new ArrayList<Asteroid>();
		brush.setColor(Color.gray);
		for (Asteroid asteroid : asteroidList)
		{
			asteroid.move();
			if(asteroid.collision(ship))
			{
				collideCount = 40;
				if(!invincible)
				{
					lives--;
				}
				collide = true;
				invincible = true;
			}
			asteroid.paint(brush, Color.white);
			
			for(Bullet shot : shots)
			{
				if(asteroid.contains(shot.getCenter()))
				{
					if(asteroid.hit() < 1)
					{
						splodePlz.add(asteroid);
					}
					removeList.add(shot);
				}
			}
		}
		
		for(Asteroid asteroid : splodePlz)
		{
			asteroidList.remove(asteroid);
			score += asteroid.getScore();
		}
		
		for(Bullet shot: shots)
		{
			shot.move();
			if(shot.outOfBounds())
			{
				removeList.add(shot);
			}
			shot.paint(brush, shipColor);
		}
		
		for(Bullet shot: removeList)
		{
			shots.remove(shot);
		}
		for(Bullet shot : removeList)
		{
			shots.remove(shot);
		}
	}

	private void paintWords(Graphics brush)
	{
		brush.setColor(Color.green);
		brush.drawString("Level: " + level, 25, 50);
		brush.drawString("Score: " + score, 25, 75);
		if(lives < 2)
		{
			brush.setColor(Color.red);
		}
		brush.drawString("Lives Left: " + lives, 25, 25);
	}
	
	private void paintShip(Graphics brush, Color shipColor)
	{
		if(!collide)
		{
			ship.paint(brush, shipColor);
		}
		else
		{
			ship.paint(brush, shipColor);
			collideCount--;
			if(collideCount == 0)
			{
				collide = false;
				invincible = false;
			}
		}
		ship.move();
	}
	
	@Override
	public void paint(Graphics brush) 
	{
		this.requestFocus();
		if(AsteroidsControl.limbo)
		{
			if(AsteroidsControl.reset){	resetGame(); }
		}
		else if(AsteroidsControl.menu)
		{
			stopTimers();
			AsteroidsControl.paused = true;
			int dialogResult = base.confirmationMessage("Your score is " + score + ". Are you sure you would like to give up and return to the menu?", "Exit game?");
			if(dialogResult == JOptionPane.OK_OPTION)
			{
				base.changeState(States.MENU);
			}
			else
			{
				startTimers();
				AsteroidsControl.paused = true;
			}
		}
		else if(AsteroidsControl.paused)
		{
			brush.drawImage(Images.PAUSE.image , 200, 200, frame);
			brush.drawImage(Images.PROCEED.image , 215, 508, frame);
		}
		else
		{
			if(asteroidList.isEmpty())
			{
				brush.drawImage(Images.WIN.image , 200, 200, frame);
				brush.drawImage(Images.PROCEED.image , 215, 508, frame);
				AsteroidsControl.limbo = true;
			}
			else if(lives < 1)
			{
				brush.setColor(Color.red);
				brush.drawString("Lives Left: " + lives, 25, 25);
				brush.drawImage(Images.LOSE.image , 200, 200, frame);
				brush.drawImage(Images.PROCEED.image , 215, 508, frame);
				AsteroidsControl.limbo = true;
			}
			else
			{
				super.paint(brush);
				
				Color shipColor = ship.rainbow();
				if(collide)
				{
					shipColor = ship.danger();
				}
				
				paintStars(brush);
				paintAsteroidsAndBullets(brush, shipColor);
				paintWords(brush);
				paintShip(brush, shipColor);
			}
		}
	}
	
	
}
