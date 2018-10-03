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

@SuppressWarnings("serial")
public class AsteroidsGame extends JPanel implements Animation
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
	protected ActionListener repainter;
	protected AsteroidsFrame frame;
	protected Point shipPosition;
	protected Color bulletColor;
	
	protected boolean collide;
	protected int score;
	protected final int baseScore;
	protected final int timerCount;

	public AsteroidsGame(AsteroidsControl base, ShipType shipType)
	{
		this.base = base;
		frame = base.getFrame();
		theLayout = new SpringLayout();
		score = 0;
		baseScore = 5;
		timerCount = AsteroidsControl.refreshTime;
		asteroidList = new ArrayList<Asteroid>();
		stars = base.createStars(new ArrayList<Star>(), 100, StarType.FAST);
		stars = base.createStars(stars, 50, StarType.STANDARD);
		stars = base.createStars(stars, 25, StarType.SLOW);
		this.setFocusable(true);
		this.requestFocus();
		shipPosition = new Point(AsteroidsControl.SCREEN_WIDTH / 2, AsteroidsControl.SCREEN_HEIGHT / 2);
		ship = new Ship(shipPosition, shipType);
		this.addKeyListener(ship);
		
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
	
	protected void paintAsteroidsAndBullets(Graphics brush, Color bulletColor)
	{
		List<Bullet> shots = ship.getBullets();
		for (Asteroid asteroid : asteroidList)
		{
			asteroid.paint(brush);
		}
		for(Bullet shot: shots)
		{
			shot.paint(brush, bulletColor);
		}
	}
	
	protected void paintShip(Graphics brush)
	{
		ship.paint(brush);
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
}
