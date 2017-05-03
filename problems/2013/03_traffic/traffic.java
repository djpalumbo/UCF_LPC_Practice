// Arup Guha
// 8/4/2013
// Solution to 2013 UCF Locals Problem: Traffic

import java.util.*;
import java.io.*;

public class traffic {

	final public static int MAX = 1000000;

	public static void main(String[] args) throws Exception {

		Scanner stdin = new Scanner(new File("traffic.in"));
		int numCases = Integer.parseInt(stdin.nextLine().trim());

		// Process each case.
		for (int loop=0; loop<numCases; loop++) {

			StringTokenizer tok = new StringTokenizer(stdin.nextLine());
			int n = Integer.parseInt(tok.nextToken());
			int e = Integer.parseInt(tok.nextToken());

			// Set up storage of street names and edges.
			HashMap<String,Integer> codes = new HashMap<String,Integer>();
			int streetNo = 0;
			ArrayList[] allEdges = new ArrayList[n];
			for (int i=0; i<n; i++)
				allEdges[i] = new ArrayList<Edge>();

			// Read in data.
			for (int i=0; i<e; i++) {
				tok = new StringTokenizer(stdin.nextLine(),",");
				int from = Integer.parseInt(tok.nextToken());
				int to = Integer.parseInt(tok.nextToken());
				int flow = Integer.parseInt(tok.nextToken());

				String street = tok.nextToken();
				allEdges[from].add(new Edge(flow, to, street));

				// Add this code, if necessary.
				if (!codes.containsKey(street))
					codes.put(street, streetNo++);
			}

			// Make reverse lookup.
			String[] streetNames = new String[streetNo];
			for (String key: codes.keySet())
				streetNames[codes.get(key)] = key;

			// Copy into 2D array.
			Edge[][] graph = new Edge[n][];
			int[][] capacities = new int[n][];
			for (int i=0; i<n; i++) {
				graph[i] = new Edge[allEdges[i].size()];
				capacities[i] = new int[allEdges[i].size()];
				for (int j=0; j<allEdges[i].size(); j++) {
					graph[i][j] = ((Edge)allEdges[i].get(j));
					capacities[i][j] = ((Edge)allEdges[i].get(j)).capacity;
				}
			}

			// Establish current flow here.
			netflow orig = new netflow(graph, 0, n-1);
			int baseline = orig.getMaxFlow();

			// Answer will be stored here.
			int best = 0;
			String bestStr = "";

			// Try augmenting each street.
			for (int i=0; i<streetNames.length; i++) {

				// Zero out the flow in the graph.
				String myStr = streetNames[i];
				orig.zeroFlow();

				// Update edges so widened street has maximum capacity (based on input limits)
				for (int j=0; j<orig.adjMat.length; j++) {
					for (int k=0; k<orig.adjMat[j].length; k++) {
						if (orig.adjMat[j][k].name.equals(myStr))
							orig.adjMat[j][k].capacity = MAX;
						else
							orig.adjMat[j][k].capacity = capacities[j][k];
					}
				}

				// Update if this street is better than anything in the past.
				int nextFlow = orig.getMaxFlow();
				if (nextFlow - baseline > best) {
					best = nextFlow - baseline;
					bestStr = myStr;
				}
			}

			// Output our solution.
			System.out.println(bestStr.trim()+" "+best);
		}

		stdin.close();
	}
}

/*** My network flow code follows below ***/
class Edge {

	public int dest;
	public int capacity;
	public int flow;
	public String name;

	public Edge(int cap, int d, String s) {
		capacity = cap;
		flow = 0;
		dest = d;
		name = s;
	}

	public int maxPushForward() {
		return capacity - flow;
	}

	public int maxPushBackward() {
		return flow;
	}

	public boolean pushForward(int moreflow) {

		// We can't push through this much flow.
		if (flow+moreflow > capacity)
			return false;

		// Push through.
		flow += moreflow;
		return true;
	}

	public boolean pushBack(int lessflow) {

		// Not enough to push back on.
		if (flow < lessflow)
			return false;

		flow -= lessflow;
		return true;
	}
}

class direction {

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

class netflow {

	public Edge[][] adjMat;
	private int source;
	private int dest;
	private HashMap[] lookup;
	private LinkedList[] backEdgeLookup;

	// Input matrix is edge list.
	public netflow(Edge[][] matrix, int start, int end) {

		// Set up easy stuff.
		adjMat = matrix;
		source = start;
		dest = end;
		lookup = new HashMap[matrix.length];

		// Allocate empty LLs.
		backEdgeLookup = new LinkedList[matrix.length];
		for (int i=0; i<matrix.length; i++)
			backEdgeLookup[i] = new LinkedList<Integer>();

		// Fill these in.
		for (int i=0; i<adjMat.length; i++) {

			lookup[i] = new HashMap<Integer,Integer>();
			for (int j=0; adjMat[i] != null && j<adjMat[i].length; j++) {
				lookup[i].put(adjMat[i][j].dest, j);
				backEdgeLookup[adjMat[i][j].dest].offer(i);
			}
		}
	}

	public void zeroFlow() {
		for (int i=0; i<adjMat.length; i++)
			for (int j=0; j<adjMat[i].length; j++)
				adjMat[i][j].flow = 0;
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

			if (adjMat[next] == null) continue;

			// Find all neighbors and add into the queue. These are forward edges.
			for (int i=0; i<adjMat[next].length; i++) {

				int item = adjMat[next][i].dest;
				if (!inQueue[item] && adjMat[next][i].maxPushForward() > 0) {
					bfs_queue.offer(item);
					inQueue[item] = true;
					prev[item] = new direction(next, true);
				}
			}

			// Now look for back edges.
			for (int i=0; i<backEdgeLookup[next].size(); i++) {

				int item = (Integer)backEdgeLookup[next].pollFirst();
				if (!inQueue[item] && lookup[item].containsKey(next) && adjMat[item][(Integer)(lookup[item].get(next))].maxPushBackward() > 0) {
					bfs_queue.offer(item);
					inQueue[item] = true;
					prev[item] = new direction(next, false);
				}
				backEdgeLookup[next].offer(item);
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
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i).prev][(Integer)lookup[nextpath.get(i).prev].get(nextpath.get(i+1).prev)].maxPushForward());
				}
				else {
					this_flow = Math.min(this_flow, adjMat[nextpath.get(i+1).prev][(Integer)lookup[nextpath.get(i+1).prev].get(nextpath.get(i).prev)].maxPushBackward());
				}
			}

			// Now, put this flow through.
			for (int i=0; i<nextpath.size()-1; i++) {

				if (nextpath.get(i).forward) {
					adjMat[nextpath.get(i).prev][(Integer)lookup[nextpath.get(i).prev].get(nextpath.get(i+1).prev)].pushForward(this_flow);
				}
				else {
					adjMat[nextpath.get(i+1).prev][(Integer)lookup[nextpath.get(i+1).prev].get(nextpath.get(i).prev)].pushBack(this_flow);
				}
			}

			// Add this flow in and then get the next path.
			flow += this_flow;
			nextpath = findAugmentingPath();
		}

		return flow;
	}
}