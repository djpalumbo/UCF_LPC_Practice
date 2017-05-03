
import java.util.*;
import java.math.*;

public class rain
{
   public static void main(String[] args)
   {
      new rain(new Scanner(System.in));
   }

   public rain(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int sideLen = in.nextInt();
         int radius = in.nextInt();
        
         double sideRad = sideLen * 0.5;
         double cornerLen = Math.sqrt(sideRad*sideRad*2);
      
         if (cornerLen <= radius)
            System.out.printf("%.2f%n", 1.0 * sideLen * sideLen);
         else if (sideRad >= radius)
            System.out.printf("%.2f%n", Math.PI * radius * radius); 
         else
         {
            double h = Math.sqrt(radius*radius - sideRad*sideRad);
            double area1 = h * sideRad;

            double ang1 = Math.asin(h / radius);
            double ang2 = Math.PI*0.5 - ang1*2.0;

            // Sector area
            double area2 = radius * radius * (ang2 * 0.5);

            // Generalize quadrant solution to the whole circle.
            double res = 4.0 * (area1 + area2);

            System.out.printf("%.2f%n", res);
         }
      }
   }
}
