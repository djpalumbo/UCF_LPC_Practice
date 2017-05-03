// Arup Guha
// 8/19/2013
// Solution to 2013 UCF Local Contest Problem: Jump
import java.util.*;
import java.io.*;

public class jump {

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("jump.in"));
		int numCases = fin.nextInt();

		// Go through each case.
		for (int loop=0; loop<numCases; loop++) {

			// Get the input.
			int x1 = fin.nextInt();
			int y1 = fin.nextInt();
			int x2 = fin.nextInt();
			int y2 = fin.nextInt();

			// This transformation preserves the gcd exactly (assuming gcd(0,0) is 0). So check for equal gcds.
			// It can be shown that we can reach any square with the same gcd of coordinates.
			if (gcd(Math.abs(x1), Math.abs(y1)) == gcd(Math.abs(x2), Math.abs(y2)))
				System.out.println("1");
			else
				System.out.println("0");
		}

		fin.close();
	}

	// Usual gcd function, must have gcd(0,0) return 0.
	public static int gcd(int a, int b) {
		if (a == 0) return b;
		if (b == 0) return a;
		return gcd(b, a%b);
	}
}