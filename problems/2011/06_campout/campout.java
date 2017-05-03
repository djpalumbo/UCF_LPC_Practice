// Arup Guha
// 8/31 - 9/2/2011
// Solution to 2011 UCF Locals Contest Problem: Campout

import java.io.*;
import java.util.*;

// Handles an edge for the networf flow algorithm.
class Edge {

	// Information we need for one edge.
	private int capacity;
	private int flow;

	// No flow at first.
	public Edge(int cap) {
		capacity = cap;
		flow = 0;
	}

	// Returns what we can push forward through this edge.
	public int maxPushForward() {
		return capacity - flow;
	}

	// Returns what we can push backwards through this edge.
	public int maxPushBackward() {
		return flow;
	}

	// Pushes moreflow through this edge, if the capacity is there.
	// If not, false is returned and nothing is done.
	public boolean pushForward(int moreflow) {

		// We can't push through this much flow.
		if (flow+moreflow > capacity)
			return false;

		// Push through.
		flow += moreflow;
		return true;
	}

	// Pushes lessflow back against this edge, if the capacity is there.
	// If not, false is returned and nothing is done.
	public boolean pushBack(int lessflow) {

		// Not enough to push back on.
		if (flow < lessflow)
			return false;

		flow -= lessflow;
		return true;
	}
}

// Stores the direction of an edge for the network flow algorithm.
class direction {

	// Stores the previous node visited in an augmenting path and
	// the direction to traverse that edge.
	public int prev;
	public boolean forward;

	public direction(int node, boolean dir) {
		prev = node;
		forward = dir;
	}

	public String toString() {
		if (forward)
			return "" + prev + "->";
		else
			return "" + prev + "<-";
	}
}

class networkflow {

	private Edge[][] adjMat;
	private int source;
	private int dest;

	// All positive entries in flows should represent valid flows
	// between vertices. All other entries must be 0 or negative.
	public networkflow(int[][] flows, int start, int end) {

		// Stores graph as an adjacency matrix of edges.
		source = start;
		dest = end;
		adjMat = new Edge[flows.length][flows.length];

		for (int i=0; i<flows.length; i++) {
			for (int j=0; j<flows[i].length; j++) {

				// Fill in this flow.
				if (flows[i][j] > 0)
					adjMat[i][j] = new Edge(flows[i][j]);
				else
					adjMat[i][j] = null;
			}
		}
	}

	public ArrayList<direction> findAugmentingPath() {

		// This will store the previous node visited in the BFS.
		direction[] prev = new direction[adjMat.length];
		boolean[] inQueue = new boolean[adjMat.length];
		for (int i=0; i<inQueue.length; i++)
			inQueue[i] = false;

		// The source has no previous node.
		prev[source] = new direction(-1, true);

		LinkedList<Integer> bfs_queue = new LinkedList<Integer>();
		bfs_queue.offer(new Integer(source));
		inQueue[source] = true;

		// Our BFS will go until we clear out the queue.
		while (bfs_queue.size() > 0) {

			// Add all the new neighbors of the current node.
			Integer next = bfs_queue.poll();


			// Find all neighbors and add into the queue. These are forward edges.
			for (int i=0; i<adjMat.length; i++)
				if (!inQueue[i] && adjMat[next][i] != null && adjMat[next][i].maxPushForward() > 0) {
					bfs_queue.offer(new Integer(i));
					inQueue[i] = true;
					prev[i] = new direction(next, true);
				}

			// Now look for back edges.
			for (int i=0; i<adjMat.length; i++)
				if (!inQueue[i] && adjMat[i][next] != null && adjMat[i][next].maxPushBackward() > 0) {
					bfs_queue.offer(new Integer(i));
					inQueue[i] = true;
					prev[i] = new direction(next, false);
				}
		}

		// No augmenting path found.
		if (!inQueue[dest])
			return null;

		ArrayList<direction> path = new ArrayList<direction>();

		direction place = prev[dest];

		direction dummy = new direction(dest, true);
		path.add(dummy);

		// Build the path backwards.
		while (place.prev != -1) {
			path.add(place);
			place = prev[place.prev];
		}

		// Reverse it now.
		Collections.reverse(path);

		return path;
	}

	// Run the Max Flow Algorithm here.
	public int getMaxFlow() {

		int flow = 0;

		ArrayList<direction> nextpath = findAugmentingPath();

		// Loop until there are no more augmenting paths.
		while (nextpath != null) {

			// Check what the best flow through this path is.
			int this_flow = Integer.MAX_VALUE;
			
			// This loop just finds the minimum value of all the extra capacities in the edges on this path.
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].maxPushForward());
				}
				else {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].maxPushBackward());
				}
			}

			// Now, put this flow through.
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					adjMat[nextpath.get(i).prev][nextpath.get(i+1).prev].pushForward(this_flow);
				}
				else {
					adjMat[nextpath.get(i+1).prev][nextpath.get(i).prev].pushBack(this_flow);
				}
			}

			// Add this flow in and then get the next path.
			flow += this_flow;
			nextpath = findAugmentingPath();
		}

		return flow;
	}

}

public class campout {

	public static void main(String[] arg) throws Exception {

		Scanner fin = new Scanner(new File("campout.in"));
		int numCases = fin.nextInt();

		// Go through each case.
		for (int caseNum=1; caseNum<=numCases; caseNum++) {

			// Size of our flow graph.
			int[][] graph = new int[54][54];

			// Sets that no student can man the tent for more than 80 hours.
			for (int i=1; i<=10; i++)
				graph[0][i] = 20;

			// We just need three students to man the tent for each time slot.
			for (int i=11; i<=52; i++)
				graph[i][53] = 3;

			// Read in all 10 students' info.
			for (int stud=1; stud<=10; stud++) {

				// Initialize each slot so that this student can cover it.
				boolean[] cancover = new boolean[42];
				for (int i=0; i<42; i++)
					cancover[i] = true;

				int numConflicts = fin.nextInt();

				// Go through each conflict.
				for (int con=0; con<numConflicts; con++) {

					int d = fin.nextInt();
					int s = fin.nextInt();
					int e = fin.nextInt();

					// Each time maps to one of the time ranges, so we just go through these and make them false.
					for (int i=(d-1)*6+s/4; i<(d-1)*6+(e+3)/4; i++)
						cancover[i] = false;
				}

				// Fill in shifts student can do.
				for (int i=0; i<42; i++)
					if (cancover[i])
						graph[stud][11+i] = 1;

			}

			// Get the flow of this network.
			networkflow mine = new networkflow(graph, 0, 53);
			int maxflow = mine.getMaxFlow();

			System.out.print("Case #"+caseNum+": ");

			// This means all of 42 shifts were covered by at least 3 students each.
			if (maxflow == 126)
				System.out.println("YES");
			else
				System.out.println("NO");

			System.out.println();

		}

		fin.close();
	}

}