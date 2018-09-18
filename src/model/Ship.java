/**
 * @author Ariana Fairbanks
 */

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import control.AsteroidsControl;
import model_abstracts.Point;
import model_abstracts.Polygon;

public class Ship extends Polygon implements KeyListener
{
	public static boolean forward;
	public static boolean backward;
	public static boolean turningRight;
	public static boolean turningLeft;
	private boolean shoot;
	private boolean mustRelease;
	private ArrayList<Bullet> shots;
	@SuppressWarnings("unused")
	private Point front;
	private static Point[] shipShape = { new Point(0, 0), new Point(0, 20), new Point(30, 10) };

	public Ship(Point inPosition, double inRotation)
	{
		super(shipShape, inPosition, inRotation);
		forward = false;
		backward = false;
		turningRight = false;
		turningLeft = false;
		shoot = false;
		mustRelease = false;
		shots = new ArrayList<Bullet>();
		front = inPosition;
	}

	public void paint(Graphics brush, Color color) 
	{
		Point[] points = this.getPoints();
		int npts = points.length;
		this.front = points[0];
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
        if(forward) 
        {
            position.x += 3 * Math.cos(Math.toRadians(rotation));
            position.y += 3 * Math.sin(Math.toRadians(rotation));
        }
        if(backward) 
        {
            position.x -= 3 * Math.cos(Math.toRadians(rotation));
            position.y -= 3 * Math.sin(Math.toRadians(rotation));
        }
        if(turningRight) 
        {
            rotate(2);
        }
        if(turningLeft) 
        {
            rotate(-2);
        }
        if(shoot) 
        {
            if(!mustRelease)
            {
            	Bullet start = new Bullet(getPoints()[2].clone(), rotation);
            	start.getCenter().x -= 2;
            	shots.add(start);
            }
            mustRelease = true;
            shoot = false;
        }

		if(position.x > AsteroidsControl.SCREEN_WIDTH) 
		{
			position.x -= AsteroidsControl.SCREEN_WIDTH;
		} 
		else if(position.x < 0)
		{
			position.x += AsteroidsControl.SCREEN_WIDTH;
		}
		if(position.y > AsteroidsControl.SCREEN_HEIGHT) 
		{
			position.y -= AsteroidsControl.SCREEN_HEIGHT;
		} 
		else if(position.y < 0) 
		{
			position.y += AsteroidsControl.SCREEN_HEIGHT;
		}
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return shots;
	}
	
	public double getRotation()
	{
		return rotation;
	}

	public void setPosition(Point position)
	{
		super.setPosition(position);
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if(!AsteroidsControl.limbo && !AsteroidsControl.paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
			{
				forward = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
			{
				backward = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) 
			{
				turningLeft = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
			{
				turningRight = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
				shoot = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) 
		{
			if(AsteroidsControl.limbo)
			{
				AsteroidsControl.reset = true;
			}
			else
			{
				if(AsteroidsControl.paused)
				{
					AsteroidsControl.paused = false;
				}
				else
				{
					AsteroidsControl.paused = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(!AsteroidsControl.limbo && !AsteroidsControl.paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
			{
				forward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
			{
				backward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) 
			{
				turningLeft = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
			{
				turningRight = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
				mustRelease = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{

	}

}


