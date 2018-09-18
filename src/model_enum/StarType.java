/**
 *	@author Ariana Fairbanks
 */

package model_enum;

public enum StarType
{
	STANDARD (6, 1, 1), SLOW (8, 2, .5), FAST (4, 1, 2);
	
	public int maxRadius;
	public int minRadius;
	public double speed;
	
	private StarType(int maxRadius, int minRadius, double speed)
	{
		this.maxRadius = maxRadius;
		this.minRadius = minRadius;
		this.speed = speed;
	}
}