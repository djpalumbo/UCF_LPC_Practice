
import java.util.*;
import java.math.*;

public class lemonade
{
   public static void main(String[] args)
   {
      new lemonade(new Scanner(System.in));
   }

   public lemonade(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int days = in.nextInt();
         int lemonsNeeded = in.nextInt();
         int sugarNeeded = in.nextInt();

         long res = 0;
         int bestLemonPrice = Integer.MAX_VALUE;
         int bestSugarPrice = Integer.MAX_VALUE;
         int sugarAvailable = 0;
         while (days --> 0)
         {
            int cups = in.nextInt();
            int priceLemon = in.nextInt();
            int priceSugar = in.nextInt();
            bestLemonPrice = Math.min(priceLemon, bestLemonPrice);
            bestSugarPrice = Math.min(priceSugar, bestSugarPrice);

            while (cups*sugarNeeded > sugarAvailable)
            {
               sugarAvailable += 5 * 16;
               res += bestSugarPrice; 
            }
            res += cups * (bestLemonPrice * lemonsNeeded);
            sugarAvailable -= cups*sugarNeeded;
         }

         System.out.println(res);
      }
   }
}
