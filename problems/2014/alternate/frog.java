
import java.util.*;

public class frog
{
   public static void main(String[] args)
   {
      new frog(new Scanner(System.in));
   }

   int oo = 987654321;
   public frog(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int N = in.nextInt();
         int K = in.nextInt()+1;
         int[] dp = new int[N];
         Arrays.fill(dp, oo);
         dp[0] = 0;
         String s = in.next();

         System.out.printf("Day #%d%n", tc);
         System.out.printf("%d %d%n", N, K-1);
         System.out.printf("%s%n", s);
         for (int i=0; i<N; i++)
            for (int j=1; j<=K; j++) 
               if (i+j < N && s.charAt(i+j) == '.')
                  dp[i+j] = Math.min(dp[i+j], dp[i]+1);
         System.out.println(dp[N-1] == oo ? 0 : dp[N-1]);
         System.out.println();
      }
   }
}
