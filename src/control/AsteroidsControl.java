/**
 *	@author Ariana Fairbanks
 */

package control;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.HighScore;
import model.States;
import model_abstracts.Point;
import model_game.Asteroid;
import model_game.Star;
import view.AsteroidsFrame;

public class AsteroidsControl
{
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	public static boolean paused;
	public static boolean limbo; //continue?
	public static boolean reset; //set up next level
	
	private AsteroidsFrame frame;
	private States state;
	private HighScore[] highScores;
	
	
	
	public void start()
	{
		limbo = false;
		reset = false;
		paused = false;
		frame = new AsteroidsFrame(this);
	}
	
	public void changeState(States state)
	{
		this.state = state;
		frame.changeViewState();
	}
	
	public Star[] createStars(int numberOfStars, int maxRadius) 
	{
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; ++i) 
		{
			Point center = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1)
			{
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}
		return stars;
	 }
	
	public List<Asteroid> createRandomAsteroids(int number, int maxWidth, int minWidth, int speed) 
	{
		List<Asteroid> asteroids = new ArrayList<>(number);

		for(int i = 0; i < number; ++i) 
		{
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxWidth);
			if(radius < minWidth)
			{
				radius += minWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0)
			{
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) 
			{
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			double randomX = Math.random() * SCREEN_WIDTH;
			double randomY = Math.random() * SCREEN_HEIGHT;
			boolean xTooNearShip = false;
			boolean yTooNearShip = false;
			if((randomX > 350) && (randomX <450)){xTooNearShip = true;}
			if((randomY > 250) && (randomY <350)){yTooNearShip = true;}
			if((xTooNearShip) && (yTooNearShip))
			{
				if(xTooNearShip)
				{
					if((int)randomX % 2 == 0){	randomX += 100;	}else{ randomX -= 100; }
				}
				else
				{
					if((int)randomY % 2 == 0){	randomY += 100;	}else{ randomY -= 100; }
				}
			}
			Point inPosition = new Point(randomX, randomY);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation, speed));
		}
		return asteroids;
	}
	
	public States getState()
	{
		return state;
	}
	
	public AsteroidsFrame getFrame()
	{
		return frame;
	}
	
}
