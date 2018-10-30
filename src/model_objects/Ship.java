/**
 * @author Ariana Fairbanks
 */

package model_objects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import control.AsteroidsControl;
import model.ShipShapes;
import model_abstracts.Point;
import model_abstracts.Polygon;
import model_enum.BulletType;
import model_enum.ShipType;
import view.ColorScheme;

public class Ship extends Polygon implements KeyListener
{
	public static boolean forward;
	public static boolean turningRight;
	public static boolean turningLeft;
	private double speed;
	private int rechargeTime;
	private int cooldown;
	private boolean shoot;
	private ArrayList<Bullet> shots;
	private ShipType type;
	private BulletType bullet;

	public Ship(Point inPosition, ShipType type, BulletType bullet)
	{
		super(ShipShapes.shipShapes.get(type.shipShape), inPosition.clone(), type.startingRotation);
		int speedMod = 4;
		this.speed = type.speed * speedMod;
		this.type = type;
		this.bullet = bullet;
		rechargeTime = bullet.rechargeTime;
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
		int screenSizeMod = (AsteroidsControl.screenHeight / 500);
        if(forward) 
        {
            position.x += speed * screenSizeMod * Math.cos(Math.toRadians(rotation));
            position.y += speed * screenSizeMod * Math.sin(Math.toRadians(rotation));
        }
        if(turningRight) 
        {
            rotate((int)(speed * screenSizeMod));
        }
        if(turningLeft) 
        {
            rotate((int)(-speed * screenSizeMod));
        }

		if(position.x > AsteroidsControl.screenBoundaryRight) 
		{
			position.x -= AsteroidsControl.screenBoundaryRight;
		} 
		else if(position.x < AsteroidsControl.screenBoundaryLeft)
		{
			position.x += AsteroidsControl.screenBoundaryRight;
		}
		if(position.y > AsteroidsControl.screenHeight) 
		{
			position.y -= AsteroidsControl.screenHeight;
		} 
		else if(position.y < 0) 
		{
			position.y += AsteroidsControl.screenHeight;
		}
	}
	
	public void resetShip()
	{
		forward = false;
		turningRight = false;
		turningLeft = false;
		shoot = false;
		cooldown = 0;
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
	
	public void recharge()
	{
		if(cooldown > 0)
		{
			cooldown--;
		}
		if(shoot && cooldown == 0)
		{
			Bullet newBullet = new Bullet(getPoints()[0].clone(), rotation, bullet);
        	newBullet.getCenter().x -= 2;
        	shots.add(newBullet);
        	cooldown += rechargeTime;
		}
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if(!AsteroidsControl.limbo && !AsteroidsControl.paused)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
			{
				forward = true;
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
				shoot = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e){ }

}


