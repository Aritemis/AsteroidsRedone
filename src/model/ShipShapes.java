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
		shipShapes.add(new Point[]{ new Point(0, 0), new Point(0, (AsteroidsControl.screenHeight / 20)), new Point((AsteroidsControl.screenHeight / 15), (AsteroidsControl.screenHeight / 40)) });
	}
	
	
}
