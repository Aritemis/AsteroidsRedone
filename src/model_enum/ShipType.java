/**
 * @author Ariana Fairbanks
 */

package model_enum;

import model.ShipShapes;
import model_abstracts.Point;

public enum ShipType
{
	STANDARD(ShipShapes.shipShape1, 1);
	
	public final Point[] shipShape;
	public final int speed;
	
	private ShipType(Point[] shipShape, int speed)
	{
		this.shipShape = shipShape;
		this.speed = speed;
	}
}
