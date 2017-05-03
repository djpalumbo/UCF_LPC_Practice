
import java.util.*;
import java.io.*;

public class blackout
{
   public static void main(String[] args) throws Exception
   {
      new blackout(new Scanner(new File("blackout.in")));
   }

   int N, K, W, H;
   circle2[] bulbs;
   boolean canCover(double current)
   {
      circle2[] vs = new circle2[N];
      for (int i=0; i<N; i++)
         vs[i] = bulbs[i].expandMult(current);

      ArrayList<Double> interestingDeltas = new ArrayList<Double>();
      interestingDeltas.add(0.0);
      interestingDeltas.add(W*1.0);

      for (int i=0; i<N; i++)
         for (int j=i+1; j<N; j++)
            for (vec2 v : vs[i].intersect(vs[j]))
               if (0 < v.x && v.x < W)
                  interestingDeltas.add(v.x);
      for (circle2 cur : vs)
      {
         double[] ys = new double[]{cur.cen.y, H-cur.cen.y};
         for (double dy : ys)
         {
            if (dy <= cur.rad)
            {
               double dx = Math.sqrt(Math.abs(cur.rad*cur.rad-dy*dy));
               double x1 = cur.cen.x-dx;
               double x2 = cur.cen.x+dx;
               if (0 < x1 && x1 < W)
                  interestingDeltas.add(x1);
               if (0 < x2 && x2 < W)
                  interestingDeltas.add(x2);
            }
         }
      }

      Collections.sort(interestingDeltas);
      for (int i=1; i<interestingDeltas.size(); i++)
      {
         double x = (interestingDeltas.get(i)+interestingDeltas.get(i-1))*0.5;
         int numCircles = 0;
         ArrayList<Event> events = new ArrayList<Event>();
         events.add(new Event(0, 0));
         events.add(new Event(H, 0));

         for (circle2 cur : vs)
         {
            double dx = Math.abs(cur.cen.x-x);
            if (dx <= cur.rad)
            {
               double dy = Math.sqrt(Math.abs(cur.rad*cur.rad-dx*dx));
               double y1 = cur.cen.y-dy-1e-9;
               double y2 = cur.cen.y+dy+1e-9;
               if (y1 < 0)
                  numCircles++;
               else if (y1 <= H)
                  events.add(new Event(y1, 1));

               if (0 <= y2 && y2 <= H)
                  events.add(new Event(y2, -1));
            }
         }

         Collections.sort(events);
         for (Event e : events)
         {
            numCircles += e.d;
            if (numCircles <= K)
               return false;
         }
      }

      return true;
   }

   public blackout(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         N = in.nextInt();
         K = in.nextInt();
         W = in.nextInt();
         H = in.nextInt();
         
         bulbs = new circle2[N];
         for (int i=0; i<N; i++)
            bulbs[i] = new circle2(new vec2(in.nextInt(), in.nextInt()), in.nextInt());
         
         double lo = 0;
         double hi = W+H;
         for (int i=0; i<50; i++)
         {
            if ((hi-lo) < 1e-7) break;
            double mid = (lo+hi)*0.5;
            if (canCover(mid))
               hi = mid;
            else
               lo = mid;
         }

         String r1 = String.format("%.2f%n", hi+1e-9);
         String r2 = String.format("%.2f%n", lo-1e-9);
         if (r1.compareTo(r2) != 0)
            System.out.println("BAD DATA "+r1+" "+r2);
         System.out.printf(r1);
      }
   }
}

class Event implements Comparable<Event>
{
   double y;
   int d;

   public Event(double yy, int dd)
   {
      y=yy; d=dd;
   }
   
   public int compareTo(Event rhs)
   {
      return Double.compare(y, rhs.y);
   }
}

class circle2
{
   vec2 cen;
   double rad;

   public circle2(vec2 cc, double rr)
   {
      cen = cc; rad = rr;
   }

   circle2 expandMult(double delta)
   {
      return new circle2(cen, rad*delta);
   }
    
   vec2[] intersect(circle2 rhs) 
   {
      double L = rad;
		double R = rhs.rad;
		double B = cen.dist(rhs.cen);
		
		int nSols = GEOM.testTriangle(L,R,B);
		if(nSols == 0) return new vec2[0]; 
		
		double c = (B*B+L*L-R*R)/(2*B);
		double b = Math.sqrt(Math.abs(L*L-c*c));
		vec2 u = rhs.cen.sub(cen).normalize();
		vec2 v = new vec2(-u.y,u.x);
		vec2 vc = u.scale(c), vb = v.scale(b);
		return new vec2[]{cen.add(vc).add(vb),cen.add(vc).sub(vb)};
	}
}

class vec2
{
   double x, y;
   public vec2(double xx, double yy)
   {
      x=xx; y=yy;
   }

   vec2 add(vec2 rhs)
   {
      return new vec2(x+rhs.x, y+rhs.y);
   }

   vec2 sub(vec2 rhs)
   {
      return new vec2(x-rhs.x, y-rhs.y);
   }

   vec2 scale(double s)
   {
      return new vec2(x*s, y*s);
   }

   double mag()
   {
      return Math.sqrt(x*x+y*y);
   }

   double dist(vec2 rhs)
   {
      return sub(rhs).mag();
   }

   vec2 normalize()
   {
      return scale(1.0/mag());
   }
}

class GEOM
{
   static double EPS = 1e-9;
   static boolean eq(double a, double b)
   {
      double d = Math.abs(a-b);
      if (d < EPS) return true;
      return d < EPS * Math.max(Math.abs(a), Math.abs(b));
   }

   static int testTriangle(double a, double b, double c)
   {
      double[] tri = new double[]{a,b,c};
      Arrays.sort(tri);
      double T = tri[0] + tri[1];
      if (GEOM.eq(T, tri[2])) return 1;
      return T>tri[2]?2:0;
   }
}
