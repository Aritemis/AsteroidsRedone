/**
 * @author Ariana Fairbanks
 */

package model_abstracts;

public abstract class Circle
{
	protected Point center;
	protected int radius;

	public Circle(Point center, int radius)
	{
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter()
	{
		return center;
	}
}
