/**
 * @author Ariana Fairbanks
 */

package model_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

import control.AsteroidsControl;
import model_abstracts.Circle;
import model_abstracts.Point;

public class Star extends Circle 
{
	private double speed;

	public Star(Point center, int radius, double speed)
	{
		super(center, radius);
		this.speed = speed;
	}

	public void paint(Graphics brush) 
	{
		Graphics2D g2d = (Graphics2D) brush;
		g2d.setColor(Color.white);
		if(ThreadLocalRandom.current().nextDouble() < .15)
		{
			g2d.setColor(Color.gray);
		}
		g2d.fillOval((int) center.x, (int) center.y, radius, radius);
	}

	public void move(double rotation) 
	{
		double radians = Math.toRadians(rotation);
		double mod = -3;
		
		if(Ship.forward) 
		{
			mod = -5;
		}
		if(Ship.backward) 
		{
            mod = 3;
		}
		
		center.x += speed * mod * Math.cos(radians);
		center.y += speed * mod * Math.sin(radians);


		if(center.x > AsteroidsControl.SCREEN_WIDTH) 
		{
			center.x -= AsteroidsControl.SCREEN_WIDTH;
		} 
		else if(center.x < 0)
		{
			center.x += AsteroidsControl.SCREEN_WIDTH;
		}
		if(center.y > AsteroidsControl.SCREEN_HEIGHT) 
		{
			center.y -= AsteroidsControl.SCREEN_HEIGHT;
		} 
		else if(center.y < 0) 
		{
			center.y += AsteroidsControl.SCREEN_HEIGHT;
		}
	}

}
