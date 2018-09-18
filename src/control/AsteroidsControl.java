/**
 *	@author Ariana Fairbanks
 */

package control;

import java.util.concurrent.ThreadLocalRandom;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.HighScore;
import model_abstracts.Point;
import model_enum.AsteroidType;
import model_enum.StarType;
import model_enum.States;
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
	
	public ArrayList<Star> createStars(ArrayList<Star> stars, int numberOfStars, StarType type) 
	{
		for(int i = 0; i < numberOfStars; ++i) 
		{
			Point center = new Point(ThreadLocalRandom.current().nextDouble() * SCREEN_WIDTH, ThreadLocalRandom.current().nextDouble() * SCREEN_HEIGHT);
			int radius = (int) (ThreadLocalRandom.current().nextDouble(1, type.maxRadius));
			stars.add(new Star(center, radius, type.speed));
		}
		return stars;
	 }
	
	public ArrayList<Asteroid> createRandomAsteroids(ArrayList<Asteroid> asteroids, int number, AsteroidType type) 
	{
		for(int i = 0; i < number; ++i) 
		{
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (ThreadLocalRandom.current().nextInt(type.minWidth, type.maxWidth + 1));

			// Find the circles angle
			double angle = (ThreadLocalRandom.current().nextDouble() * Math.PI * 1.0/2.0);
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
			double x = ThreadLocalRandom.current().nextDouble() * SCREEN_WIDTH;
			double y = ThreadLocalRandom.current().nextDouble() * SCREEN_HEIGHT;
			if(ThreadLocalRandom.current().nextDouble() > .5) { x = 0; }
			else { y = 0; }
			Point inPosition = new Point(x, y);
			double inRotation = ThreadLocalRandom.current().nextDouble() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation, type.maxWidth, type.speed));
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
