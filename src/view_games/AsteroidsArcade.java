/**
 *	@author Ariana Fairbanks
 */

package view_games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import control.AsteroidsControl;
import model_abstracts.Point;
import model_enum.ShipType;
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
	private Color shipColor;
	
	private boolean collide;
	private boolean clearedLevel;
	private int level;
	private int score;
	private final int baseScore;
	private final int timerCount;

	public AsteroidsArcade(AsteroidsControl base)
	{
		this.base = base;
		frame = base.getFrame();
		theLayout = new SpringLayout();
		level = 0;
		score = 0;
		baseScore = 5;
		timerCount = 10;
		clearedLevel = false;
		asteroidList = new ArrayList<Asteroid>();
		this.setFocusable(true);
		this.requestFocus();
		shipPosition = new Point(AsteroidsControl.SCREEN_WIDTH / 2, AsteroidsControl.SCREEN_HEIGHT / 2);
		ship = new Ship(shipPosition, 270, ShipType.STANDARD);
		this.addKeyListener(ship);
		setUpLevel();
		
		
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
		repaintTimer = new Timer(timerCount, repainter);
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
	
	private void setUpLevel()
	{
		ship.resetShip();
		ship.setPosition(shipPosition);
		if(clearedLevel)
		{
			level++;
		}
		else
		{
			level = 1;
			score = 0;
			lives = 3;
		}
		asteroidList = base.generateAsteroids(asteroidList, level, baseScore);
		stars = base.createStars(new ArrayList<Star>(), 100, StarType.FAST);
		stars = base.createStars(stars, 50, StarType.STANDARD);
		stars = base.createStars(stars, 25, StarType.SLOW);
		collideCount = 0;
		invincible = false;
		AsteroidsControl.limbo = false;
		AsteroidsControl.reset = false;
		AsteroidsControl.paused = false;
		collide = false;
		clearedLevel = false;
	}
	
	private void paintStars(Graphics brush)
	{
		for (Star star : stars)
		{
			if(!AsteroidsControl.paused)
			{
				star.move(ship.getRotation());
			}
			star.paint(brush);
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
			if(!AsteroidsControl.paused)
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
			asteroid.paint(brush, Color.white);
		}
		
		if(!AsteroidsControl.paused)
		{
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
			
			for(Bullet shot : removeList)
			{
				shots.remove(shot);
			}
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
		if(!AsteroidsControl.paused)
		{
			ship.move();
		}
	}
	
	@Override
	public void paint(Graphics brush) 
	{
		this.requestFocus();
		if(AsteroidsControl.menu)
		{
			stopTimers();
			base.resetGameVariables();
			base.changeState(States.MENU);
		}
		else if(AsteroidsControl.limbo)
		{
			if(AsteroidsControl.reset){	setUpLevel(); }
		}
		else
		{
			if(asteroidList.isEmpty())
			{
				brush.drawImage(Images.win, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
				clearedLevel = true;
			}
			else if(lives < 1)
			{
				brush.setColor(Color.red);
				brush.drawString("Lives Left: " + lives, 25, 25);
				brush.drawImage(Images.lose, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
			}
			else
			{
				if(!AsteroidsControl.paused)
				{
					shipColor = ship.rainbow();
					if(collide)
					{
						shipColor = ship.danger();
					}
				}
				super.paint(brush);
				paintStars(brush);
				paintAsteroidsAndBullets(brush, shipColor);
				paintWords(brush);
				paintShip(brush, shipColor);
				if(AsteroidsControl.paused)
				{
					brush.drawImage(Images.pause, 200, 200, frame);
					brush.drawImage(Images.proceed, 215, 508, frame);
				}
			}
		}
	}
	
	
}
