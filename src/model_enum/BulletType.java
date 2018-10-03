/**
 * @author Ariana Fairbanks
 */

package model_enum;

public enum BulletType
{
	STANDARD(1, 1, 1);
	
	public final double speed;
	public final double damage;
	public final double size;
	
	private BulletType(double speed, double damage, double size)
	{
		this.speed = speed;
		this.damage = damage;
		this.size = size;
	}
}
