
import java.util.*;
import java.io.*;

public class goldrush
{
   public static void main(String[] args) throws Exception
   {
      new goldrush(new Scanner(new File("goldrush.in")));
   }

   long mod(long a, long b)
   {
      return (a%b+b)%b;
   }

   long extgcd(long a, long b, long[] res)
   {
      long x = res[1] = 0;
      long y = res[0] = 1;
      while (b != 0)
      {
         long q = a/b;
         long t = b; b = a % b; a = t;
         t = x; x = res[0]-q*x; res[0] = t;
         t = y; y = res[1]-q*y; res[1] = t;
      }
      return a;
   }

   long modinv(long a, long n)
   {
      long[] v = new long[2];
      if (extgcd(a, n, v) > 1) return -1;
      return mod(v[0], n);
   }

   int MODO = 1000003;
   long[] fact;
   long choose(long N, long K)
   {
      if (K > N) return 0L;
      if (N == K) return 1L;
      long res = choose(N/MODO, K/MODO);
      int n = (int)(N % MODO);
      int k = (int)(K % MODO);
      if (k > n) return 0L;
      long top = fact[n];
      long bot = (fact[k]*fact[n-k])%MODO;
      long res2 = (top*modinv(bot, MODO))%MODO;
      return (res * res2)%MODO;
   }

   long fastPow(long v, long exp)
   {
      if (exp == 1) return v;
      if (exp % 2 == 1) return (v * fastPow(v, exp-1)) % MODO;
      long rr = fastPow(v, exp/2);
      return (rr * rr) % MODO;
   }

   public goldrush(Scanner in)
   {
      fact = new long[MODO+1];
      fact[1] = fact[0] = 1L;
      for (int i=2; i<=MODO; i++)
         fact[i] = (i*fact[i-1])%MODO;

      int T = in.nextInt();
      while (T-->0)
      {
         long N = in.nextLong();
         long K = in.nextLong();
         
         long gameWays = (2L*choose(K, K/2))%MODO;
         
         long res = 1;
         while (N > 1)
         {
            long numGames = N/2;
            long rr = fastPow(gameWays, numGames);
            res = (res * rr)%MODO;
            N = (N+1)/2;
         }
         System.out.printf("%d%n", res);
      }
   }
}
