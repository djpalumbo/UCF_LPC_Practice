
/*

This problem requires a little graph theory knowledge. Your given some flights
that are guaranteed not to loop back to a city. Determine a time ordering
of the flights so that a flight doesn't leave before another going to that city
lands!

To get the optimal order we can simply simulate the flights. First look at all
the cities. If a city isn't expecting a flight then we can take it. Reduce
the number of flights its destination city is expecting. We can repeat this
process until no flights remain. The problem is we need the earliest such
ordering. To speed things up we keep a priority queue that contains flights
from cities that aren't expecting any more flights. Taking the first element
of this queue will give us the best ordering of flights possible.

*/

import java.util.*;
import java.io.*;

public class airways
{
   public static void main(String[] args) throws Exception
   {
      new airways(new Scanner(new File("airways.in")));
   }

   int id_ptr;
   HashMap<String, Integer> idMap;
   int getId(String name)
   {
      Integer res = idMap.get(name);
      if (res == null)
      {
         res = id_ptr++;
         idMap.put(name, res);
      }   
      return res;
   }

   public airways(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         id_ptr = 0;
         idMap = new HashMap<String,Integer>();
         int M = in.nextInt();
         ArrayList<int[]> edges = new ArrayList<int[]>();
         while (M-->0)
         {
            int i = getId(in.next());
            int j = getId(in.next());
            int f = in.nextInt();
            edges.add(new int[]{i,j,f});
         }
         
         Graph g = new Graph(id_ptr);
         for (int[] e : edges)
            g.add(e[0], e[1], e[2]);

         int[] indeg = new int[g.N];
         for (int i=0; i<g.N; i++)
            for (Edge e : g.adj[i])
               indeg[e.j]++;
         PriorityQueue<Edge> q = new PriorityQueue<Edge>();
         for (int i=0; i<g.N; i++)
            if (indeg[i] == 0)
               for (Edge e : g.adj[i])
                  q.add(e);

         StringBuilder res = new StringBuilder();
         while (q.size() > 0)
         {
            Edge e = q.poll();
            res.append(e.flight);
            res.append(' ');
            if (--indeg[e.j]==0)
               for (Edge nxt : g.adj[e.j])
                  q.add(nxt);
         }
         System.out.println(res.toString().trim());
      }
   }
}

class Graph
{
   int N;
   ArrayList<Edge>[] adj;

   public Graph(int n)
   {
      adj = new ArrayList[N=n];
      for (int i=0; i<N; i++)
         adj[i] = new ArrayList<Edge>();
   }

   void add(int i, int j, int fn)
   {
      adj[i].add(new Edge(j, fn));
   }
}

class Edge implements Comparable<Edge>
{
   int j, flight;

   public Edge(int jj, int ff)
   {
      j=jj; flight=ff;
   }

   public int compareTo(Edge rhs)
   {
      return flight-rhs.flight;
   }
}
