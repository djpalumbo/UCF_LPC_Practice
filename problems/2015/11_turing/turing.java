// Arup Guha
// 8/29/2015
// Solution to UCF Locals Problem: Turing's Challenge

import java.util.*;

public class turing {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Process each case.
		for (int loop=0; loop<numCases; loop++) {

			long x = stdin.nextLong();
			long n = stdin.nextLong();

			// Easy case, all terms but first drop out.
			if (x%4 == 0) System.out.println(0);

			// All terms but first two always drop out.
			else if (x%4 == 2) {
				if (n%2 == 0) 	System.out.println(0);
				else			System.out.println(3);
			}

			// This is the interesting case, answer is equal to answer for Pascal's Triangle, by itself.
			else {

				// Other impossible case - binary representation of n is all 1s - all terms are 1 or 3.
				if (pow2(n+1)) System.out.println(0);

				// Finally, here we have a larger answer.
				else {
					long sumIndex13 = (1L << (Long.bitCount(n)-1))*(n+2);
					System.out.println(sumIndex13+max2(n));
				}
			}
		}
	}

    // Returns the largest index that stores the value 2 in Pascal's Triangle, row n, mod 4.
	public static long max2(long n) {

		// Calculate the number of times 2 divides into n!
		long totalTwos = getTwos(n);

		// Now, we'll try to find the number of times 2 divides into (n-val)! and val!, where
		// val is a perfect power of 2 itself.
		for (long val=1L; val<n; val<<=1) {

			// Get sum of both.
			long rest = n - val;
			long thisWay = getTwos(val) + getTwos(rest);

			// Trigger the answer.
			if (totalTwos - 1 == thisWay)
				return rest+1;
		}

		// To make the compiler happy.
		return -1;
	}

    // Returns true iff n is a perfect power of 2.
	public static boolean pow2(long n) {

		// Get first power of 2 >= val.
		long val = 1L;
		while (val < n) val = (val << 1);

		// Means that n is a perfect power of two.
		return val == n;
	}

	// Returns the number of times 2 divides into n!
	public static long getTwos(long n) {
		long res = 0;
		while (n > 0) {
			res += (n/2L);
			n /= 2L;
		}
		return res;
	}
}
