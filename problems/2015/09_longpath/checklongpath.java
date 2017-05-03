// Arup Guha
// 8/29/2015
// Checker for UCF Locals Problem: Longpath

import java.util.*;
import java.io.*;

public class checklongpath {

	public static void main(String[] args) throws Exception {

		// args[0] = real input
		// args[1] = student output
		// args[2] = real output

		Scanner inp = new Scanner(new File(args[0]));
		BufferedReader studout = new BufferedReader(new FileReader(args[1]));

        // Get from input.
		int numCases = inp.nextInt();

        // Go through cases.
		for (int loop=1; loop<=numCases; loop++) {

            // Read in graph.
			int n = inp.nextInt();
			int[][] g = new int[n][n];
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					g[i][j] = inp.nextInt();

            // Make sure input is there.
			String line = "";
			if (studout.ready()) line = studout.readLine();
			else {
				System.out.println("Case "+loop+": Not enough output.");
				break;
			}

            // Must have n tokens.
			StringTokenizer tok = new StringTokenizer(line);
			if (tok.countTokens() != n) {
				System.out.println("Case "+loop+": Not correct number of vertices.");
				break;
			}

            // Store the path and the frequency of each vertex in these arrays.
			int[] list = new int[n];
			int[] freq = new int[n];
			boolean bad = false;

			// Screen out invalid locations and update frequency array.
			for (int i=0; i<n; i++) {
				list[i] = Integer.parseInt(tok.nextToken());
				if (list[i] < 1 || list[i] > n) {
                    System.out.println("Case "+loop+": Invalid location.");
                    bad = true;
                    break;
				}
                freq[list[i]-1]++;
			}
			if (bad) break;

			// Look for revisited location.
			for (int i=0; i<n; i++) {
                if (freq[i] != 1) {
                    System.out.println("Case "+loop+": Revisited location.");
                    bad = true;
                    break;
                }
            }
			if (bad) break;

            // Now, check to see if this path is valid.
			for (int i=0; i<n-1; i++) {

				if (g[list[i]-1][list[i+1]-1] != 1) {
					System.out.println("Case "+loop+": Walking an invalid path.");
					bad = true;
					break;
				}
			}

			if (bad) break;
		}
	}
}
