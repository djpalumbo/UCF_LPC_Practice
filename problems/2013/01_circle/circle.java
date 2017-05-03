// Arup Guha
// 8/9/2013
// Solution to 2013 UCF Local Contest Problem: Circle

import java.util.*;
import java.io.*;

public class circle {

	// Create a coordinate system with (0,0) at the center of the square. You find that the coordinate
	// of the top right corner is half of this factor times the radius. To build the coordinate system,
	// notice that (0,0) is the center of the diamond with side 2r, so the coordinate of the middle top
	// center is (0, r(sqrt(2))). From there, draw out the equilateral triangle formed by the centers of
	// the three top right circles, to notice that there's a 15 degree pitch up to get to the top right
	// center circle. Finally, from there, move up and to the right by r.
	final static double factor = (2*Math.cos(15.0/180*Math.PI) + 1)*2;

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("circle.in"));
		int numCases = fin.nextInt();

		// Process each case, the output is the area of a square with size r*factor.
		for (int loop=0; loop<numCases; loop++) {
			double r = fin.nextDouble();
			System.out.printf("%.5f\n", factor*factor*r*r);
		}

		fin.close();
	}
}