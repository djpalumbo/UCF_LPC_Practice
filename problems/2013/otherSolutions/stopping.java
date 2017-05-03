
/*

Given the frequency of stops, determine the number of stops of a trip.
In this problem there are two reason we can stop. It can be solved with
simple simulation because the number of miles is low. Simply loop each
mile and then determine if that miles is the stop for one of the objectives.
To test if it is a stop we can simply use the modulus operation '%' to determine
the remainder after dividing the frequency of the stops. There also exists
a simple mathematical solution if you are clever but making the computer do
the work is much easier on your brain. =)

*/

import java.util.*;
import java.io.*;

public class stopping
{
   public static void main(String[] args) throws Exception
   {
      new stopping(new Scanner(new File("stopping.in")));
   }
   
   public stopping(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int miles = in.nextInt();
         int gas = in.nextInt();
         int food = in.nextInt();
         System.out.printf("%d %d %d%n", miles, gas, food);

         int numStops = 0;
         for (int i=1; i<miles; i++)
            if (i % gas == 0 || i % food == 0)
               numStops++;
         System.out.println(numStops);
      }
   }
}
