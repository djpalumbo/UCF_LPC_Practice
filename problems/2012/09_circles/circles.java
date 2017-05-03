// Arup Guha
// 8/28/2012
// Solution to 2012 UCF Local Contest Probem: Simi Circles
// Note: Uses some geometry and law of cosines to solve a triangle.

import java.util.*;
import java.io.*;

class drop {

	public final static boolean DEBUG = false;
	public final static double PI = 3.1415926535898;

	public double x;
	public double y;
	public double r;

	public drop(double myx, double myy, double myr) {
		x = myx;
		y = myy;
		r = myr;
	}

	public double areaAdded(drop other) {

		double dist = dToCenter(other);

		// Just here to validate input file.
		if (DEBUG && dist < Math.abs(r - other.r))
			System.out.println("Error in input file.");

		// No intersect case. Add the whole circle.
		if (dist >= r + other.r)
			return other.area();

		// Angle formed by radius of new circle and line between centers, using law of cosines.
		double theta = Math.acos((other.r*other.r + dist*dist - r*r)/(2*other.r*dist));

		// Angle formed by radius of old circle and line between centers, using law of cosines.
		double alpha = Math.acos((r*r + dist*dist-other.r*other.r)/(2*r*dist));

		// Area of the sector of the new circle that is untouched by the old.
		// (2*theta is the central angle of the new circle intersecting with the old.
		double extra = other.areaSector(2*PI - 2*theta);

		if (DEBUG)
			System.out.println("theta = "+theta+" alpha is "+alpha+ " extra is "+extra);

		// Triangle formed by the sector that intersects the circle.
		double triangle = .5*other.r*other.r*Math.sin(2*theta);

		// This is that tiny slice inside the sector of the old circle, but outside the triangle of
		// that same circle, that must be subtracted out of the triangle we just calculated.
		double slice = areaSector(2*alpha) - .5*r*r*Math.sin(2*alpha);

		// This is exactly the area we didn't add in that is new.
		double lensNonArea = triangle - slice;
		extra += lensNonArea;

		if (DEBUG)
			System.out.println("tri = "+triangle+" slice = "+slice+" lens = "+lensNonArea);

		return extra;
	}

	// Area of the whole circle
	public double area() {
		return PI*r*r;
	}

	// Returns the area of a sector with the given angle.
	public double areaSector(double angle) {
		return area()*angle/(2*PI);
	}

	// Distance between centers of circles.
	public double dToCenter(drop other) {
		return Math.sqrt((x-other.x)*(x-other.x) + (y-other.y)*(y-other.y));
	}
}

public class circles {

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("circles.in"));

		// Go through each case.
		int numCases = fin.nextInt();
		for (int loop=1; loop<=numCases; loop++) {

			// Initial items.
			drop prev = null;
			double area = 0;

			// This method of only considering the previous circle only works because
			// of the restrictions in the problem specification.
			int numDrops = fin.nextInt();
			for (int j=0; j<numDrops; j++) {

				double x = fin.nextDouble();
				double y = fin.nextDouble();
				double r = fin.nextDouble();
				drop d = new drop(x,y,r);

				// First drop case.
				if (prev == null)
					area += d.area();

				// Otherwise, we look to add the new area.
				else
					area += prev.areaAdded(d);

				prev = d;
			}

			// Output the result.
			System.out.printf("Set #%d: %.2f\n\n", loop, area);
		}

		fin.close();
	}
}