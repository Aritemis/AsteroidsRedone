/**
 *	@author Ariana Fairbanks
 */

package view_games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import control.AsteroidsControl;
import model_abstracts.Point;
import model_enum.BulletType;
import model_enum.ShipType;
import model_enum.StarType;
import model_enum.States;
import model_objects.Asteroid;
import model_objects.Bullet;
import model_objects.Ship;
import model_objects.Star;
import view.Animation;
import view.AsteroidsFrame;
import view_images.Images;

public class AsteroidsArcade extends AsteroidsGame
{
	private static final long serialVersionUID = 4338632920192192267L;
	private boolean clearedLevel;
	private int level;

	public AsteroidsArcade(AsteroidsControl base)
	{
		super(base, ShipType.STANDARD, BulletType.STANDARD, true);
		level = 0;
		clearedLevel = false;
		setUpLevel();
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpListeners()
	{

	}
	
	private void setUpLevel()
	{
		ship.resetShip();
		ship.setPosition(shipPosition);
		if(clearedLevel)
		{
			level++;
		}
		else
		{
			level = 1;
			score = 0;
			lives = 5;
		}
		asteroidList = Asteroid.generateArcadeAsteroids(asteroidList, level, baseScore);
		base.resetGameVariables();
		collideCount = 0;
		invincible = false;
		collide = false;
		clearedLevel = false;
	}

	private void paintWords(Graphics brush)
	{
		brush.setColor(Color.green);
		brush.drawString("Level: " + level, 25, 50);
		brush.drawString("Score: " + score, 25, 75);
		if(lives < 2)
		{
			brush.setColor(Color.red);
		}
		brush.drawString("Lives Left: " + lives, 25, 25);
	}
	
	@Override
	public void paint(Graphics brush) 
	{
		this.requestFocus();
		if(AsteroidsControl.menu)
		{
			stopTimers();
			base.resetGameVariables();
			base.changeState(States.MENU);
		}
		else if(AsteroidsControl.limbo)
		{
			if(AsteroidsControl.reset){	setUpLevel(); }
		}
		else
		{
			if(asteroidList.isEmpty())
			{
				brush.drawImage(Images.win, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
				clearedLevel = true;
			}
			else if(lives < 1)
			{
				brush.setColor(Color.red);
				brush.drawString("Lives Left: " + lives, 25, 25);
				brush.drawImage(Images.lose, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
			}
			else
			{
				if(!AsteroidsControl.paused)
				{
					bulletColor = ship.rainbow();
					if(collide)
					{
						bulletColor = ship.danger();
					}
				}
				if(AsteroidsControl.move)
				{
					moveStars();
					moveAsteroidsAndBullets();
					ship.move();
				}
				super.paint(brush);
				paintStars(brush);
				paintAsteroidsAndBullets(brush, bulletColor);
				paintWords(brush);
				paintShip(brush);
				if(AsteroidsControl.paused)
				{
					brush.drawImage(Images.pause, 200, 200, frame);
					brush.drawImage(Images.proceed, 215, 508, frame);
				}
			}
		}
	}
	
	
}
