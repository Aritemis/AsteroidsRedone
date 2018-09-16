/**
 * @author Ariana Fairbanks
 */

package modelabstracts;
import java.awt.Component;

public abstract class Circle extends Component
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
