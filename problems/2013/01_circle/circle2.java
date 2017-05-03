// Arup Guha
// 8/9/2013
// Alternate Solution to 2013 UCF Local Contest Problem: Circle

import java.util.*;
import java.io.*;

public class circle2 {

	// Draw a line segment from the center of the square to the top right corner.
	// This line bisects a 45-45-90 triangle with hypotenuse 2r, a equilateral triangle
	// with side 2r (height sqrt(3)r) and a square of side r. The length of this segment
	// is then r (height of first triangle to the hypotenuse) + sqrt(3)r + sqrt(2)r.
	// Thus, the full diagonal is simply 2(1 + sqrt(2) + sqrt(3))r so the square has side
	// length that is this divided by sqrt(2), which is sqrt(2)(1 + sqrt(2) + sqrt(3)r.
	// Square this to obtain the answer.
	final static double factor = 2*Math.pow((1+Math.sqrt(2)+Math.sqrt(3)), 2);

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("circle.in"));
		int numCases = fin.nextInt();

		// Process each case, the output is the area of a square with size r*factor.
		for (int loop=0; loop<numCases; loop++) {
			double r = fin.nextDouble();
			System.out.printf("%.5f\n", factor*r*r);
		}

		fin.close();
	}
}