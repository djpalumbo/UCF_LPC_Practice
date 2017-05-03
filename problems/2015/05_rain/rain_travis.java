import java.io.*;
import java.util.*;

public class rain_travis {
	public static final double EPSILON = 1e-9;
	public static final double PI = 3.14159265358979;
	public static int NUM_TESTS = 100000;
	
	public static void main(String[] Args) throws Exception{
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));

		int t = sc.nextInt();

		while (t-- > 0) {
			double l = sc.nextDouble();
			double r = sc.nextDouble();

			// Check the different cases cases
			if (r * 2 < l || equal(r * 2, l)) {
				// Circle in square
				out.printf("%.02f%n", r * r * PI);
			} else if (l / Math.sqrt(2) < r || equal(l / Math.sqrt(2), r)) {
				// Square in Circle
				out.printf("%.02f%n", l * l);
			} else {
				// Intersecting shapes :(

				// Get the adjacent leg of the triangle formed by the center,
				// intersection of square circle, and intersection of x axis and
				// square
				double leg1 = l / 2.0;

				// Get the hypotenuse of the triangle
				double hypot = r;

				// Get the opposite leg of the triangle
				double leg2 = Math.sqrt(hypot * hypot - leg1 * leg1);

				// Find angle of the point of intersection on the circle
				double theta = Math.atan2(leg2, leg1);

				// Get the area of the 8 triangles in the intersection
				double triArea = 8.0 * (leg1 * leg2 / 2.0);

				// Get the area of the 4 arcs
				double arcArea = (r * r / 2) * ((PI * 2.0) - 8.0 * theta);
				
				// Print the answer
				out.printf("%.02f%n", triArea + arcArea);
				
				// double ans = 0;
				// // Check the answer using riemann sum
				// for (int i = 0; i < NUM_TESTS; i++){
				// double x = (i * l / NUM_TESTS) - (l / 2);
				//
				// double y = Math.min(Math.sqrt(r * r - x * x), l / 2);
				//
				// ans += y * (l / NUM_TESTS);
				// }
				// System.out.printf("%.02f%n", ans * 2);
				
			}
		}
		out.close();
	}

	// Check if two values are equal within some error
	private static boolean equal(double a, double b) {
		// Check absolute
		if (Math.abs(a - b) < EPSILON) {
			return true;
		}

		// Check relative
		if (Math.abs(a - b) < EPSILON * Math.max(a, b)) {
			return true;
		}
		return false;
	}
}
