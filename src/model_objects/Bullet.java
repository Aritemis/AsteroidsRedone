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
import view.ColorScheme;

public class Bullet extends Circle 
{
	private double rotation;
	private double speed;
	private BulletType type;

	public Bullet(Point center, double rotation, BulletType type) 
	{
		super(center, (int)(AsteroidsControl.screenHeight / type.size));
		this.rotation = rotation;
		this.speed = type.speed;
		this.type = type;
		System.out.println("c " + center + " r " + radius);
		System.out.println(AsteroidsControl.screenHeight + " " + type.size + " " + (AsteroidsControl.screenHeight / type.size));
	}

	public void paint(Graphics brush) 
	{
		//switch case type?
		brush.setColor(ColorScheme.bullet);
		brush.fillOval((int)center.x, (int)center.y, radius, radius);
	}

	public void move() 
	{
		center.x += speed * (AsteroidsControl.screenWidth / 200) * Math.cos(Math.toRadians(rotation));
		center.y += speed * (AsteroidsControl.screenWidth / 200) * Math.sin(Math.toRadians(rotation));
	}
	
	public boolean outOfBounds()
	{
		boolean result = false;
		if(center.x > AsteroidsControl.screenWidth || center.x < 0 || center.y > AsteroidsControl.screenHeight || center.y < 0) 
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
