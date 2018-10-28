/**
 * @author Ariana Fairbanks
 */

package model_enum;

import model.ShipShapes;
import model_abstracts.Point;

public enum ShipType
{
	STANDARD(0, 1, 270, 30, 40);
	
	public final int shipShape;
	public final int speed;
	public final double startingRotation;
	public final int shipWidth;
	public final int shipHeight;
	
	private ShipType(int shipShape, int speed, double startingRotation, int shipWidth, int shipHeight)
	{
		this.shipShape = shipShape;
		this.speed = speed;
		this.startingRotation = startingRotation;
		this.shipWidth = shipWidth;
		this.shipHeight = shipHeight;
	}
}
