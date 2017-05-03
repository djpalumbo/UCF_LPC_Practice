
import java.util.*;
import java.math.*;

public class brownie
{
   public static void main(String[] args)
   {
      new brownie(new Scanner(System.in));
   }

   public brownie(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int mostlyUseless = in.nextInt();
         int numBrownies = in.nextInt();
         System.out.printf("Practice #%d: %d %d%n", tc, mostlyUseless, numBrownies);
         
         int N = in.nextInt();
         while (N-->0)
         {
            int v = in.nextInt();
            while (v >= numBrownies)
               numBrownies *= 2;
            numBrownies -= v;
            System.out.printf("%d %d%n", v, numBrownies);
         }
        
         System.out.println();
      }
   }
}
