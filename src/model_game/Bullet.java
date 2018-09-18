/**
 * @author Ariana Fairbanks
 */

package model_game;

import java.awt.Color;
import java.awt.Graphics;
import control.AsteroidsControl;
import model_abstracts.Circle;
import model_abstracts.Point;

public class Bullet extends Circle 
{

	private double rotation;

	public Bullet(Point center, double rotation) 
	{
		super(center, 5);
		this.rotation = rotation;
	}

	public void paint(Graphics brush, Color color) 
	{
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, radius, radius);
	}

	public void move() 
	{
		center.x += 4 * Math.cos(Math.toRadians(rotation));
		center.y += 4 * Math.sin(Math.toRadians(rotation));
	}
	
	public boolean outOfBounds()
	{
		boolean result = false;
		if(center.x > AsteroidsControl.SCREEN_WIDTH || center.x < 0 || center.y > AsteroidsControl.SCREEN_HEIGHT || center.y < 0) 
		{
			result = true;
		}
		return result;
	}
	
	public Point getCenter()
	{
		return this.center;
	}


}
