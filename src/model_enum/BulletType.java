package model_enum;

/**
 * @author Ariana Fairbanks
 */
public enum BulletType
{
	STANDARD(1);
	
	public final int speed;
	
	private BulletType(int speed)
	{
		this.speed = speed;
	}
}
