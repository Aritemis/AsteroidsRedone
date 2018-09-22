/**
 *	@author Ariana Fairbanks
 */

package control;

import java.util.concurrent.ThreadLocalRandom;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private List<AsteroidType> asteroidTypes;
	
	
	
	public void start()
	{
		limbo = false;
		reset = false;
		paused = false;
		frame = new AsteroidsFrame(this);
		asteroidTypes = Arrays.asList(AsteroidType.values());
		if(asteroidTypes.size() > 0)
		{
			asteroidTypes.remove(AsteroidType.STANDARD);
		}
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
			int radius = ThreadLocalRandom.current().nextInt(type.minRadius, type.maxRadius + 1);
			stars.add(new Star(center, radius, type.speed));
		}
		return stars;
	 }
	
	public ArrayList<Asteroid> generateAsteroids(ArrayList<Asteroid> asteroids, int level, int baseScore)
	{
		int totalAsteroids = 5 + (level / 5);
		int currentAsteroids = 0;
		int speedModifier = 1 + level / 10;
		int healthModifier = 1 + level / 20;
		if(asteroidTypes.size() > 0)
		{
			for(AsteroidType currentType : asteroidTypes)
			{
				int number = (int) Math.floor(currentType.appearanceRate * level);
				if(number > 0)
				{
					currentAsteroids += number;
					asteroids = createAsteroids(asteroids, number, baseScore, speedModifier, healthModifier, currentType);
				}
			}
		}
		int remainingAsteroids = totalAsteroids - currentAsteroids;
		if(remainingAsteroids > 0)
		{
			asteroids = createAsteroids(asteroids, remainingAsteroids, baseScore, speedModifier, healthModifier, AsteroidType.STANDARD);
		}
		return asteroids;
	}
	
	public ArrayList<Asteroid> createAsteroids(ArrayList<Asteroid> asteroids, int number, int baseScore, double speedModifier, double healthModifier, AsteroidType type) 
	{
		for(int i = 0; i < number; ++i) 
		{
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = ThreadLocalRandom.current().nextInt(type.minWidth, type.maxWidth + 1);

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
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			double x = ThreadLocalRandom.current().nextDouble() * SCREEN_WIDTH;
			double y = ThreadLocalRandom.current().nextDouble() * SCREEN_HEIGHT;
			if(ThreadLocalRandom.current().nextDouble() > .5) { x = 0; }
			else { y = 0; }
			Point inPosition = new Point(x, y);
			double inRotation = ThreadLocalRandom.current().nextDouble() * 360;
			double speed = type.baseSpeed * speedModifier;
			int health = (int) (type.baseHealth * healthModifier);
			int score = (int) (speed * health * 10);
			asteroids.add(new Asteroid(inSides, inPosition, inRotation, type.maxWidth, speed, health, score));
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
