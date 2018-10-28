/**
 *	@author Ariana Fairbanks
 */

package model_objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import control.AsteroidsControl;
import model_abstracts.Point;
import model_abstracts.Polygon;
import model_enum.AsteroidType;

public class Asteroid extends Polygon
{
	private AsteroidType type;
	private int maxWidth;
	private double speed;
	private int health;
	private int score;
	
	public Asteroid(Point[] inShape, Point inPosition, double inRotation, AsteroidType type, int maxWidth, double speed, int health, int score) 
	{
		super(inShape, inPosition, inRotation);
		this.type = type;
		this.maxWidth = maxWidth;
		this.speed = speed;
		this.health = health;
		this.score = score;
	}

	public void paint(Graphics brush) 
	{
		Point[] points = this.getPoints();
		int npts = points.length;
		int[] xValues = new int[npts];
		int[] yValues = new int[npts];
		for(int i = 0; i < points.length; i++)
		{
			xValues[i] = (int) points[i].x;
			yValues[i] = (int) points[i].y;
		}
		brush.setColor(type.fillColor);
		brush.fillPolygon(xValues, yValues, npts);
		brush.setColor(type.lineColor);
		brush.drawPolygon(xValues, yValues, npts);
	}

	public void move() 
	{
		position.x += speed * Math.cos(Math.toRadians(rotation));
		position.y += speed * Math.sin(Math.toRadians(rotation));
	}
	
	public void moveInBounds()
	{
		int screenWidth = AsteroidsControl.screenWidth;
		int screenHeight = AsteroidsControl.screenHeight;
		if(position.x > screenWidth + maxWidth) 
		{
			position.x -= screenWidth + (maxWidth);
		} 
		else if(position.x + maxWidth < 0)
		{
			position.x += screenWidth + (maxWidth);
		}
		
		if(position.y > screenHeight + maxWidth) 
		{
			position.y -= screenHeight + (maxWidth);
		} 
		else if(position.y + maxWidth < 0) 
		{
			position.y += screenHeight + (maxWidth);
		}
	}
	
	public boolean outOfBounds()
	{
		boolean result = false;
		int screenWidth = AsteroidsControl.screenWidth;
		int screenHeight = AsteroidsControl.screenHeight;
		if((position.x > screenWidth + maxWidth) || (position.x + maxWidth < 0) || 
				(position.y > screenHeight + maxWidth) || (position.y + maxWidth < 0))
		{
			result = true;
		}
		return result;
	}

	public int hit(double damage)
	{
		health -= damage;
		return health;
	}
	
	public int getHealth()
	{
		return health;
	}

	public int getScore()
	{
		return score;
	}
	
	public static ArrayList<Asteroid> generateArcadeAsteroids(ArrayList<Asteroid> asteroids, int level, int baseScore)
	{
		int totalAsteroids = 5 + (level / 5);
		int speedModifier = 1 + (level / 10) * (AsteroidsControl.screenWidth / 500);
		int healthModifier = 1 + (level / 15);
		asteroids = createAsteroids(asteroids, totalAsteroids, baseScore, speedModifier, healthModifier, AsteroidType.STANDARD);
		return asteroids;
	}
	
	public static ArrayList<Asteroid> createAsteroids(ArrayList<Asteroid> asteroids, int number, int baseScore, double speedModifier, double healthModifier, AsteroidType type) 
	{
		for(int i = 0; i < number; ++i) 
		{
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = ThreadLocalRandom.current().nextInt((AsteroidsControl.screenHeight / type.minWidth), (AsteroidsControl.screenHeight / type.maxWidth) + 1);

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
			double x = ThreadLocalRandom.current().nextDouble() * AsteroidsControl.screenWidth;
			double y = ThreadLocalRandom.current().nextDouble() * AsteroidsControl.screenHeight;
			if(ThreadLocalRandom.current().nextDouble() > .5) { x = 100; }
			else { y = 100; }
			Point inPosition = new Point(x, y);
			double inRotation = ThreadLocalRandom.current().nextDouble() * 360;
			double speed = type.baseSpeed * speedModifier;
			int health = (int) (type.baseHealth * healthModifier);
			int score = (int) (speed * health * 10);
			asteroids.add(new Asteroid(inSides, inPosition, inRotation, type, type.maxWidth, speed, health, score));
		}
		return asteroids;
	}
}
