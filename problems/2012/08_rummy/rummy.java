// Arup Guha
// 7/1/2013
// Solution to 2012 UCF Local Contest Problem: Rummy

import java.util.*;

public class rummy {

	final public static int NUMCARDS = 7;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through all of the cases.
		for (int loop=0; loop<numCases; loop++) {

			// Store the hand
			int[] hand = new int[NUMCARDS];
			for (int i=0; i<NUMCARDS; i++)
				hand[i] = stdin.nextInt();

			// Output result.
			System.out.print("Rummy Hand:");
			for (int i=0; i<NUMCARDS; i++)
				System.out.print(" "+hand[i]);
			System.out.println();
			System.out.println(score(hand, (1 << 7)-1));
			System.out.println();
		}
	}

	public static int score(int[] hand, int curMask) {

		// Set up best score to be all cards, so that none are used.
		int n = hand.length;
		int best = 0;
		for (int i=0; i<n; i++)
			if (((curMask >> i) & 1) == 1)
				best += hand[i];

		// Go through each subset for the first "match"
		for (int mask=1; mask<=curMask; mask++) {

			// Only allow using at least three cards that are currently in our hand.
			int bits = Integer.bitCount(mask);
			if (bits < 3 || (mask | curMask) != curMask) continue;

			// Try this match recursively and update if we get a better score.
			if (isMatch(hand, mask)) {
				int temp = score(hand, curMask-mask);
				if (temp < best)
					best = temp;
			}
		}

		// This is the best we could do.
		return best;
	}

	// Get the best score of this hand stored as a frequency without 3 or more of a kind.
	public static boolean isMatch(int[] hand, int mask) {
		return isRun(hand, mask) || isSet(hand, mask);
	}

	// Returns the list of cards specified by mask.
	public static ArrayList<Integer> getCards(int[] hand, int mask) {

		// Pick the ones in the mask.
		ArrayList<Integer> cards = new ArrayList<Integer>();
		for (int i=0; i<hand.length; i++)
			if (((mask >> i) & 1) == 1)
				cards.add(hand[i]);

		return cards;
	}

	// Returns true if the cards in hand specified by mask form a run.
	public static boolean isRun(int[] hand, int mask) {

		// Get the cards.
		ArrayList<Integer> cards = getCards(hand, mask);

		// Sort and see if they are consecutive.
		Collections.sort(cards);
		for (int i=0; i<cards.size()-1; i++)
			if (cards.get(i+1) - cards.get(i) != 1)
				return false;

		// If we get here, they are.
		return true;
	}

	public static boolean isSet(int[] hand, int mask) {

		// Get the cards.
		ArrayList<Integer> cards = getCards(hand, mask);

		// Look for unequal cards.
		for (int i=0; i<cards.size()-1; i++)
			if (cards.get(i+1) != cards.get(i))
				return false;

		// If we get here, we pass.
		return true;
	}
}