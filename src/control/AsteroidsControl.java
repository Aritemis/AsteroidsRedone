/**
 *	@author Ariana Fairbanks
 */

package control;

import java.util.ArrayList;
import java.util.List;
import model.Star;
import modelabstracts.Point;
import view.AsteroidsFrame;
import view.States;

public class AsteroidsControl
{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	public static boolean paused;
	public static boolean limbo; //continue?
	public static boolean reset; //set up next level
	
	private AsteroidsFrame frame;
	private States state;
	
	
	public void start()
	{
		frame = new AsteroidsFrame(this);
	}
	
	public void changeState(States state)
	{
		this.state = state;
		frame.changeViewState();
	}
	
	public Star[] createStars(int numberOfStars, int maxRadius) 
	{
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; ++i) 
		{
			Point center = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1)
			{
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}
		return stars;
	 }
	
	public States getState()
	{
		return state;
	}
	
}
