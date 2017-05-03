
import java.util.*;
import java.math.*;

public class stars
{
   public static void main(String[] args)
   {
      new stars(new Scanner(System.in));
   }

   int oo = 987654321, R, C;
   boolean[][] isMarked;
   boolean[][] canMark;
   public stars(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         R = in.nextInt();
         C = in.nextInt();
         isMarked = new boolean[R][C]; 
         canMark = new boolean[R][C];
         for (int i=0; i<R; i++)
         {
            String s = in.next();
            for (int j=0; j<C; j++)
               isMarked[i][j] = s.charAt(j) == '#';
         }

         // Find the cells that can be stamped
         for (int i=1; i<(R-1); i++)
            for (int j=1; j<(C-1); j++)
               canMark[i-1][j] = isMarked[i-1][j] && isMarked[i][j] && isMarked[i+1][j] && isMarked[i][j-1] && isMarked[i][j+1];

         // Create a mask of all cells that get stamped when we stamp
         int stampMask = 1 | (1 << C) | (1 << (C-1)) | (1 << (C+1)) | (1 << (2*C));

         // Run the bitmask DP to calculate minimum stampings.
         int[][] dp = new int[R*C+1][1<<(2*C)];
         for (int[] dd : dp) Arrays.fill(dd, oo);
         dp[0][0] = 0;
         int curCell = -1;
         for (int i=0; i<R; i++)
         {
            for (int j=0; j<C; j++)
            {
               curCell++;
               int nxtCell = curCell+1;
               for (int mask=0; mask<(1<<(2*C)); mask++) if (dp[curCell][mask] < oo)
               {
                  // Try marking the cell
                  if (canMark[i][j])
                  {
                     int nxtMask = (mask | stampMask) >> 1;
                     dp[nxtCell][nxtMask] = Math.min(dp[nxtCell][nxtMask], dp[curCell][mask]+1);
                  }

                  // Try not marking the cell
                  if ((isMarked[i][j] && ((mask & 1) == 1)) || !isMarked[i][j])
                  {
                     int nxtMask = mask >> 1;
                     dp[nxtCell][nxtMask] = Math.min(dp[nxtCell][nxtMask], dp[curCell][mask]);
                  }
               }
            }
         }
         
         String res = "impossible";
         if (dp[R*C][0] < oo)
            res = String.format("%d", dp[R*C][0]);
         System.out.printf("Image #%d: %s%n%n", tc, res);
      }
   }
}
