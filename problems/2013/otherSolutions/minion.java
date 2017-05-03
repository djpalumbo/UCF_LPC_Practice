
/*

In this problem we are asked to determine if a minion can reach location N-1 
from location 0. A minion cannot take a route if he is not able to accomplish
that trial. 

We can set up a graph representing where the minion can go. That graph is an
adjacency matrix. Only add the edge if the trial is not a bad one. Then simply
run the floyd warshall algorithm to determine where he can eventually go.
Since the number of locations is small, O(N^3) is fine! Then check if the minion
can reach N-1 from 0 in the resulting matrix.

*/

import java.util.*;
import java.io.*;

public class minion
{
   public static void main(String[] args) throws Exception
   {
      new minion(new Scanner(new File("minion.in")));
   }

   public minion(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         TreeSet<String> badThings = new TreeSet<String>();
         int N = in.nextInt();
         while (N-->0)
            badThings.add(in.next());
         N = in.nextInt();
         int M = in.nextInt();
         boolean[][] adj = new boolean[N][N];
         while (M-->0)
         {
            int i = in.nextInt();
            int j = in.nextInt();
            String s = in.next();
            if (!badThings.contains(s))
               adj[i][j] = adj[j][i] = true;
         }
         for (int k=0; k<N; k++)
            for (int i=0; i<N; i++)
               for (int j=0; j<N; j++)
                  adj[i][j] |= adj[i][k] & adj[k][j];
         System.out.println(adj[0][N-1] ? 1 : 0);
      }
   }
}
