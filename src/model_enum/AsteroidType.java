/**
 *	@author Ariana Fairbanks
 */

package model_enum;

public enum AsteroidType
{
	STANDARD (35, 40, 1);
	
	public int minWidth;
	public int maxWidth;
	public double speed;
	
	private AsteroidType(int minWidth, int maxWidth, double speed)
	{
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.speed = speed;
	}
}
