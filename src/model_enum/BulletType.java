/**
 * @author Ariana Fairbanks
 */

package model_enum;

public enum BulletType
{
	STANDARD(1, 1, 120, 15);
	
	public final double speed;
	public final double damage;
	public final double size;
	public final int rechargeTime;
	
	private BulletType(double speed, double damage, double size, int rechargeTime)
	{
		this.speed = speed;
		this.damage = damage;
		this.size = size;
		this.rechargeTime = rechargeTime;
	}
}
