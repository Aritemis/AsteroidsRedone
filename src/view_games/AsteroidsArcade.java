/**
 *	@author Ariana Fairbanks
 */

package view_games;

import java.awt.Color;
import java.awt.Graphics;
import control.AsteroidsControl;
import model_enum.BulletType;
import model_enum.ShipType;
import model_objects.Asteroid;
import view.ColorScheme;
import view.ViewPanel;
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
	public void refresh()
	{
		
		if(AsteroidsControl.move && !AsteroidsControl.menu && !AsteroidsControl.limbo && !AsteroidsControl.reset && !asteroidList.isEmpty())
		{
			super.update();
		}
	}
	
	@Override
	public void paint(Graphics brush) 
	{
		//super.paint(brush);
		this.requestFocus();
		if(AsteroidsControl.menu)
		{
			stopTimers();
			base.resetGameVariables();
			base.changeState(ViewPanel.MENU);
		}
		else if(AsteroidsControl.limbo)
		{
			if(AsteroidsControl.reset){	setUpLevel(); }
		}
		else
		{
			if(asteroidList.isEmpty())
			{
				brush.drawImage(Images.levelclear, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
				clearedLevel = true;
			}
			else if(lives < 1)
			{
				brush.setColor(Color.red);
				brush.drawString("Lives Left: " + lives, 25, 25);
				brush.drawImage(Images.gameover, 200, 200, frame);
				brush.drawImage(Images.proceed, 215, 508, frame);
				AsteroidsControl.limbo = true;
			}
			else
			{
				if(!AsteroidsControl.paused)
				{
					ColorScheme.normalColor();
					if(collide)
					{
						ColorScheme.damagedColor();
					}
				}
				super.paint(brush);
				paintStars(brush);
				paintAsteroidsAndBullets(brush);
				paintShip(brush);
				paintWords(brush);
				if(AsteroidsControl.paused)
				{
					brush.drawImage(Images.pause, 200, 200, frame);
					brush.drawImage(Images.proceed, 215, 508, frame);
				}
			}
		}
	}

}
