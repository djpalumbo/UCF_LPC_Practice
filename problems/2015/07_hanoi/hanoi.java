// Arup Guha
// 8/22/2015
// Solution to 2015 UCF Locals Problem: Towers of Hanoi Grid (hanoi)

import java.util.*;

public class hanoi {

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Process all cases.
		for (int loop=1; loop<=numCases; loop++) {

			int discs = stdin.nextInt();
			int n = stdin.nextInt();

			// Impossible case.
			if (discs > (n-1)*(n-1)+1)
				System.out.println("Grid #"+loop+": impossible\n");

			// Otherwise, there's a nice tidy formula - each disc moves 2n-2 steps, doing a grid walk.
			else
				System.out.println("Grid #"+loop+": "+(discs*(2*n-2))+"\n");
		}
	}

}