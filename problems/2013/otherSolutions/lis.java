
import java.util.*;
import java.io.*;

public class lis
{
   public static void main(String[] args) throws Exception
   {
      new lis(new Scanner(new File("lis.in")));
   }

   public lis(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int N = in.nextInt();
         int K = in.nextInt();
         int[] vs = new int[N];
         for (int i=0; i<N; i++)
            vs[i] = in.nextInt();

         BIT[] dp = new BIT[K];
         for (int i=0; i<K; i++)
            dp[i] = new BIT(100003);
         for (int i=0; i<N; i++)
         {
            for (int k=K-1; k>=0; k--)
            {
               int lower = dp[k].sum(vs[i]-1);
               if (k+1 < K)
               {
                  int val = dp[k].sum(100001)-lower;
                  if (val < 0)
                     val += Util.MODO;
                  dp[k+1].inc(vs[i], val % Util.MODO);
               }
               dp[k].inc(vs[i], lower);
            }
            dp[0].inc(vs[i], 1);
         }
         System.out.println(dp[K-1].sum(100001));
      }
   }
}

class BIT
{
   int n;
   int[] v;

   public BIT(int N)
   {
      v = new int[n=N];   
   }

   int sum(int x)
   {
      return x < 0 ? 0 : (v[x]+sum((x&(x+1))-1)) % Util.MODO;
   }

   void inc(int p, int x)
   {
      for (int i=p; i<n; i |= (i+1))
      {
         v[i] += x;
         v[i] %= Util.MODO;
      }
   }
}

class Util
{
   static int MODO = 1000000007;
}
