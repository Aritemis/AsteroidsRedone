/**
 * @author Ariana Fairbanks
 */

package model_enum;

import model.ShipShapes;
import model_abstracts.Point;

public enum ShipType
{
	STANDARD(ShipShapes.shipShape1, 1, 270);
	
	public final Point[] shipShape;
	public final int speed;
	public final double startingRotation;
	
	private ShipType(Point[] shipShape, int speed, double startingRotation)
	{
		this.shipShape = shipShape;
		this.speed = speed;
		this.startingRotation = startingRotation;
	}
}
