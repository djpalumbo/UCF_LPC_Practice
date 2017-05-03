// Arup Guha
// 6/27/2013
// Solution to 2012 UCF Local Contest Problem: Rummy

import java.util.*;

public class rummy2 {

	final public static int MAX = 13;
	final public static int NUMCARDS = 7;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through all of the cases.
		for (int loop=0; loop<numCases; loop++) {

			// Store a hand in two ways: normal and frequencies.
			int[] hand = new int[MAX+1];
			int[] order = new int[NUMCARDS];
			for (int i=0; i<NUMCARDS; i++) {
				order[i] = stdin.nextInt();
				hand[order[i]]++;
			}

			// Output result.
			System.out.print("Rummy Hand:");
			for (int i=0; i<NUMCARDS; i++)
				System.out.print(" "+order[i]);
			System.out.println();
			System.out.println(score(hand));
			System.out.println();
		}
	}

	public static int score(int[] freq) {

		int[] same = new int[2];
		int numSame = 0;
		for (int i=0; i<freq.length; i++) {
			if (freq[i] >= 3) {
				same[numSame] = i;
				numSame++;
			}
		}

		// Greedy case works here. If you have 6 cards used this way,
		// you'll never use 7 cards another way.
		if (numSame == 2) {
			int ans = 0;
			for (int i=0; i<freq.length; i++)
				if (freq[i] < 3)
					ans += (i*freq[i]);

			return ans;
		}

		// Any score is better than 1000...
		int temp = 1000, best = 1000;

		// Try using the 3 or more of a kind here.
		if (numSame == 1) {

			// Adjust frequency of the 3 or more of a kind.
			int save = freq[same[0]];
			int start = freq[same[0]] - 3;

			// Try each number of a kind from 3 on.
			while (start >= 0) {
				freq[same[0]] = start;
				temp = getRunScore(freq);
				if (temp < best) best = temp;
				start--;
			}

			// Restore the frequency array.
			freq[same[0]] = save;
		}

		// Try again, without any 3 or more of a kind.
		temp = getRunScore(freq);
		if (temp < best) best = temp;

		// This is the best we could do.
		return best;
	}

	// Get the best score of this hand stored as a frequency without 3 or more of a kind.
	public static int getRunScore(int[] freq) {

		// So we don't destroy the original.
		int[] copy = Arrays.copyOf(freq, freq.length);

		// Subtract all runs.
		while (hasRun(copy));

		// Return what's left.
		int sum = 0;
		for (int i=0; i<copy.length; i++)
			sum += (i*copy[i]);

		return sum;

	}

	public static boolean hasRun(int[] freq) {

		// i is the start of the run.
		for (int i=0; i<freq.length; i++) {

			// Get the longest run starting at i.
			int run = 0, end = -1;
			for (int j=i; j<freq.length; j++) {
				if (freq[j] == 0) {
					if (run >= 3) end = j-1;
					break;
				}
				run++;
			}

			// Subtract out this run.
			if (run >= 3) {
				while (freq[end] > 0) {
					freq[end]--;
					end--;
				}
				return true;
			}

		}

		// Never found a run.
		return false;
	}
}