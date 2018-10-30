/**
 *	@author Ariana Fairbanks
 */

package model_enum;

import java.awt.Color;

public enum AsteroidType
{
	STANDARD (13, 10, 1, 1, 1, Color.white, Color.black);
	
	public final int minWidth;
	public final int maxWidth;
	public final double baseSpeed;
	public final int baseHealth;
	public final double appearanceRate;
	public final int sizeScoreModifier;
	public final Color lineColor;
	public final Color fillColor;
	
	private AsteroidType(int minWidth, int maxWidth, double baseSpeed, int baseHealth, double appearanceRate, Color lineColor, Color fillColor)
	{
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.baseSpeed = baseSpeed;
		this.baseHealth = baseHealth;
		this.appearanceRate = appearanceRate;
		this.sizeScoreModifier = (100 - maxWidth) / 25; 
		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}
}
