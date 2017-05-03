// Arup Guha
// 8/17/2014
// Alternate Solution to 2014 UCF Locals Problem: Shopping Spree
// Note: This uses a greedy solution. We maintain a priority queue and always
//       take the best item we're allowed to take at any time.

import java.util.*;
import java.io.*;

public class shop2 {

	public static void main(String[] args) throws Exception {

		Scanner stdin = new Scanner(new File("shop.in"));
		int numCases = stdin.nextInt();

		// Go through each case.
		for (int loop=1; loop<=numCases; loop++) {

			// Read in data.
			int n = stdin.nextInt();

			// We can take the first item, no matter what.
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
			int ans = stdin.nextInt();
			pq.offer(ans);

			// Process the rest of the items.
			for (int i=2; i<=n; i++) {

				// By default, add this in.
				int next = stdin.nextInt();

				// We're allowed to have a new item, so take it!
				if (i%2 == 0 && i > 2) {
					pq.offer(next);
					ans += next;
				}

				// See if this item is bigger than our least, if so, exchange.
				else {

					int low = pq.peek();
					if (next > low) {
						ans = ans - low + next;
						pq.poll();
						pq.offer(next);
					}
				}
			}

			// Here is our answer.
			System.out.println("Spree #"+loop+": "+ans);
		}

		stdin.close();
	}
}