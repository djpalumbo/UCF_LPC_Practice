// Arup Guha
// 8/10/06
// Solution to 2006 UCF Local Contest Problem: Etch

import java.util.*;
import java.io.*;

public class etch {
	
	// Just supposed to be a very small value.
	final static double EPSILON = 0.000000000001;
	
	public static void main(String[] args) throws IOException {

		Scanner fin = new Scanner(new File("etch.in"));
		int numcases = fin.nextInt();
		
		// Loop through all of the input cases.
		for (int i=1; i<=numcases; i++) {
			
			// Read in the data for the case.
			double f1, f2, a, b, c;
			f1 = fin.nextDouble();
			f2 = fin.nextDouble();
			a = fin.nextDouble();
			b = fin.nextDouble();
			c = fin.nextDouble();
			
			// Find the answer and print it out properly formatted.
			double ans = findTime(f1, f2, a, b, c);
			System.out.printf("Crystal #%d: %.2f\n\n",i,ans);
		}
			
		fin.close(); // Close the input file.
	}
	
	// This function doesn't analytically determine the correct time. Instead, since
	// the function is monotonically increasing, it does a binary search until it
	// gets pretty close to the actual value.
	public static double findTime(double f1, double f2, double a, double b, double c) {
		
		// This is the value we want the function on the right-hand side to evaluate to.
		double targettime = (f2-f1)/(f1*f2);
		
		// The crystal has to etch for more than 0 seconds.
		double low = 0;
		
		// Based on the formula, since c is positive, we know that the amount of
		// etch time is less than if we divided the target by a, because this would
		// be the correct answer if we didn't take into account the contribution
		// to the function of the exponential part.
		double high = targettime/a;
		
		// Essentially, low represents a value lower than the actual etch time
		// and high represents a value greater than the actual etch time.
		
		// Assigning mid a value so the compiler doesn't yell.
		double mid = 0;
	
		// Keep on refining the guess until high and low are very close together.
		while (high - low > EPSILON) {
		
		    // Guess in the middle of our boundary.
			mid = (high+low)/2;
			double ans = f(a,b,c,mid);
			
			// If this guess is too low, adjust our low bound.
			if (ans<targettime)
				low = mid;
			
			// Real lucky if your guess is perfect.
			else if (ans == targettime)
				break;
				
			// Otherwise adjust the high bound.
			else
				high = mid;
		}
		
		return mid; // This is the answer we want to return.
	}
	
	// Just calculates the function in the problem description 
	// in terms of a,b,c and t.
	public static double f(double a, double b, double c, double t) {
		return a*t+b*(1-Math.exp(-c*t));
	}
}