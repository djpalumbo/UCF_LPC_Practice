
import java.util.*;

public class shop
{
   public static void main(String[] args)
   {
      new shop(new Scanner(System.in));
   }

   public shop(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int N = in.nextInt();
         int[] vs = new int[N];
         for (int i=0; i<N; i++)
            vs[i] = in.nextInt();

         int[] dp = new int[N+1];
         dp[1] = vs[0];
         for (int i=1; i<N; i++)
         {
            for (int cnt=N; cnt>=0; cnt--)
            {
               int nCnt = cnt+1;
               if ((2*nCnt) <= (i+1))
                  dp[nCnt] = Math.max(dp[nCnt], dp[cnt]+vs[i]);
            }
         }
         int res = 0;
         for (int d : dp)
            res = Math.max(res, d);
         System.out.printf("Spree #%d: %d%n", tc, res);
      }
   }
}
