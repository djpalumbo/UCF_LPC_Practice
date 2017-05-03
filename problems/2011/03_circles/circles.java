// Arup Guha
// 8/15/2011
// Solution to UCF Local Contest Problem: Circles 

import java.util.*;
import java.io.*;

public class circles {
	
	public static void main(String[] args) throws Exception {
		
		Scanner fin = new Scanner(new File("circles.in"));
		
		int numCases = fin.nextInt();
		
		for (int i=0; i<numCases; i++) {
			
			// Read in the triangle data.
			int L1 = fin.nextInt();
			int L2 = fin.nextInt();
			double H = Math.sqrt(L1*L1 + L2*L2);
			
			// This is the radius of the largest circle.
			double R = (L1 + L2 - H)/2;
			
			// If we sum up all the diameters, we get this value. To get this
			// Draw a line from the center of the biggest circle to the vertex
			// where the hypotenuse meets the longer leg. This line contains
			// the diameter of all circles except for the first one. It just 
			// contains the radius of the first circle. So, if we take this
			// length and add the radius of the first circle, the result is
			// the sum of the diameters of all the circles.
			double sumAllDiameters = R + Math.sqrt(R*R + Math.pow(L2-R,2));
			
			// Since there is a constant ratio, c, between the radius of each circle,
			// we get the following equation:
			
			// 2r/(1-c) = SumAllDiameters
			
			// Now, we solve for 1-c:
			// 1-c = 2r/sumAllDiameters
			
			// Then we can solve for c:
			// c = 1 - 2r/SumAllDiameters
			double ratioRadii = 1 - 2*R/sumAllDiameters;
			
			double areaCircles = Math.PI*(R*R/(1-ratioRadii*ratioRadii));
			double areaTriangle = L1*L2/2.0;
			
			System.out.printf("Case #%d: %.4f\n\n", i+1, areaCircles/areaTriangle);
		}		
	}
}