
/*

For this problem you are given some items and get a two dollar discount
if you buy more than one of each item. Solving it can be done with fairly
simple math as there is always at least one item. The answer is just the price
of the first item plus the number of remaining items times the discounted price.

*/

import java.util.*;
import java.io.*;

public class bulk
{
   public static void main(String[] args) throws Exception
   {
      new bulk(new Scanner(new File("bulk.in")));
   }
   
   public bulk(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int count = in.nextInt();
         int price = in.nextInt();
         int total = price + (count - 1) * (price - 2);
         System.out.printf("%d %d%n%d%n", count, price, total);
      }
   }
}
