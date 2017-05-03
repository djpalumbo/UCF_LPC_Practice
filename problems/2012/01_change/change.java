// Arup Guha
// 8/26/2012
// Solution to 2012 UCF Locals Contest Problem: Change

import java.util.*;
import java.io.*;

public class change {

	public static void main(String[] args) throws Exception {

		// Open file and get number of cases.
		Scanner fin = new Scanner(new File("change.in"));
		int numCases = fin.nextInt();

		// Go through each case.
		for (int i=1; i<=numCases; i++) {

			// Read in coin set.
			int numCoins = fin.nextInt();
			int[] denom = new int[numCoins];
			for (int j=0; j<numCoins; j++)
				denom[j] = fin.nextInt();

			// Used to verify that input data is valid.
			if (invalidSum(denom))
				System.out.println("Set #"+i+": Sum is too big");

			// Output the solution.
			System.out.println("Set #"+i+": "+solve(denom));
			System.out.println();
		}

		fin.close();
	}

	// Returns the smallest positive integer value that can't be represented
	// as the sum of items in denom.
	public static int solve(int[] denom) {

		// Needed for our greedy algorithm.
		Arrays.sort(denom);

		// Basically, the current sum+1 is the maximum value the next number
		// must be. If it's bigger, we know we can't hit this current target exactly.
		int sum = 0, curTarget = 1, index = 0;
		while (index < denom.length && denom[index] <= curTarget) {
			sum += denom[index];
			curTarget = sum+1;
			index++;
		}

		return curTarget;
	}

	public static boolean invalidSum(int[] array) {
		long sum = 0;
		for (int i=0; i<array.length; i++)
			sum = sum + array[i];

		return sum > 1000000000L;
	}
}