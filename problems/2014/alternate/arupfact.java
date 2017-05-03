// Arup Guha
// 8/17/2014
// Solution to 2014 UCF Locals Problem: Factorial Products

import java.util.*;
import java.io.*;

public class arupfact {

	final public static int MAX = 2500;
	final public static double EPSILON = 1e-12;

	public static void main(String[] args) throws Exception {

		// We will store the logs of each factorial for lookup.
		double[] logFact = new double[MAX+1];
		for (int i=2; i<=MAX; i++)
			logFact[i] = logFact[i-1]+Math.log(i);

		Scanner stdin = new Scanner(new File("fact.in"));
		int numCases = stdin.nextInt();

		for (int loop=1; loop<=numCases; loop++) {

			int[] sizes = new int[3];
			for (int i=0; i<3; i++)
				sizes[i] = stdin.nextInt();

			// Store the logs of the factorial products in question.
			double[] logs = new double[3];
			for (int i=0; i<3; i++) {
				for (int j=0; j<sizes[i]; j++)
					logs[i] += logFact[stdin.nextInt()];
			}

			// Print the result here.
			System.out.println("Case #"+loop+": "+result(logs));
		}

		stdin.close();
	}

	// Returns desired output based on logs of the three list products.
	public static String result(double[] logs) {
		if (beat(logs[0], logs[1]) && beat(logs[0], logs[2])) return "A";
		if (beat(logs[1], logs[0]) && beat(logs[1], logs[2])) return "B";
		if (beat(logs[2], logs[0]) && beat(logs[2], logs[1])) return "C";
		return "TIE";
	}

	public static boolean beat(double a, double b) {
		return (a - b)/a > EPSILON;
	}
}
