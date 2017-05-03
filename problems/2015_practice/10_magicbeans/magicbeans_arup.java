// Arup Guha
// 6/24/2015
// Solution to SI@UCF Competition Camp Conteset #5 Problem B: Magic Beans

import java.util.*;

public class magicbeans_arup {

	final public static long MOD = 1000000007L;
	final public static int MAXSUB = 16;

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int numCases = stdin.nextInt();

		// Will come in handy for counting later.
		long[] fact = new long[33];
		fact[0] = 1L;
		for (int i=1; i<fact.length; i++)
			fact[i] = (fact[i-1]*i)%MOD;

		// Process each case.
		for (int loop=0; loop<numCases; loop++) {

			int n = stdin.nextInt();
			long dx = stdin.nextLong();
			long dy = stdin.nextLong();
			bean target = new bean(dx, dy);

			// Read in 1st half of beans and get where you can go with those.
			bean[] left = new bean[n/2];
			for (int i=0; i<left.length; i++) {
				long x = stdin.nextLong();
				long y = stdin.nextLong();
				left[i] = new bean(x,y);
			}
			bean[] leftSub = getSubsets(left);
			TreeMap<bean,Integer> leftMap = getMap(leftSub);
			long[][] numLeftSubsets = getSubList(leftMap, leftSub);

			// Do same for right half.
			bean[] right = new bean[n-n/2];
			for (int i=0; i<right.length; i++) {
				long x = stdin.nextLong();
				long y = stdin.nextLong();
				right[i] = new bean(x,y);
			}
			bean[] rightSub = getSubsets(right);
			TreeMap<bean,Integer> rightMap = getMap(rightSub);
			long[][] numRightSubsets = getSubList(rightMap, rightSub);

			long res = 0;

			// Go through each possible outcome from the left side.
			for (bean b: leftMap.keySet()) {

				// Where we need to go.
				bean need = target.subtract(b);

				// Add in our matches (the number of b in left times the number of need in right)
				if (rightMap.containsKey(need)) {

					int leftIndex = leftMap.get(b);
					int rightIndex = rightMap.get(need);

					// Now we must look through all combos of different subset lengths from left and right.
					for (int i=0; i<MAXSUB+1; i++) {
						if (numLeftSubsets[leftIndex][i] == 0) continue;

						// We are creating freq combinations of beans that work where each of those combinations
						// has exactly i+j beans.
						for (int j=0; j<MAXSUB+1; j++) {
							int bits = i+j;
							long freq = numLeftSubsets[leftIndex][i]*numRightSubsets[rightIndex][j];
							res = (res + (freq%MOD)*fact[bits])%MOD;
						}
					}
				}
			}

			// Ta da!
			System.out.println(res);
		}
	}

	// Returns an array such that long[x][y] stores number of ways we can get to point x using
	// a subset of exactly y beans.
	public static long[][] getSubList(TreeMap<bean,Integer> map, bean[] list) {

		// Relatively easy since i stores the mask here.
		long[][] res = new long[map.size()][MAXSUB+1];
		for (int i=0; i<list.length; i++) {
			int index = map.get(list[i]);
			res[index][Integer.bitCount(i)]++;
		}
		return res;
	}

	// Returns an array with all subsets of items in list. Each subset is stored as one
	// bean which is where you end up if you eat those beans.
	public static bean[] getSubsets(bean[] list) {

		int n = list.length;
		bean[] res = new bean[1<<n];

		// Fill in each subset.
		for (int i=0; i<res.length; i++) {

			// Calculate the sum of beans from list corresponding to the mask i.
			res[i] = new bean(0,0);
			for (int bit=0; bit<n; bit++)
				if ((i & (1<<bit)) > 0)
					res[i] = res[i].add(list[bit]);
		}

		// This is what we want.
		return res;
	}

	// Does a backwards mapping from each unique point in list to an index.
	public static TreeMap<bean,Integer> getMap(bean[] list) {

		TreeMap<bean,Integer> res = new TreeMap<bean,Integer>();
		int index = 0;

		// Go through each item - just store each unique one.
		for (int i=0; i<list.length; i++)
			if (!res.containsKey(list[i]))
				res.put(list[i], index++);

		return res;
	}
}

class bean implements Comparable<bean> {

	public long x;
	public long y;

	public bean(long myx, long myy) {
		x = myx;
		y = myy;
	}

	public bean add(bean other) {
		return new bean(x+other.x, y+other.y);
	}

	public bean subtract(bean other) {
		return new bean(x-other.x, y-other.y);
	}

	public int compareTo(bean other) {
		if (x < other.x) return -1;
		if (x > other.x) return 1;
		if (y < other.y) return -1;
		if (y > other.y) return 1;
		return 0;
	}
}
