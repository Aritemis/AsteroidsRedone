package model_abstracts;

public abstract class Polygon 
{
	private Point[] shape;   // An array of points.
	public Point position;   
	public double rotation; // Zero degrees is due east.

	public Polygon(Point[] inShape, Point inPosition, double inRotation) 
	{
		shape = inShape;
		position = inPosition;
		rotation = inRotation;

		// First, we find the shape's top-most left-most boundary, its origin.
		//Point origin = new Point(shape[0].x, shape[0].y);
		/*
		for (Point p : shape) 
		{
			if (p.x < origin.x) origin.x = p.x;
			if (p.y < origin.y) origin.y = p.y;
		}
		*/

		// Then, we orient all of its points relative to the real origin.
		/*
		for (Point p : shape) 
		{
			p.x -= origin.x;
			p.y -= origin.y;
		}
		*/
	}

	// "getPoints" applies the rotation and offset to the shape of the polygon.
	public Point[] getPoints() 
	{
		Point center = findCenter();
		Point[] points = new Point[shape.length];
		for (int i = 0; i < shape.length; i++) 
		{
			//    for (Point p : shape) {
			Point p = shape[i];
			double x = ((p.x-center.x) * Math.cos(Math.toRadians(rotation)))
					- ((p.y-center.y) * Math.sin(Math.toRadians(rotation)))
					+ center.x/2 + position.x;
			double y = ((p.x-center.x) * Math.sin(Math.toRadians(rotation)))
					+ ((p.y-center.y) * Math.cos(Math.toRadians(rotation)))
					+ center.y/2 + position.y;
			points[i] = new Point((int)x, (int)y);
		}
		return points;
	}
	
	public boolean collision(Polygon ship) 
	{
		// get the Point objects for the Ship
		Point[] points = ship.getPoints();
		boolean contains = false;
		for(Point p : points) 
		{
			if(this.contains(p)) 
			{
				contains = true;
			}
		}
		return contains;
	}


	// "contains" implements some magical math (i.e. the ray-casting algorithm).
	public boolean contains(Point point) 
	{
		Point[] points = getPoints();
		double crossingNumber = 0;
		for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) 
		{
			if ((((points[i].x < point.x) && (point.x <= points[j].x)) ||
					((points[j].x < point.x) && (point.x <= points[i].x))) &&
					(point.y > points[i].y + (points[j].y-points[i].y)/
							(points[j].x - points[i].x) * (point.x - points[i].x))) 
			{
				crossingNumber++;
			}
		}
		return crossingNumber%2 == 1;
	}

	public void rotate(int degrees) {rotation = (rotation+degrees)%360;}

	// "findArea" implements some more magic math.
	private double findArea() 
	{
		double sum = 0;
		for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) 
		{
			sum += shape[i].x*shape[j].y-shape[j].x*shape[i].y;
		}
		return Math.abs(sum/2);
	}

	// "findCenter" implements another bit of math.
	private Point findCenter() 
	{
		Point sum = new Point(0,0);
		for (int i = 0, j = 1; i < shape.length; i++, j=(j+1)%shape.length) 
		{
			sum.x += (shape[i].x + shape[j].x)
					* (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
			sum.y += (shape[i].y + shape[j].y)
					* (shape[i].x * shape[j].y - shape[j].x * shape[i].y);
		}
		double area = findArea();
		return new Point((int) Math.abs(sum.x/(6*area)), (int)Math.abs(sum.y/(6*area)));
	}
	
}