
import java.io.*;
import java.util.*;


public class spot
{
   public spot(Scanner in)
   {
      int     p;
      int     i, j;
      double  total;
      int     xs, ys, n;
      int[]   lights;
      int[]   angles;
      int[]   intensities;
      double  d, w, ang;

      // Loop over the scenes
      p = in.nextInt();
      for (i = 0; i < p; i++)
      {
         // Initialize total
         total = 0;

         // Get Stacie's position and the number of lights
         xs = in.nextInt();
         ys = in.nextInt();
         n = in.nextInt();

         // Get the light positions
         lights = new int[n];
         for (j = 0; j < n; j++)
            lights[j] = in.nextInt();

         // Get the light focus angles
         angles = new int[n];
         for (j = 0; j < n; j++)
            angles[j] = in.nextInt();

         // Get the light intensities
         intensities = new int[n];
         for (j = 0; j < n; j++)
            intensities[j] = in.nextInt();

         // Now, loop over the lights and determine which ones Stacie is in
         for (j = 0; j < n; j++)
         {
            // Compute distance from Stacie to this light
            d = Math.sqrt( (xs - lights[j]) * (xs - lights[j]) + (ys * ys) );

            // Compute the length of the segment from the vertical of
            // this light to Stacie
            w = Math.abs(xs - lights[j]);

            // Now, compute the angle from this light to Stacie
            ang = Math.atan2(w, ys) * 180.0 / 3.14159265;

            // Now, check if Stacie is within the light
            if (ang <= angles[j] + 0.01)
            {
               // Stacie is inside light j so add the intensity
               total += (double ) intensities[j] / (d * d);
            }
         }

         // Output answer
         System.out.printf("Scene #%d: Spotlight intensity on Stacie is %.3f%n",
                           i + 1, total);
         System.out.println();
      }
   }


   public static void main(String[] args)
   {
      new spot(new Scanner(System.in));
   }
}

