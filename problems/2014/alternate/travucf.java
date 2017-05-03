import java.util.*;
import java.io.*;

public class travucf 
{
   // Stores the costs of merging on buildings
   public static int[] costs;
   
   // Stores the choose under modulo MOD
   public static long[][] choose;
   
   // Stores the constant modulo that is used in this problem
   public static long MOD = 1000000007;
   
   // Stores the maximum number of choose values
   public static int MAXC = 1001;
   
   // Store the adjacency list of ucf
   public static HashSet<Integer>[] adj;
   
   // The main function
   public static void main(String[] Args) throws FileNotFoundException
   {
      // The scanner that will handle all data input
      Scanner sc = new Scanner(new File("ucf.in"));
      
      // Read in the number of test cases
      int t = sc.nextInt();
      
      // Initialize the case counter as 0
      int cc = 0;
      
      // Precompute choose
      precomp();
      
      // Handle all the cases
      while (t-->0)
      {   
         // Read in the number of buildings
         int n = sc.nextInt();
         
         // Read in the number of connections
         int c = sc.nextInt();
         
         // Make the adjacency list
         adj = new HashSet[n];
         
         // Make the array for the costs
         costs = new int[n];
         
         // Read in the costs while building the adjacency list
         for (int k = 0; k < n; k++)
         {
            adj[k] = new HashSet<Integer>();
            costs[k] = sc.nextInt();
         }
         
         // Read in the connections
         for (int k = 0; k < c; k++)
         {
            int a = sc.nextInt()-1;
            int b = sc.nextInt()-1;
            adj[a].add(b);
            adj[b].add(a);
         }
         
         if (n == 2 && c == 1)
         {
            // Handle an "Edge" case :D
            int minB = 1;
            int minC = Math.min(costs[0], costs[1]);
            int num = 1;
            
            // Check an edge case of the edge case
            if (costs[0] == costs[1])
               num = 2;
            
            // Output results for this case
            System.out.printf("Case #%d: %d %d %d%n", ++cc, minB, minC, num);
         }
         else if (n == c + 1)
         {
            // Handle a tree case
            int minB = 1;
            int minC = 0;
            int num = 0;
            
            // Mark the leaves
            boolean[] isLeaf = new boolean[n];
            for (int k = 0; k < n; k++)
               isLeaf[k] = (adj[k].size() == 1);

            // Remove leaves to make later computations easier
            for (int k = 0; k < n; k++)
               if (isLeaf[k])
                  for (Integer j : adj[k])
                     adj[j].remove(k);

            // Get the cost by summing the values of the non-leaf nodes
            for (int k = 0; k < n; k++)
               if (!isLeaf[k])
                  minC += costs[k];
            
            // Get the number of ways to compress by compressing on all non-leaf
            // nodes
            for (int k = 0; k < n; k++)
               if (!isLeaf[k]){
                  num += dfs(-1, k)[0];
                  num %= MOD;
               }
            
            // Output results
            System.out.printf("Case #%d: %d %d %d%n", ++cc, minB, minC, Math.max(num,1));
         }
         else
         {
            // Handle a non-tree
            // NOTE: This is a hard case, and makes the problem worth the tag
            int minB = 0;
            int minC = 0;
            int num = 0;
            
            // Get the degrees of the buildings
            int[] degrees = new int[n];
            Queue<Integer> q = new LinkedList<Integer>();
            for (int k = 0; k < n; k++)
            {
               // Get the degree
               degrees[k] = adj[k].size();
               
               // Add the node to the list of buildings that need to be 
               // destroyed 
               if (degrees[k] == 1)
               {
                  q.add(k);
                  
                  // offset the costs by the cost of this node, because this
                  // node will never be merged on
                  minC -= costs[k];
               }
            }
            
            // Turn the degrees into the degrees after a full compression
            while (!q.isEmpty())
            {
               // Get the current node
               int cur = q.poll();
               
               // Look at all nodes that this node was/is connected to
               for (Integer x : adj[cur])
               {
                  // Check if this is the one node that is still connected to
                  // the current node
                  if (degrees[x] != 0)
                  {
                     // "Remove" the edge
                     degrees[x]--;
                     degrees[cur]--;
                     
                     // Add x if it is now a leaf
                     if (degrees[x] == 1)
                        q.add(x);
                  }
               }
            }
            
            // Loop through each node and find the nodes that have changed 
            // degrees, because they need to have their cost added (they have
            // been merged unless they are a leaf, but we offset the cost 
            // earlier)
            for (int k = 0; k < n; k++)
               if (degrees[k] != adj[k].size())
                  minC += costs[k];
            
            // Find the nodes that are in the graph still; these will be the
            // building left over
            for (int k = 0; k < n; k++)
               if (degrees[k] != 0)
                  minB++;
         
            // Mark the leaves
            boolean[] isLeaf = new boolean[n];
            for (int k = 0; k < n; k++)
               isLeaf[k] = (adj[k].size() == 1);
            
            // Mark nodes that are to be used for a merge
            boolean[] isMarked = new boolean[n];
            for (int k = 0; k < n; k++)
               if (degrees[k] != adj[k].size())
                  isMarked[k] = true;

            // Find a leaf for some magic
            int magicNode = -1;
            for (int k = 0; k < n; k++)
            {
               if (isLeaf[k])
               {
                  magicNode = k;
               }
            }
            
            // Remove leaves to make later computations easier
            for (int k = 0; k < n; k++)
               if (isLeaf[k])
                  for (Integer j : adj[k])
                     adj[j].remove(k);
            
            // Remove the edges that are left after compress, they is useless
            for (int k = 0; k < n; k++)
               if (degrees[k] != 0)
               {
                  // Prepare a holder for the edges to be removed
                  ArrayList<Integer> al = new ArrayList<Integer>();
                  
                  // Get the nodes that should be disconnected
                  for (Integer j : adj[k])
                     if (degrees[j] != 0)
                        al.add(j);
                  
                  // Remove them
                  for (Integer j : al)
                  {
                     adj[k].remove(j);
                     adj[j].remove(k);
                  }
               }
            
            // Check that there was compression
            if (magicNode != -1)
            {
               // Remove any remaining edges; the node needs to be clean
               if (adj[magicNode].size() != 0)
               {
                  // Prepare a holder for the edges to be removed
                  ArrayList<Integer> al = new ArrayList<Integer>();

                  // Get the nodes that should be disconnected
                  for (Integer j : adj[magicNode])
                        al.add(j);

                  // Remove them
                  for (Integer j : al)
                  {
                     adj[magicNode].remove(j);
                     adj[j].remove(magicNode);
                  }
               }
               // Hook up the last nodes to be merged to this magic node
               for (int k = 0; k < n; k++)
               {
                  if (isMarked[k] && degrees[k] != 0)
                  {
                     adj[magicNode].add(k);
                  }
               }
               
               // Do the dfs from this magical node
               num = (int)dfs(-1, magicNode)[0];
            }
            
            // Output the results
            System.out.printf("Case #%d: %d %d %d%n", ++cc, minB, minC, Math.max(num,1));
         }
      }
   }
   private static long[] dfs(int par, int cur) 
   {
      // first index is the number of ways to compress and the second 
      // is the number of nodes that have been compressed
      long[] ans = new long[2];
      ans[0] = 1;
      ans[1] = 0;
      for(Integer k : adj[cur])
      {
         if(k != par)
         {
            long[] tans = dfs(cur, k);
            ans[1] += tans[1];
            ans[0] %= MOD;
            ans[0] *= tans[0];
            ans[0] %= MOD;
            ans[0] *= choose[(int)ans[1]][(int)tans[1]];
            ans[0] %= MOD;
         }
      }
      ans[1]++;
      return ans;
   }
   private static void precomp() 
   {
      // Allocate memory for choose
      choose = new long[MAXC][MAXC];
      
      // Loop through all possible large values for choose
      for(int k = 0; k < MAXC; k++)
      {   
         // Allocate memory for this choose row
         choose[k] = new long[k+1];
         
         // Handle base cases
         choose[k][0] = 1;
         choose[k][k] = 1; 
         
         // Handle the non-base cases
         for(int j = 1; j < k; j++)
            choose[k][j] = (choose[k-1][j] + choose[k-1][j-1]) % MOD;
      }
   }
}
