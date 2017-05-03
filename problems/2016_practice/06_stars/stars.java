// Arup Guha
// 9/1/2010
// Solution to 2010 UCF Local Contest Problem: Stars

import java.io.*;
import java.util.*;

// Stores a single edge list.
class EdgeList {
	
	public int thisNode;
	public ArrayList<Integer> neighbors;
	
	public EdgeList(int vertex) {
		thisNode = vertex;
		neighbors = new ArrayList<Integer>();
	}
	
	public void addNeighbor(int neighbor) {
		neighbors.add(neighbor);
	}
	
	// For debugging.
	public String toString() {
		String ans = thisNode+": ";
		for (int i=0; i<neighbors.size(); i++)
			ans = ans + neighbors.get(i) +" ";
		return ans;
	}
}

// Stores a Graph and all the relevant information for this problem.
class Graph {
	
	// All the things we need to keep track of for this problem.
	public EdgeList[] allEdges;
	public boolean[] visited;
	public int numComponents;
	public int needFixing;
	int numVisited;
	
	// Initialize this graph to be empty with size vertices.
	public Graph(int size) {
		allEdges = new EdgeList[size];
		
		for (int i=0; i<size; i++)
			allEdges[i] = new EdgeList(i);
			
		numComponents = 0;
		numVisited = 0;
		needFixing = 0;
		
		visited = new boolean[size];
		for (int i=0; i<size; i++)
			visited[i] = false;
	}
	
	// Add an undirected edge between vertices start and end.
	public void addEdge(int start, int end) {
		allEdges[start].addNeighbor(end);
		allEdges[end].addNeighbor(start);
	}
	
	// Solves the given problem by doing a search from each vertex.
	public void countComponents() {
		
		for (int i=0; i<allEdges.length; i++) {
			
			// Only count components that have more than one piece.
			if (visited[i] == false && allEdges[i].neighbors.size() > 0) {
				
				numComponents++;
				
				// Mark this connected component.
				int saveVisited = numVisited;
				int numEdges = dfs(i)/2;
				int numVertices = numVisited - saveVisited;
				
				// Cycle detection for this problem is easy.
				if (numEdges >= numVertices)
					needFixing++;
			}
			
			// We still need to mark this guy.
			else
				visited[i] = true;
		}
		
	}
	
	// Regular DFS, but we also return the total number of edges incident to
	// each vertex that gets called in a connected component. This number is
	// twice the total number of edges.
	public int dfs(int startNode) {
	
		// Count is the sum of the degrees of each vertex reached by this dfs.
		int cnt = allEdges[startNode].neighbors.size();
		int savecnt = cnt;
		
		// Standard dfs code.
		visited[startNode] = true;
		numVisited++;
		
		// Recursively visit each unvisited neighbor.
		for (int i=0; i<savecnt; i++)
			if (visited[allEdges[startNode].neighbors.get(i)] == false)
				cnt += dfs(allEdges[startNode].neighbors.get(i));
					
		return cnt;
	}
	
	// For debugging.
	public String toString() {
		String ans = "";
		for (int i=0; i<allEdges.length; i++)
			ans = ans + allEdges[i].toString() + "\n";
		return ans;
	}
}

public class stars {
	
	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("stars.in"));
		
		// Go through each case.
		int n = fin.nextInt();
		for (int loop=1; loop<=n; loop++) {
			
			int numVertices = fin.nextInt();
			int numEdges = fin.nextInt();
			
			// Create the graph.
			Graph g = new Graph(numVertices);
			for (int i=0; i<numEdges; i++) {
				int v1 = fin.nextInt() - 1;
				int v2 = fin.nextInt() - 1;
				g.addEdge(v1, v2);
			}
			
			// Do the component search and output results.
			g.countComponents();
			System.out.println("Night sky #"+loop+": "+g.numComponents+" constellations, of which "+g.needFixing+" need to be fixed.\n");
		}		
	}
} 