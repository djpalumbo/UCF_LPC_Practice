
import java.util.*;
import java.io.*;

public class sneetches_matt
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      PrintWriter out = new PrintWriter(System.out);
      int T = in.nextInt();
      while (T-->0) new sneetches_matt(in, out);
      out.close();
   }

   sneetches_matt(Scanner in, PrintWriter out)
   {
      int N = in.nextInt();
      int Q = in.nextInt();
      String s = in.next();
      
      // Make ST
      SegmentTree st = new SegmentTree(N);
      for (int i=0; i<N; i++)
         if (s.charAt(i) == 'S')
            st.flip(1, i, i);
      
      // Process Queries
      while (Q-->0)
      {
         int a = in.nextInt()-1;
         int b = in.nextInt()-1;
         st.flip(1, a, b);

         // Print results
         out.printf("%d %d%n", st.getMaxStar(1), st.getMaxPlain(1));
      }
   }
}

class SegmentTree
{
   int[] delta, lo, hi, numStarLeft, numStarRight, maxStar;
   int[] maxPlain, numPlainLeft, numPlainRight;
   
   SegmentTree(int n)
   {
      delta = new int[4*n+1];
      lo = new int[4*n+1];
      hi = new int[4*n+1];
      numStarLeft = new int[4*n+1];
      numStarRight = new int[4*n+1];
      maxStar = new int[4*n+1];
      numPlainLeft = new int[4*n+1];
      numPlainRight = new int[4*n+1];
      maxPlain = new int[4*n+1];
      init(1, 0, n-1);
   }

   // Build each node in the segment tree
   void init(int i, int l, int r)
   {
      lo[i] = l;
      hi[i] = r;
      int sz = r-l+1;
      numStarLeft[i] = numStarRight[i] = maxStar[i] = 0;
      maxPlain[i] = numPlainLeft[i] = numPlainRight[i] = sz;
      if (l == r) return;
      int m = (l+r-1)/2;
      init(2*i, l, m);
      init(2*i+1, m+1, r);
   }

   // Flip each of the sneetches star value in the specified range.
   void flip(int i, int left, int right)
   {
      if (hi[i] < left || right < lo[i]) return;
      if (left <= lo[i] && hi[i] <= right)
      {
         // Flip all sneetches in this subtree.
         // Two flips makes for equal array.
         delta[i] ^= 1;
         return;
      }

      prop(i);
      flip(2*i, left, right);
      flip(2*i+1, left, right);
      update(i);
   }

   // Push the change down the tree
   void prop(int i)
   {
      delta[2*i] ^= delta[i];
      delta[2*i+1] ^= delta[i];
      delta[i] = 0;
   }

   // Update the current values in the tree
   void update(int i)
   {
      // The maximum star and plain value sequence may be in the left or
      // right subtree of our data structure. It also may bridge the gap.
      // This is what we will determine here.
      maxStar[i] = Math.max(getMaxStar(2*i), getMaxStar(2*i+1));
      maxStar[i] = Math.max(maxStar[i], getNumStarRight(2*i)+getNumStarLeft(2*i+1));
      maxPlain[i] = Math.max(getMaxPlain(2*i), getMaxPlain(2*i+1));
      maxPlain[i] = Math.max(maxPlain[i], getNumPlainRight(2*i)+getNumPlainLeft(2*i+1));
   
      // Update our running left side values.
      numStarLeft[i] = getNumStarLeft(2*i);
      if (numStarLeft[i] == getSize(2*i))
         numStarLeft[i] += getNumStarLeft(2*i+1);
      numPlainLeft[i] = getNumPlainLeft(2*i);
      if (numPlainLeft[i] == getSize(2*i))
         numPlainLeft[i] += getNumPlainLeft(2*i+1);
      
      // Update our running right side values.
      numStarRight[i] = getNumStarRight(2*i+1);
      if (numStarRight[i] == getSize(2*i+1))
         numStarRight[i] += getNumStarRight(2*i);
      numPlainRight[i] = getNumPlainRight(2*i+1);
      if (numPlainRight[i] == getSize(2*i+1))
         numPlainRight[i] += getNumPlainRight(2*i);
   }

   /* Helper function land. Delta handling gets annoying otherwise. */
   int getMaxStar(int i)
   {
      return delta[i] == 0 ? maxStar[i] : maxPlain[i];
   }

   int getNumStarLeft(int i)
   {
      return delta[i] == 0 ? numStarLeft[i] : numPlainLeft[i];
   }

   int getNumStarRight(int i)
   {
      return delta[i] == 0 ? numStarRight[i] : numPlainRight[i];
   }

   int getMaxPlain(int i)
   {
      return delta[i] == 0 ? maxPlain[i] : maxStar[i];
   }

   int getNumPlainLeft(int i)
   {
      return delta[i] == 0 ? numPlainLeft[i] : numStarLeft[i];
   }

   int getNumPlainRight(int i)
   {
      return delta[i] == 0 ? numPlainRight[i] : numStarRight[i];
   }

   int getSize(int i)
   {
      return hi[i]-lo[i]+1;
   }
}
