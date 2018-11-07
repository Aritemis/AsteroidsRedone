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
import model_enum.BulletType;
import model_enum.ShipType;
import model_enum.StarType;
import model_objects.Asteroid;
import model_objects.Bullet;
import model_objects.Ship;
import model_objects.Star;
import view.Animation;
import view.AsteroidsFrame;
import view.AsteroidsPanel;
import view.ViewPanel;
import view_images.Images;

@SuppressWarnings("serial")
public class AsteroidsGame extends AsteroidsPanel implements Animation
{
	protected AsteroidsControl base;
	protected SpringLayout theLayout;
	protected Ship ship;
	protected ArrayList<Asteroid> asteroidList;
	protected ArrayList<Star> stars;
	protected int collideCount;
	protected int lives;
	protected boolean invincible;
	protected Timer repaintTimer;
	protected Timer updateTimer;
	protected ActionListener repainter;
	protected ActionListener updater;
	protected AsteroidsFrame frame;
	protected Point shipPosition;
	protected ShipType shipType;
	protected BulletType bullets;
	
	protected boolean recurringAsteroids;
	protected boolean collide;
	protected int score;
	protected final int baseScore;
	protected final int timerCount;

	public AsteroidsGame(AsteroidsControl base, ShipType shipType, BulletType bullets, boolean recurringAsteroids)
	{
		this.base = base;
		frame = base.getFrame();
		theLayout = new SpringLayout();
		score = 0;
		baseScore = 5;
		timerCount = AsteroidsControl.repaintTime;
		asteroidList = new ArrayList<Asteroid>();
		stars = Star.createDefaultStars();
		this.setFocusable(true);
		this.requestFocus();
		shipPosition = new Point((AsteroidsControl.screenHeight / 2) + AsteroidsControl.screenBoundaryLeft, AsteroidsControl.screenHeight / 2);
		ship = new Ship(shipPosition, shipType, bullets);
		this.addKeyListener(ship);
		this.shipType = shipType;
		this.bullets = bullets;
		this.recurringAsteroids = recurringAsteroids;
		
		setUpLayout();
		setUpTimers();
		startTimers();
	}
	
	protected void setUpLayout()
	{
		this.setLayout(theLayout);
		setBackground(Color.BLACK);
	}
	
	protected void setUpTimers()
	{
		repainter = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				repaint();
			}
		};
		repaintTimer = new Timer(AsteroidsControl.repaintTime, repainter);
		repaintTimer.setRepeats(true);
		
		updater = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				refresh();
			}
		};
		updateTimer = new Timer(AsteroidsControl.updateTime, updater);
		updateTimer.setRepeats(true);
	}
	
	public void startTimers()
	{
		repaintTimer.start();
		updateTimer.start();
	}

	public void stopTimers()
	{
		repaintTimer.stop();
		updateTimer.stop();
	}
	
	protected void refresh()
	{
		
	}
	
	protected void update()
	{
		updateCollide();
		moveStars();
		moveAsteroidsAndBullets();
		ship.move();
		ship.recharge();
	}
	
	protected void updateCollide()
	{
		if(collide)
		{
			collideCount--;
			if(collideCount == 0)
			{
				collide = false;
				invincible = false;
			}
		}
	}
	
	protected void moveStars()
	{
		for (Star star : stars)
		{
			star.move(ship.getRotation());
		}
	}
	
	protected void moveAsteroidsAndBullets()
	{
		List<Bullet> shots = ship.getBullets();
		List<Bullet> removeList = new ArrayList<Bullet>();
		List<Asteroid> splodePlz = new ArrayList<Asteroid>();
		List<Asteroid> outOfBounds = new ArrayList<Asteroid>();
		for (Asteroid asteroid : asteroidList)
		{
			asteroid.move();
			if(recurringAsteroids)
			{
				asteroid.moveInBounds();
			}
			else
			{
				if(asteroid.outOfBounds())
				{
					outOfBounds.add(asteroid);
				}
			}
			
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
					if(asteroid.hit(bullets.damage) < 1)
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
		
		for(Asteroid asteroid : outOfBounds)
		{
			asteroidList.remove(asteroid);
		}
		
		for(Bullet shot: shots)
		{
			shot.move();
			if(shot.outOfBounds())
			{
				removeList.add(shot);
			}
		}
		
		for(Bullet shot : removeList)
		{
			shots.remove(shot);
		}
	}
	
	protected void paintStars(Graphics brush)
	{
		for (Star star : stars)
		{
			star.paint(brush);
		}
	}
	
	protected void paintAsteroidsAndBullets(Graphics brush)
	{
		List<Bullet> shots = ship.getBullets();
		for (Asteroid asteroid : asteroidList)
		{
			asteroid.paint(brush);
		}
		for(Bullet shot: shots)
		{
			shot.paint(brush);
		}
	}
	
	protected void paintShip(Graphics brush)
	{
		ship.paint(brush);
	}
	
}
