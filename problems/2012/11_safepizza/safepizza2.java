// Arup Guha
// 8/31/2012
// Solution to 2012 UCF Locals Problem: Safe Pizza

import java.io.*;
import java.util.*;

// Stores a single pizza.
class pizza {

	public String name;
	public int dailyDose;
	public int numSlices;
	public int current;

	public pizza(String n, int daily, int total) {
		name = n;
		dailyDose = daily;
		numSlices = total;
		current = total;
	}

	public pizza(pizza other) {
		name = new String(other.name);
		dailyDose = other.dailyDose;
		numSlices = other.numSlices;
		current = other.numSlices;
	}

	public void next() {
		if (dailyDose > current)
			current = numSlices - (dailyDose - current);
		else
			current -= dailyDose;
	}

	public double weight() {
		return (double)current/numSlices;
	}

	public String toString() {
		return name;
	}
}

class arrangement {

	public pizza[] perm;

	// Note: index 0 stores top of the stack...
	public arrangement(pizza[] p) {
		perm = p;
	}

	// Return the number of adjacent pairs of boxes that are out of order.
	public int outOfOrder() {
		
		int cnt = 0;

		for (int i=0; i<perm.length-1; i++) {

			// These get skipped.
			if (perm[i].current == 0) continue;

			// Find the next non-zero box.
			int j = i+1;
			while (j < perm.length && perm[j].current == 0) j++;

			// No non-empty box below...
			if (j == perm.length) break;

			// These consecutive boxes are out of order.
			if (perm[i].weight() > perm[j].weight()) cnt++;
		}

		return cnt;
	}

	// Advance one day, for all pizzas.
	public void next() {
		for (int i=0; i<perm.length; i++)
			perm[i].next();
	}
}

public class safepizza {

	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("safepizza.in"));

		// Go through each case.
		int numCases = fin.nextInt();
		for (int loop=1; loop<=numCases; loop++) {

			int numPizzas = fin.nextInt();
			int numDays = fin.nextInt();

			// Read in all pizzas.
			pizza[] list = new pizza[numPizzas];
			for (int i=0; i<numPizzas; i++) {
				String s = fin.next();
				int day = fin.nextInt();
				int size = fin.nextInt();
				list[i] = new pizza(s, day, size);
			}

			// Print out the best solution.
			int[] best = solve(list, numDays);
			System.out.println("Leftover Pizza Stacking Order for Semester "+loop+":");
			for (int i=0; i<best.length; i++)
				System.out.println(list[best[i]]);
			System.out.println();
		}

		fin.close();
	}

	public static int[] solve(pizza[] list, int numDays) {

		int[] bestperm = new int[list.length];
		int[] perm = new int[list.length];
		for (int i=0; i<perm.length; i++) {
			perm[i] = i;
			bestperm[i] = i;
		}
		int minBadDays = 10000;

		// Go through each permutation.
		for (int i=0; i<fact(perm.length); i++) {

			// Figure out the number of bad days in this arrangement.
			int badDays = sim(list, perm, numDays);

			// Update if necessary.
			if (badDays < minBadDays) {
				bestperm = Arrays.copyOf(perm, perm.length);
				minBadDays = badDays;
			}
			else if (badDays == minBadDays && tieBreak(perm, bestperm, list)) {
				bestperm = Arrays.copyOf(perm, perm.length);
			}

			// Go to the next permutation.
			nextPerm(perm);
		}

		return bestperm;
	}

	// Runs the full simulation of numDays days, of the permutation perm of the
	// pizzas in list.
	public static int sim(pizza[] list, int[] perm, int numDays) {

		// Make a deep copy here.
		pizza[] temp = new pizza[list.length];
		for (int i=0; i<perm.length; i++)
			temp[i] = new pizza(list[perm[i]]);

		arrangement fridge = new arrangement(temp);
		int cnt = 0;

		// Run through all the days.
		for (int i=0; i<numDays; i++) {

			fridge.next();

			// Count the bad boxes on this day.
			cnt += fridge.outOfOrder();
		}

		return cnt;
	}

	// Returns true iff perm comes before bestperm, in terms of the alphabetical comparison.
	public static boolean tieBreak(int[] perm, int[] bestperm, pizza[] list) {

		for (int i=0; i<perm.length; i++) {
			int ans = list[perm[i]].name.compareTo(list[bestperm[i]].name);
			if (ans < 0)
				return true;
			else if (ans > 0)
				return false;
		}
		return false;
	}

	public static void nextPerm(int perm[]) {

		int length = perm.length;

    	// Find the spot that needs to change.
    	int i = length-1;
    	while (i>0 && perm[i] < perm[i-1]) i--;
    	i--; // Advance to the location that needs to be swapped.

    	// So last perm doesn't cause a problem.
    	if (i == -1) return;

    	// Find the spot with which to swap index i.
    	int j=length-1;
    	while (j>i && perm[j]<perm[i]) j--;

    	// Swap it.
    	int temp = perm[i];
    	perm[i] = perm[j];
    	perm[j] = temp;

   		// reverse from index i+1 to length-1.
   		int k,m;
   		for (k=i+1,m=length-1; k<m; k++,m--) {
       		temp = perm[k];
       		perm[k] = perm[m];
       		perm[m] = temp;
    	}
	}

	public static int fact(int n) {
		int ans = 1;
		for (int i=1; i<=n; i++)
			ans = ans*i;
		return ans;
	}


}