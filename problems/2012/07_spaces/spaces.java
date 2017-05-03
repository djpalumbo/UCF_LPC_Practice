// Arup Guha
// 6/29/2013
// Solution to 2012 UCF Locals Problem: Spaces

import java.util.*;

public class spaces {

	public static pair[] dp;
	public static HashSet<String> prefixes;
	public static HashMap<String,Integer> dictionary;
	public static String sentence;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {

			sentence = stdin.next();
			int size = stdin.nextInt();
			prefixes = new HashSet<String>();
			dictionary = new HashMap<String,Integer>();

			// Store each word and each prefix.
			for (int i=0; i<size; i++) {
				String item = stdin.next();
				int score = stdin.nextInt();
				dictionary.put(item, score);

				for (int j=1; j<=item.length(); j++)
					prefixes.add(item.substring(0,j));
			}

			// Set up our memoization table.
			dp = new pair[sentence.length()+1];
			for (int i=0; i<dp.length; i++)
				dp[i] = null;
			dp[dp.length-1] = new pair(0,-1);

			// Run our recursive, memoized solution.
			int ans = solve(0);

			// Output answer.
			System.out.println("Inscription #"+loop+":");
			System.out.println(ans);

			// Reconstruct sentence.
			int i = 0;
			while (i < sentence.length()) {

				// Get next word.
				System.out.print(sentence.substring(i, dp[i].nextIndex));
				i = dp[i].nextIndex;

				// Only print space if that wasn't the last word.
				if (i < sentence.length()) System.out.print(" ");
			}
			System.out.println("\n");
		}
	}

	// Returns the best score for sentence[index..length-1].
	public static int solve(int index) {

		// Solved this case already.
		if (dp[index] != null) return dp[index].score;

		int bestScore = -1, bestIndex = -1;

		// Iterate through all ending points for the next word.
		for (int i=index+1; i<=sentence.length(); i++) {

			String curWord = sentence.substring(index, i);

			// This word is in the dictionary, so we could build off of it.
			if (dictionary.containsKey(curWord)) {

				int tempScore = dictionary.get(curWord) + solve(i);

				// Update both values if this option is better than previous ones.
				// The strictly greater than takes care of the tie breaker since we
				// are iterating from shorter to longer first words.
				if (tempScore > bestScore) {
					bestScore = tempScore;
					bestIndex = i;
				}
			}

			// Helps us speed this up. If this isn't a prefix of some dictionary word,
			// There's no point in us looking further.
			if (!prefixes.contains(curWord)) break;
		}

		// Store and return.
		dp[index] = new pair(bestScore, bestIndex);
		return bestScore;
	}

}

class pair {

	public int score;
	public int nextIndex;

	public pair(int s, int index) {
		score = s;
		nextIndex = index;
	}
}