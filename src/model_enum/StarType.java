/**
 *	@author Ariana Fairbanks
 */

package model_enum;

public enum StarType
{
	STANDARD (6, 1);
	
	public int maxRadius;
	public double speed;
	
	private StarType(int maxRadius, double speed)
	{
		this.maxRadius = maxRadius;
		this.speed = speed;
	}
}