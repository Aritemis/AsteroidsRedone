/**
 *	@author Ariana Fairbanks
 */

package model_game;

import java.awt.Color;
import java.awt.Graphics;

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
		int screenWidth = AsteroidsControl.SCREEN_WIDTH;
		int screenHeight = AsteroidsControl.SCREEN_HEIGHT;
		if(position.x > screenWidth + maxWidth) 
		{
			position.x -= screenWidth + (1.5 * maxWidth);
		} 
		else if(position.x + maxWidth < 0)
		{
			position.x += screenWidth + (1.5 * maxWidth);
		}
		
		if(position.y > screenHeight + maxWidth) 
		{
			position.y -= screenHeight + (1.5 * maxWidth);
		} 
		else if(position.y + maxWidth < 0) 
		{
			position.y += screenHeight + (1.5 * maxWidth);
		}
		
	}

	public int hit()
	{
		health--;
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
}
