/**
 * @author Ariana Fairbanks
 */

package model_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.AsteroidsControl;
import model_abstracts.Point;
import model_abstracts.Polygon;

public class Ship extends Polygon implements KeyListener
{
	public static boolean forward;
	public static boolean backward;
	public static boolean turningRight;
	public static boolean turningLeft;
	private double startingRotation;
	private double speed;
	private boolean shoot;
	private boolean mustRelease;
	private ArrayList<Bullet> shots;
	private int colorPosition;
	private int shipColor;
	private static Point[] shipShape = { new Point(0, 0), new Point(0, 20), new Point(30, 10) };

	public Ship(Point inPosition, double startingRotation, double speed)
	{
		super(shipShape, inPosition.clone(), startingRotation);
		this.startingRotation = startingRotation;
		this.speed = speed;
		resetShip();
	}

	public void paint(Graphics brush, Color color) 
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
		brush.setColor(Color.black);
		brush.fillPolygon(xValues, yValues, npts);
		brush.setColor(color);
		brush.drawPolygon(xValues, yValues, npts);
	}

	public Color rainbow()
	{
		Color color = null;
		switch(colorPosition)
		{
			case 0:
				color = Color.cyan;
				colorPosition ++;
				break;
				
			case 1: 
				color = Color.blue;
				colorPosition = 0;
				break;
				
			default:
				color = Color.gray;
				break;
		}
		return color;
	}
	
	public Color danger()
	{
		Color color = null;
		switch(shipColor)
		{
			case 0:
				color = Color.red;
				shipColor ++;
				break;
				
			case 1: 
				color = Color.white;
				shipColor = 0;
				break;
				
			default:
				color = Color.gray;
				break;
		}
		return color;
	}
	
	public void move() 
	{	
        if(forward) 
        {
            position.x += speed * 3 * Math.cos(Math.toRadians(rotation));
            position.y += speed * 3 * Math.sin(Math.toRadians(rotation));
        }
        if(backward) 
        {
            position.x -= speed * 3 * Math.cos(Math.toRadians(rotation));
            position.y -= speed * 3 * Math.sin(Math.toRadians(rotation));
        }
        if(turningRight) 
        {
            rotate((int)(speed * 2));
        }
        if(turningLeft) 
        {
            rotate((int)(speed * -2));
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
	
	public void resetShip()
	{
		forward = false;
		backward = false;
		turningRight = false;
		turningLeft = false;
		shoot = false;
		mustRelease = false;
		shots = new ArrayList<Bullet>();
		colorPosition = 0;
		shipColor = 0;
		rotation = startingRotation;
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
		this.position = position.clone();
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
		if(e.getKeyCode() == KeyEvent.VK_M)
		{
			if(!AsteroidsControl.menu)
			{
				if(AsteroidsControl.limbo)
				{
					AsteroidsControl.menu = true;
				}
				else 
				{
					AsteroidsControl.paused = true;
					int dialogResult = AsteroidsControl.confirmationMessage("Abandon game and return to menu?", "Exit game?");
					if(dialogResult == JOptionPane.OK_OPTION)
					{
						AsteroidsControl.menu = true;
					}
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
	public void keyTyped(KeyEvent e){ }

}


