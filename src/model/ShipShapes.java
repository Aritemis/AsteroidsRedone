/**
 *	@author Ariana Fairbanks
 */

package model;

import java.util.ArrayList;

import control.AsteroidsControl;
import model_abstracts.Point;

public class ShipShapes
{
	public static ArrayList<Point[]> shipShapes;
	
	public static void redoShipShapes()
	{
		shipShapes = new ArrayList<Point[]>();
		shipShapes.add(new Point[]{ new Point((AsteroidsControl.screenHeight / 15), (AsteroidsControl.screenHeight / 40)), new Point( 0, 0), new Point( 0, (AsteroidsControl.screenHeight / 20)) });
		shipShapes.add(new Point[]{ new Point((AsteroidsControl.screenHeight / 12.5), (AsteroidsControl.screenHeight / 37.5)), new Point( 0, 0), new Point( (AsteroidsControl.screenHeight / 75), (AsteroidsControl.screenHeight / 37.5)), new Point( 0, (AsteroidsControl.screenHeight / 18.75))});
	}
	
	
}
