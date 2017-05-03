import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class airways {

	// Using the classical topological sort approach, this problem can be solved
	// in O(cities + flights log flights)
	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(new File("airways.in"));

		int cases = in.nextInt();

		for (int runs = 0; runs < cases; runs++) {
			int size = in.nextInt();

			ArrayList<edge> el = new ArrayList<edge>();

			// Use this to keep track of all the cities
			HashSet<String> cities = new HashSet<String>();

			// This is our adjacency list
			HashMap<String, PriorityQueue<edge>> adjList = new HashMap<String, PriorityQueue<edge>>();

			// keep track of the number of flights coming into this city.
			HashMap<String, Integer> inboundCount = new HashMap<String, Integer>();

			for (int i = 0; i < size; i++) {
				String a = in.next();
				String b = in.next();
				int f = in.nextInt();

				if (cities.add(a)) {
					inboundCount.put(a, 0);
					adjList.put(a, new PriorityQueue<airways.edge>());
				}
				if (cities.add(b)) {
					inboundCount.put(b, 0);
					adjList.put(b, new PriorityQueue<airways.edge>());
				}

				el.add(new edge(a, b, f));
			}

			// Find the inbound count for each city and temporarily disregard
			// cities that have flights coming in
			for (edge e : el) {
				adjList.get(e.s).add(e);
				cities.remove(e.t);
				inboundCount.put(e.t, inboundCount.get(e.t) + 1);
			}

			// edges will be processed on a queue
			PriorityQueue<edge> q = new PriorityQueue<edge>();

			// for each city that has no inbound flights, add all their outbound
			// flights to the queue
			for (String c : cities) {
				q.addAll(adjList.get(c));
			}

			// use this to store the output
			StringBuilder sb = new StringBuilder();

			while (!q.isEmpty()) {
				// Get the head of the queue
				edge e = q.poll();

				// reduce the inbound count of the destination of the processed
				// flight
				inboundCount.put(e.t, inboundCount.get(e.t) - 1);

				// add the flight to the output
				sb.append(" ").append(e.f);

				// if the current destination city no longer has any inbound
				// flights, add all of its outbound flights to the queue to
				// process
				if (inboundCount.get(e.t) == 0) {
					q.addAll(adjList.get(e.t));
				}
			}

			System.out.println(sb.substring(1));

		}

	}

	public static class edge implements Comparable<edge> {
		String s;
		String t;
		int f;

		public edge(String a, String b, int c) {
			s = a;
			t = b;
			f = c;
		}

		@Override
		public int compareTo(edge o) {
			return f - o.f;
		}
	}
}
