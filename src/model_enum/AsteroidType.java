/**
 *	@author Ariana Fairbanks
 */

package model_enum;

public enum AsteroidType
{
	STANDARD (40, 50, .5, 1, 1);
	
	public int minWidth;
	public int maxWidth;
	public double baseSpeed;
	public int baseHealth;
	public double appearanceRate;
	public int sizeScoreModifier;
	
	private AsteroidType(int minWidth, int maxWidth, double baseSpeed, int baseHealth, double appearanceRate)
	{
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.baseSpeed = baseSpeed;
		this.baseHealth = baseHealth;
		this.appearanceRate = appearanceRate;
		this.sizeScoreModifier = (100 - maxWidth) / 25; 
		//this.speedModifier = (int) (4 * speed);
	}
}
