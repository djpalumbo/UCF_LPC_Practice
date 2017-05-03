// Arup Guha
// 8/7/2015
// Solution to 2015 UCF Locals Problem: Longest Path

import java.util.*;
import java.io.*;

public class longpath {

    public static int n;
    public static boolean[][] graph;

    public static void main(String[] args) throws Exception {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int numCases = Integer.parseInt(stdin.readLine().trim());

        // Process each case.
        for (int loop=0; loop<numCases; loop++) {

            // Read in the graph.
            n = Integer.parseInt(stdin.readLine().trim());
            graph = new boolean[n][n];

            for (int i=0; i<n; i++) {
                StringTokenizer tok = new StringTokenizer(stdin.readLine());
                for (int j=0; j<n; j++)
                    graph[i][j] = tok.nextToken().equals("1");
            }

            // By default initially start at point 1 (stored as index 0 in the graph)
            ArrayList<Integer> res = new ArrayList<Integer>();
            res.add(0);

            // Iteratively insert point of interest i+1 into our path we're building.
            for (int i=1; i<n; i++)
                insert(res, i);

            // Follow printing specification.
            System.out.print(res.get(0)+1);
            for (int i=1; i<n; i++)
                System.out.print(" "+(res.get(i)+1));
            System.out.println();

        }
    }

    public static void insert(ArrayList<Integer> list, int item) {

        // Look for insertion point!
        int index = list.size()-1;
        while (index >= 0 && !graph[list.get(index)][item]) index--;

        // This always works!
        list.add(index+1, item);
    }
}
