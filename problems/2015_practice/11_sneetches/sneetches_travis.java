import java.util.*;
import java.io.*;

public class sneetches_travis {
	// Main Method
	public static void main(String[] Args) throws Exception {
//		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter out = new PrintWriter(new BufferedWriter(
//				new OutputStreamWriter(System.out)));
		BufferedReader sc = new BufferedReader(new FileReader(new File("sneetches.in")));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File("test.out"))));

		int t = Integer.parseInt(sc.readLine());

		// Use this to be more like Matt. Must channel his inner Interval
		// Tree constructor.
		while (t-- > 0) {
			String[] head = sc.readLine().split(" ");

			int n = Integer.parseInt(head[0]);

			// Use qq instead of queries to look like one of the good
			// competative programmers
			int qq = Integer.parseInt(head[1]);

			String s = sc.readLine();
			if (s.length() != n) {
				System.out.println("LOL");
				return;
			}
			IntervalTree it = new IntervalTree(n);
			for (int k = 0; k < n; k++) {
				if (s.charAt(k) == 'S') {
					it.update(k, k);
				} else if (s.charAt(k) != 'P') {
					System.out.println("CAT");
					return;
				}
			}
			for (int k = 0; k < qq; k++) {
				String[] curQ = sc.readLine().split(" ");
				int b = Integer.parseInt(curQ[0]) - 1;
				int e = Integer.parseInt(curQ[1]) - 1;
				it.update(b, e);
				int[] ret = it.query(0, n - 1, 0);
				out.println(ret[0] + " " + ret[1]);
			}
		}
	}

	public static class IntervalTree {
		int[] left_child;
		int[] right_child;
		int[] hi;
		int[] lo;
		int[] ends;
		int[] begins;
		boolean[] delta;
		int[] answer;
		int[] n_answer;
		int num_nodes;

		IntervalTree(int n) {
			left_child = new int[2 * n];
			right_child = new int[2 * n];
			hi = new int[2 * n];
			lo = new int[2 * n];
			answer = new int[2 * n];
			n_answer = new int[2 * n];
			ends = new int[2 * n];
			begins = new int[2 * n];
			delta = new boolean[2 * n];
			init(0, n - 1);
		}

		void init(int l, int r) {
			int index = 0;

			hi[num_nodes] = r;
			lo[num_nodes] = l;
			answer[num_nodes] = 0;
			n_answer[num_nodes] = (r - l + 1);
			ends[num_nodes] = -(r - l + 1);
			begins[num_nodes] = -(r - l + 1);
			delta[num_nodes] = false;
			index = num_nodes;
			num_nodes++;
			if (r == l) {
				return;
			}
			left_child[index] = num_nodes;
			init(l, (l + r) / 2);

			right_child[index] = num_nodes;
			init((l + r) / 2 + 1, r);
		}

		void propogate(int i) {
			if (delta[i]) {
				int tmp = n_answer[i];
				n_answer[i] = answer[i];
				answer[i] = tmp;
				ends[i] = -ends[i];
				begins[i] = -begins[i];
				delta[left_child[i]] = !delta[left_child[i]];
				delta[right_child[i]] = !delta[right_child[i]];
				delta[i] = false;
			}
		}

		int[] query(int l, int h, int i) {
			int[] ans = new int[4];

			if (hi[i] < l || h < lo[i]) {
				ans[0] = 0;
				ans[1] = 0;
				ans[2] = 0;
				ans[3] = 0;
				return ans;
			}

			update(i);
			if (l <= lo[i] && hi[i] <= h) {
				ans[0] = answer[i];
				ans[1] = n_answer[i];
				ans[2] = begins[i];
				ans[3] = ends[i];
				return ans;
			}

			int lc = left_child[i];
			int rc = right_child[i];

			int[] lans = query(l, h, lc);
			int[] rans = query(l, h, rc);

			ans[0] = (lans[0] > rans[0]) ? lans[0] : rans[0];
			ans[1] = (lans[1] > rans[1]) ? lans[1] : rans[1];
			ans[0] = (ans[0] > lans[3] + rans[2]) ? ans[0] : lans[3] + rans[2];
			ans[1] = (ans[1] > -lans[3] - rans[2]) ? ans[1] : -lans[3]
					- rans[2];

			ans[2] = lans[2]
					+ ((-lans[2] == hi[lc] - lo[lc] + 1 && rans[2] < 0) ? rans[2]
							: 0)
					+ ((lans[2] == hi[lc] - lo[lc] + 1 && rans[2] > 0) ? rans[2]
							: 0);
			ans[3] = rans[3]
					+ ((-rans[3] == hi[rc] - lo[rc] + 1 && lans[3] < 0) ? lans[3]
							: 0)
					+ ((rans[3] == hi[rc] - lo[rc] + 1 && lans[3] > 0) ? lans[3]
							: 0);

			return ans;
		}

		void update(int l, int h) {
			update(l, h, 0);
		}

		void update(int l, int h, int i) {
			if (hi[i] < l || h < lo[i]) {
				return;
			}
			if (l <= lo[i] && hi[i] <= h) {
				delta[i] = !delta[i];
				propogate(i);
				return;
			}

			if (lo[i] != hi[i]) {
				update(l, h, left_child[i]);
				update(l, h, right_child[i]);
			}
			propogate(i);
			update(i);
		}

		void update(int i) {
			if (hi[i] != lo[i]) {
				int lc = left_child[i];
				int rc = right_child[i];

				propogate(lc);
				propogate(rc);
				begins[i] = begins[lc]
						+ (begins[lc] == hi[lc] - lo[lc] + 1 && begins[rc] > 0 ? begins[rc]
								: 0)
						+ (-begins[lc] == hi[lc] - lo[lc] + 1 && begins[rc] < 0 ? begins[rc]
								: 0);
				ends[i] = ends[rc]
						+ (ends[rc] == hi[rc] - lo[rc] + 1 && ends[lc] > 0 ? ends[lc]
								: 0)
						+ (-ends[rc] == hi[rc] - lo[rc] + 1 && ends[lc] < 0 ? ends[lc]
								: 0);

				answer[i] = (answer[lc] > answer[rc]) ? answer[lc] : answer[rc];
				n_answer[i] = (n_answer[lc] > n_answer[rc]) ? n_answer[lc]
						: n_answer[rc];

				answer[i] = (answer[i] > ends[lc] + begins[rc]) ? answer[i]
						: ends[lc] + begins[rc];
				n_answer[i] = (n_answer[i] > -ends[lc] - begins[rc]) ? n_answer[i]
						: -ends[lc] - begins[rc];
			}
		}
	}

}
