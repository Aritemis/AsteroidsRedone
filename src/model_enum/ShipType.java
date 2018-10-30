/**
 * @author Ariana Fairbanks
 */

package model_enum;

public enum ShipType
{
	STANDARD(1, 1, 270), ORIGINAL(0, 1, 270);
	
	public final int shipShape;
	public final int speed;
	public final double startingRotation;
	
	private ShipType(int shipShape, int speed, double startingRotation)
	{
		this.shipShape = shipShape;
		this.speed = speed;
		this.startingRotation = startingRotation;
	}
}
