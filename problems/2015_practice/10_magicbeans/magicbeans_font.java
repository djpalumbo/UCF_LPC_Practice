
import java.util.*;

public class magicbeans_font
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      int T = in.nextInt();
      while (T-->0) new magicbeans_font(in);
   }

   int MODO = 1_000_000_007;
   boolean isOn(int mask, int i)
   {
      return ((1<<i)&mask) > 0;
   }

   vec2[] compress(vec2[] vs)
   {
      if (vs.length == 0) return new vec2[0];
      vs = Arrays.copyOf(vs, vs.length);
      ArrayList<vec2> res = new ArrayList<vec2>(vs.length);
      Arrays.sort(vs);
      vec2 last = vs[0];
      res.add(last);
      for (int i=1; i<vs.length; i++) if (last.compareTo(vs[i]) != 0)
      {
         res.add(vs[i]);
         last = vs[i];
      }
      return res.toArray(new vec2[0]);
   }

   public magicbeans_font(Scanner in)
   {
      int N = in.nextInt();
      vec2 target = new vec2(in.nextInt(), in.nextInt());
      int M1 = N/2;
      int M2 = N-M1;
      vec2[] vs = new vec2[N];
      for (int i=0; i<N; i++)
         vs[i] = new vec2(in.nextInt(), in.nextInt());

      vec2[] interestingPoints = new vec2[1<<M1];
      for (int mask=0; mask<(1<<M1); mask++)
      {
         vec2 p = new vec2(0, 0);
         for (int i=0; i<M1; i++)
            if (isOn(mask, i))
               p = p.add(vs[i]);
         interestingPoints[mask] = p;
      }
      vec2[] idMapper = compress(interestingPoints);
      int numInterestingPoints = idMapper.length;
      int[][] counts = new int[numInterestingPoints][M1+1];
      for (int mask=0; mask<(1<<M1); mask++)
         counts[Arrays.binarySearch(idMapper, interestingPoints[mask])][Integer.bitCount(mask)]++;
  
      long res = 0L;
      long[] fact = new long[N+1];
      fact[0] = 1;
      for (int i=1; i<=N; i++)
         fact[i] = (fact[i-1]*i)%MODO;
      for (int mask=0; mask<(1<<M2); mask++)
      {
         vec2 p = new vec2(0, 0);
         for (int i=0; i<M2; i++)
            if (isOn(mask, i))
               p = p.add(vs[i+M1]);
         
         vec2 pp = target.sub(p);
         int loc = Arrays.binarySearch(idMapper, pp);
         if (loc >= 0)
         {
            int card = Integer.bitCount(mask);
            for (int cnt=0; cnt<=M1; cnt++)
            {
               long rr = fact[card+cnt]*counts[loc][cnt]%MODO;
               res = (res+rr)%MODO;
            }
         }
      }

      System.out.println(res);
   }
}

class vec2 implements Comparable<vec2>
{
   long x, y;
   vec2(long xx, long yy)
   {
      x=xx; y=yy;
   }

   public int compareTo(vec2 rhs)
   {
      if (x == rhs.x) 
         return Long.compare(y, rhs.y);
      return Long.compare(x, rhs.x);
   }

   vec2 add(vec2 rhs)
   {
      return new vec2(x+rhs.x, y+rhs.y);
   }
   
   vec2 sub(vec2 rhs)
   {
      return new vec2(x-rhs.x, y-rhs.y);
   }

   public String toString()
   {
      return String.format("<%d, %d>", x, y);
   }
}
