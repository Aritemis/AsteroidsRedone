/**
 *	@author Ariana Fairbanks
 */

package model_game;

import java.awt.Color;
import java.awt.Graphics;

import control.AsteroidsControl;
import model_abstracts.Point;
import model_abstracts.Polygon;

public class Asteroid extends Polygon
{
	@SuppressWarnings("unused")
	private Point[] points;
	private int maxWidth;
	
	public Asteroid(Point[] inShape, Point inPosition, double inRotation, int maxWidth) 
	{
		super(inShape, inPosition, inRotation);
		this.points = inShape;
		this.maxWidth = 2 * maxWidth;
	}

	public void paint(Graphics brush, Color color) 
	{
		move();
		Point[] points = this.getPoints();
		int npts = points.length;
		int[] xValues = new int[npts];
		int[] yValues = new int[npts];
		for(int i = 0; i < points.length; i++)
		{
			xValues[i] = (int) points[i].x;
			yValues[i] = (int) points[i].y;
		}
		brush.setColor(Color.black);
		brush.fillPolygon(xValues, yValues, npts);
		brush.setColor(color);
		brush.drawPolygon(xValues, yValues, npts);
	}

	public void move() 
	{
		position.x += Math.cos(Math.toRadians(rotation));
		position.y += Math.sin(Math.toRadians(rotation));
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
}
