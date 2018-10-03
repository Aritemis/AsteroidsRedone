/**
 * @author Ariana Fairbanks
 */

package model_objects;

import java.awt.Color;
import java.awt.Graphics;
import control.AsteroidsControl;
import model_abstracts.Circle;
import model_abstracts.Point;
import model_enum.BulletType;

public class Bullet extends Circle 
{
	private double rotation;
	private double speed;
	private BulletType type;

	public Bullet(Point center, double rotation, BulletType type) 
	{
		super(center, 5);
		this.rotation = rotation;
		this.speed = type.speed;
		this.type = type;
	}

	public void paint(Graphics brush, Color color) 
	{
		//switch case type?
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, radius, radius);
	}

	public void move() 
	{
		center.x += speed * 4 * Math.cos(Math.toRadians(rotation));
		center.y += speed * 4 * Math.sin(Math.toRadians(rotation));
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
