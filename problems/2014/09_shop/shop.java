// Arup Guha
// 8/17/2014
// Solution to 2014 UCF Locals Problem: Shopping Spree

import java.util.*;
import java.io.*;

public class shop {

	public static void main(String[] args) throws Exception {

		Scanner stdin = new Scanner(new File("shop.in"));
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {

			// Read in data.
			int n = stdin.nextInt();
			int[] vals = new int[n];
			for (int i=0; i<n; i++)
				vals[i] = stdin.nextInt();

			// Special case.
			if (n == 1) {
				System.out.println("Spree #"+loop+": "+vals[0]);
				continue;
			}

			// Store answers here as follows:
			// dp[i][j] represents the maximal value knapsack for the subset up to item i,
			// inclusive, with j or fewer items in it.
			int[][] dp = new int[n][n/2+1];
			dp[0][1] = vals[0];

			// This is it. dp[i-1][j] means, don't take this item. Alternatively,
			// we want the best of at most j-1 items out of 0 to i-1 plus item i.
			for (int i=1; i<n; i++)
				for (int j=1; j<=Math.max(1, (i+1)/2); j++)
					dp[i][j] = Math.max(dp[i][j-1], Math.max(dp[i-1][j], dp[i-1][j-1]+vals[i]));

			System.out.println("Spree #"+loop+": "+dp[n-1][n/2]);
		}

		stdin.close();
	}
}