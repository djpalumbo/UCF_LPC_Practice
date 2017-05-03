// Arup Guha
// 8/29/2012
// Solution to 2012 UCF Locals Problem: Call Me Maybe

import java.util.*;
import java.io.*;

class entry {

	public String word;
	public LinkedList<Integer> locations;

	// Create an empty entry for w.
	public entry(String w) {
		word = w;
		locations = new LinkedList<Integer>();
	}

	// I am storing each word as a single integer: 10000 x speech + wordnum
	public void addToEnd(int speech, int wordnum) {
		locations.offerLast(10000*speech + wordnum);
	}

	// Advance to the next occurrence of this word in the speeches by
	// moving the front node of this LL to the back!
	public void next() {
		Integer temp = locations.pollFirst();
		locations.offerLast(temp);
	}

	// This is the string representation I want, for convenience of output...
	// For general use, this is awful.
	public String toString() {
		int code = locations.peekFirst();
		int speechNum = code/10000;
		int wordNum = code%10000;
		return speechNum+" "+wordNum;
	}

}

// Just used to sort each word in a set of speeches.
class term implements Comparable<term> {

	public String word;
	public int speech;
	public int wordnum;

	public term(String w, int sp, int num) {
		word = w;
		speech = sp;
		wordnum = num;
	}

	// Sort procedure: first alpha, then by speech number, then word number.
	public int compareTo(term other) {
		int ans = word.compareTo(other.word);
		if (ans != 0) return ans;
		if (speech != other.speech) return speech - other.speech;
		return wordnum - other.wordnum;
	}
}

public class callme {

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("callme.in"));

		int numCases = fin.nextInt();

		for (int loop=1; loop<=numCases; loop++) {

			int numSpeeches = fin.nextInt();

			// Max size. Will resize after reading speeches.
			term[] tempArray = new term[numSpeeches*1000];
			int index = 0;

			// Read in each word of each speech into the temp array.
			for (int i=1; i<=numSpeeches; i++) {
				int numWords = fin.nextInt();
				for (int j=1; j<=numWords; j++,index++) {
					String str = fin.next();
					tempArray[index] = new term(str, i, j);
				}
			}

			// Resize this array.
			term[] speechWords = new term[index];
			for (int i=0; i<index; i++)
				speechWords[i] = tempArray[i];

			// Now, we've sorted the words.
			Arrays.sort(speechWords);

			// In this format, we can create our spoof easily.
			entry[] lookup = createLookUp(speechWords);

			// Finally, we can read in the lyrics!
			int numWords = fin.nextInt();
			String[] popSong = new String[numWords];
			for (int i=0; i<numWords; i++)
				popSong[i] = fin.next();

			// We need to figure out if we can solve it, before we try.
			if (canSolve(popSong, lookup)) {
				System.out.println("Spoof #"+loop+":");
				solve(popSong, lookup);
			}
			else {
				System.out.println("Spoof #"+loop+": NOT POSSIBLE");
			}

			System.out.println();

		}

		fin.close();
	}

	// Creates an array of entries, where each entry is a unique word with
	// a linked list of all the places that word appears.
	public static entry[] createLookUp(term[] speechWords) {

		// We need this to size the array properly.
		int count = countUniqueWords(speechWords);
		entry[] table = new entry[count];

		count = 0;
		int i = 0;

		// i is my index into speechWords, count will index into table.
		while (i < speechWords.length) {

			// Start of the next unique word.
			table[count] = new entry(speechWords[i].word);

			int start = i;

			// Add each word position for this word.
			while (i < speechWords.length && speechWords[i].word.equals(speechWords[start].word)) {
				table[count].addToEnd(speechWords[i].speech, speechWords[i].wordnum);
				i++;
			}
			count++;
		}

		return table;
	}

	// Pre-condition: speechWords is sorted in alpha order and is non-empty.
	// Post-condition: returns the number of unique words stored in speechWords.
	public static int countUniqueWords(term[] speechWords) {

		int count = 0;
		int i = 0;

		// Just go through each unique word until we're done with the list.
		while (i < speechWords.length) {

			count++;
			int start = i;

			// Advance through all identical words.
			while (i < speechWords.length && speechWords[i].word.equals(speechWords[start].word)) i++;
		}

		return count;
	}

	public static boolean canSolve(String[] song, entry[] lookup) {

		// Looks to see if Carly will be sad that her song can't get spoofed!
		for (int i=0; i<song.length; i++)
			if (binSearch(song[i], lookup) == -1)
				return false;

		// We found all the words, yeah!
		return true;
	}

	// Precondition: each item in song is found in lookup.
	public static void solve(String[] song, entry[] lookup) {

		// Go through each word.
		for (int i=0; i<song.length; i++) {

			int index = binSearch(song[i], lookup);
			System.out.println(lookup[index]);

			// Advance to the next word.
			lookup[index].next();
		}
	}

	// Returns true iff w is found in lookup, which must be sored.
	public static int binSearch(String w, entry[] lookup) {

		// Initial bounds.
		int low = 0;
		int high = lookup.length-1;

		// Run the binary search.
		while (low <= high) {

			int mid = (low+high)/2;
			if (w.equals(lookup[mid].word))
				return mid;
			else if (w.compareTo(lookup[mid].word) < 0)
				high = mid-1;
			else
				low = mid+1;

		}

		// If we get here, we didn't find the word.
		return -1;
	}
}