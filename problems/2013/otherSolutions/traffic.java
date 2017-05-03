
/*

In this problem there is some traffic conjestion that you would like to solve
by expanding a road as large as possible. This problem can be solved with the
max flow algorithm run several times.

First set up the graph discribed in the problem and run max flow to get the
initial flow of traffic. Next loop through all possible roads and run flow
again after expanding that road to infinity. The answer will be the road
with the largest flow change. This is guaranteed by the problem to be unique.

*/

import java.util.*;
import java.io.*;

public class traffic
{
   public static void main(String[] args) throws Exception
   {
      new traffic(new Scanner(new File("traffic.in")));
   }

   TreeMap<String, ArrayList<TidalFlow.Edge>> edgeMap;
   public traffic(Scanner in)
   {
      int T = Integer.parseInt(in.nextLine());
      while (T-->0)
      {
         String[] ip = in.nextLine().split(" ");
         int N = Integer.parseInt(ip[0]);
         int M = Integer.parseInt(ip[1]);
         TidalFlow tf = new TidalFlow(N);
         int large = N*5000;
         tf.add(tf.s, 0, 300*N);
         tf.add(N-1, tf.t, 300*N);
         edgeMap = new TreeMap<String, ArrayList<TidalFlow.Edge>>();
         while (M-->0)
         {
            ip = in.nextLine().split(",");
            int i = Integer.parseInt(ip[0]);
            int j = Integer.parseInt(ip[1]);
            int c = Integer.parseInt(ip[2]);
            TidalFlow.Edge e = tf.add(i, j, c);
            if (ip.length != 4)
               System.out.println("BADNESS!!!!!!!");
            if (!ip[3].equals(ip[3].trim()))
               System.out.println("BAD ROAD!!!!!! "+ip[3]);
            ArrayList<TidalFlow.Edge> street = edgeMap.get(ip[3]);
            if (street == null)
            {
               street = new ArrayList<TidalFlow.Edge>();
               edgeMap.put(ip[3], street);
            }
            street.add(e);
         }
  
         int initialFlow = tf.getFlow();
         tf.reset();
         
         String res = null;
         int improvement = -1;
         int count = 0;
         for (String name : edgeMap.keySet())
         {
            ArrayList<TidalFlow.Edge> street = edgeMap.get(name);
            for (TidalFlow.Edge seg : street)
               seg.cap = large;

            int nxtFlow = tf.getFlow();
            int nxtImp = nxtFlow-initialFlow;
            if (nxtImp == improvement)
               count++;
            else if (nxtImp > improvement)
            {
               res = name;
               improvement = nxtImp;
               count = 1;
            }
            tf.reset();
            for (TidalFlow.Edge seg : street)
               seg.cap = seg.orig;
         }

         if (count != 1)
            System.out.println("BAD DATA!!!!!!!!!!! MULTIPLE SOLUTIONS");
         System.out.printf("%s %d%n", res, improvement);
      }
   }
}

class TidalFlow
{
   ArrayDeque<Edge> stk = new ArrayDeque<Edge>();
   int N, s, t, oo = 987654321, fptr, bptr;
   ArrayList<Edge>[] adj;
   int[] q, dist, pool;
    
   TidalFlow(int NN)
   {
      N=(t=(s=NN)+1)+1;
      adj = new ArrayList[N];
      for(int i = 0; i < N; adj[i++] = new ArrayList<Edge>());
      dist = new int[N];
      pool = new int[N];
      q = new int[N];
   }

   Edge add(int i, int j, int cap)
   {
      Edge fwd = new Edge(i, j, cap, 0);
      Edge rev = new Edge(j, i, 0, 0);
      adj[i].add(rev.rev=fwd);
      adj[j].add(fwd.rev=rev);
      return fwd;
   }
  
   void reset()
   {
      for (int i=0; i<N; i++)
         for (Edge e : adj[i])
            e.flow = 0;
   }

   int augment()
   {
      Arrays.fill(dist, Integer.MAX_VALUE);
      pool[t] = dist[s] = fptr = bptr = 0;
      pool[q[bptr++] = s] = oo;
      while(bptr > fptr && q[fptr] != t)
         for(Edge e : adj[q[fptr++]])
         {
            if(dist[e.i] < dist[e.j])
               pool[e.j] += e.carry = Math.min(e.cap - e.flow, pool[e.i]);
            if(dist[e.i] + 1 < dist[e.j] && e.cap > e.flow)
               dist[q[bptr++] = e.j] = dist[e.i] + 1;
         }
      if(pool[t] == 0) return 0;
      Arrays.fill(pool, fptr = bptr = 0);
      pool[q[bptr++] = t] = oo;
      while(bptr > fptr)
         for(Edge e : adj[q[fptr++]])
         {
            if(pool[e.i] == 0) break;
            int f = e.rev.carry = Math.min(pool[e.i], e.rev.carry);
            if(dist[e.i] > dist[e.j] && f != 0)
            {
               if(pool[e.j] == 0) q[bptr++] = e.j;
               pool[e.i] -= f;
               pool[e.j] += f;
               stk.push(e.rev);
            }
         }
      int res = pool[s];
      Arrays.fill(pool, 0);
      pool[s] = res;
      while(stk.size() > 0)
      {
         Edge e = stk.pop();
         int f = Math.min(e.carry, pool[e.i]);
         pool[e.i] -= f;
         pool[e.j] += f;
         e.flow += f;
         e.rev.flow -= f;
      }
      return res;
   }

   int getFlow()
   {
      int res = 0, f = 1;
      while(f != 0)
         res += f = augment();
      return res;
   }

   class Edge
   {
      int i, j, cap, flow, carry, orig;
      Edge rev;
      public Edge(int ii, int jj, int cc, int ff)
      {
         i=ii; j=jj; orig=cap=cc; flow=ff;
      }
   }
}
