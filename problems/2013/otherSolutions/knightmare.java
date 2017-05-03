
import java.util.*;
import java.io.*;

public class knightmare
{
   public static void main(String[] args) throws Exception
   {
      new knightmare(new Scanner(new File("knightmare.in")));
   }

   int MINX, MINY, MAXX, MAXY;
   ArrayList<Event> events;
   ArrayList<Integer> xs, ys;
   void addRect(int delta, int x, int y, int lox, int loy, int hix, int hiy)
   {
      lox += x; loy += y;
      hix += x; hiy += y;
      hix++; hiy++;

      MINX = Math.min(lox, MINX);
      MINY = Math.min(loy, MINY);
      MAXX = Math.max(hix, MAXX);
      MAXY = Math.max(hiy, MAXY);

      events.add(new Event(lox, loy, hiy, delta));
      events.add(new Event(hix, loy, hiy, -delta));

      xs.add(lox); xs.add(hix);
      ys.add(loy); ys.add(hiy);
   }

   ArrayList<Integer> compress(ArrayList<Integer> vs)
   {
      ArrayList<Integer> res = new ArrayList<Integer>();
      Collections.sort(vs);
      int last = Integer.MIN_VALUE;
      for (int v : vs)
      {
         if (v != last)
         {
            res.add(v);
            last = v;
         }
      }
      
      return res;
   }

   public knightmare(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         events = new ArrayList<Event>();
         xs = new ArrayList<Integer>();
         ys = new ArrayList<Integer>();

         int N = in.nextInt();
         int K = in.nextInt();
         MINX = MINY = MAXX = MAXY = 0;
         while (N-->0)
         {
            int x = in.nextInt();
            int y = in.nextInt();
            int[] v = new int[]{in.nextInt(),in.nextInt()};
            Arrays.sort(v);
            int a = v[0], b = v[1];
            if (a == b)
            {
               addRect(1, x, y, -a, 1, -1, a);
               addRect(1, x, y, 1, -a, a, -1);
               addRect(1, x, y, 1, 1, a, a);
               addRect(1, x, y, -a, -a, -1, -1);
            }
            else
            {
               addRect(1, x, y, -b, -a, b, a);
               addRect(1, x, y, -a, -b, a, b);
               addRect(-1, x, y, -a, -a, a, a);

               addRect(-1, x, y, -b, 0, b, 0);
               addRect(-1, x, y, 0, -b, 0, b);
               addRect(1, x, y, 0, 0, 0, 0);
            }
         }

         xs.add(0); ys.add(0);
         xs = compress(xs);
         ys = compress(ys);
         Collections.sort(events);
        
         int ptr = 0;
         long res = (MAXX-MINX*1L) * (MAXY-MINY*1L);
         IntervalTree it = new IntervalTree(K, ys);
         for (int i=1; i<xs.size(); i++)
         {
            int x1 = xs.get(i-1);
            long x2 = xs.get(i), w = x2-x1;
            while (ptr < events.size() && events.get(ptr).x <= x1)
            {
               Event e = events.get(ptr++);
               int y1 = Collections.binarySearch(ys, e.y1);
               int y2 = Collections.binarySearch(ys, e.y2)-1;
               it.add(y1, y2, e.delta);
            }
            Pair[] vs = it.tree[1];
            for (Pair p : vs) if (p.v < K) res -= p.cnt * w; 
         }
         System.out.println(res);
      }
   }
}

class Event implements Comparable<Event>
{
   int x, y1, y2, delta;
   public Event(int xx, int yy1, int yy2, int dd)
   {
      x=xx; y1=yy1; y2=yy2; delta=dd;
   }
   public int compareTo(Event rhs)
   {
      return x-rhs.x;
   }
}

class IntervalTree
{
   int K;
   ArrayList<Integer> vs;
   int[] delta, lo, hi;
   Pair[][] tree;
   Pair[] MAX;
   public IntervalTree(int KK, ArrayList<Integer> vv)
   {
      K = KK;
      MAX = new Pair[0];
      vs = vv;
      int n = vs.size();
      tree = new Pair[n*4+1][0];
      delta = new int[n*4+1];
      lo = new int[n*4+1];
      hi = new int[n*4+1];
      init(1,0,n-1);
   }
   void init(int i, int l, int r)
   {
      lo[i]=l; hi[i]=r;
      tree[i] = new Pair[1];
      tree[i][0] = new Pair(0, count(i));
      if (l==r) return;
      int m = (l+r-1)/2;
      init(2*i,l,m);
      init(2*i+1,m+1,r);
   }
   long count(int i)
   {
      long a = vs.get(lo[i]);
      long b = hi[i]+1 < vs.size()?vs.get(hi[i]+1):vs.get(hi[i]);
      return b-a;
   }
   void push(int i)
   {
      delta[2*i] += delta[i];
      delta[2*i+1] += delta[i];
      delta[i] = 0;
   }
   Pair[] merge(Pair[] a, int ao, Pair[] b, int bo)
   {
      ArrayList<Pair> res = new ArrayList<Pair>(Math.min(K, a.length+b.length));
      
      int ptr1 = 0, ptr2 = 0, ptr3 = 0;
      while (res.size() < K && ptr1 < a.length && ptr2 < b.length)
      {
         int v1 = a[ptr1].v + ao, v2 = b[ptr2].v + bo;
         if (v1 < v2)
            res.add(a[ptr1++].offset(ao));
         else if (v1 > v2)
            res.add(b[ptr2++].offset(bo));
         else
            res.add(new Pair(v1, a[ptr1++].cnt + b[ptr2++].cnt));
      }

      while (res.size() < K && ptr1 < a.length)
         res.add(a[ptr1++].offset(ao));
      while (res.size() < K && ptr2 < b.length)
         res.add(b[ptr2++].offset(bo));
      return res.toArray(MAX);
   }
   void update(int i)
   {
      tree[i] = merge(tree[2*i], delta[2*i], tree[2*i+1], delta[2*i+1]);
   }
   void add(int l, int r, int x)
   {
      add(1,l,r,x);
   }
   void add(int i, int left, int right, int x)
   {
      if (lo[i] >= left && hi[i] <= right)
         delta[i] += x;
      else if (hi[i] >= left && lo[i] <= right)
      {
         push(i);
         add(2*i, left, right, x);
         add(2*i+1, left, right, x);
         update(i);
      }
   }
}

class Pair
{
   int v;
   long cnt;
   public Pair(int vv, long cc)
   {
      v=vv; cnt=cc;
   }
   Pair offset(int o)
   {
      if (o == 0) return this;
      return new Pair(v+o, cnt);
   }
}
