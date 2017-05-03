
import java.util.*;
import java.math.*;

public class hanoi
{
   public static void main(String[] args)
   {
      new hanoi(new Scanner(System.in));
   }

   public hanoi(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int d = in.nextInt();
         int n = in.nextInt();
      
         String res = "impossible";
         if (d <= ((n-1)*(n-1)+1))
            res = String.format("%d", d * 2 * (n-1));
         System.out.printf("Grid #%d: %s%n%n", tc, res);
      }
   }
}
