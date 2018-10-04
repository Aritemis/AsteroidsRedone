/**
 * @author Ariana Fairbanks
 */

package model_objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.AsteroidsControl;
import model_abstracts.Point;
import model_abstracts.Polygon;
import model_enum.BulletType;
import model_enum.ShipType;
import view.ColorScheme;

public class Ship extends Polygon implements KeyListener
{
	public static boolean forward;
	public static boolean backward;
	public static boolean turningRight;
	public static boolean turningLeft;
	private double speed;
	private boolean shoot;
	private boolean mustRelease;
	private ArrayList<Bullet> shots;
	private int speedMod;
	private ShipType type;

	public Ship(Point inPosition, ShipType type)
	{
		super(type.shipShape, inPosition.clone(), type.startingRotation);
		this.speed = type.speed;
		this.type = type;
		speedMod = 2;
		resetShip();
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
		brush.setColor(ColorScheme.shipFill);
		brush.fillPolygon(xValues, yValues, npts);
		brush.setColor(ColorScheme.shipLine);
		brush.drawPolygon(xValues, yValues, npts);
	}
	
	public void move() 
	{	
        if(forward) 
        {
            position.x += speed * speedMod * Math.cos(Math.toRadians(rotation));
            position.y += speed * speedMod * Math.sin(Math.toRadians(rotation));
        }
        if(backward) 
        {
            position.x -= speed * speedMod * Math.cos(Math.toRadians(rotation));
            position.y -= speed * speedMod * Math.sin(Math.toRadians(rotation));
        }
        if(turningRight) 
        {
            rotate((int)(speed * speedMod));
        }
        if(turningLeft) 
        {
            rotate((int)(speed * -speedMod));
        }
        if(shoot) 
        {
            if(!mustRelease)
            {
            	Bullet start = new Bullet(getPoints()[2].clone(), rotation, BulletType.STANDARD);
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
		rotation = type.startingRotation;
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
					AsteroidsControl.move = true;
				}
				else
				{
					AsteroidsControl.paused = true;
					AsteroidsControl.move = false;
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
					AsteroidsControl.move = false;
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


