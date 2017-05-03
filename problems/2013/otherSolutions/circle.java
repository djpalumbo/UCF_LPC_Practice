
/*

For this problem we are given a square and eight packed circles in it.
This is a classic recreational mathematics puzzle to find the minimum
way to pack unit circles in a square. Lucky for you the minimum square
is given in a nice picture.

Notice that there is symmetry across the diagonal. We can take advantage 
of this! Simply solve half a diagonal before determining the side length.

To do this we can break the half diagonal into three chunks.

The first chunk is the distance of the center of the corner circle from
the corner itself. Draw this out and you realize that it forms a 45-45-90
triangle with the two sides of the square.

You may remember from trigonometry this relationship:

            /|
           / |
r*sqrt(2) /  |  r
         /   |
        ------
          r

For the next segment you notice that you can from a triangle with the diagonal
and a line drawn from the center of the circle in the corner to the nearest
circle. Complete the triangle by drawing a line perpendicular to the diagonal.
Notice that this triangle has two sides 2r, and r and is a right triangle.
You may recognize this triangle as a 30-60-90 triangle:


            /|
           / |
       2r /  |  r
         /   |
        ------
       r*sqrt(3)

Then you can notice the last chunk of the line is the size of radius r.

Simply the solution for a diagonal becomes:

2 * (r * sqrt(2) + r * sqrt(3) + r)

or simplier:

2 * r * (sqrt(2) + sqrt(3) + 1)

Now you notice that because this is a square we can use the 45-45-90
that the diagonal makes to get a side length. Simply divide by sqrt(2).
The area of the square is the square of it's side.

*/

import java.util.*;
import java.io.*;

public class circle
{
   public static void main(String[] args) throws Exception
   {
      new circle(new Scanner(new File("circle.in")));
   }

   public circle(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         double r = in.nextDouble();
         double diag = 2 * r * (1 + Math.sqrt(2) + Math.sqrt(3));
         double side = diag / Math.sqrt(2);
         double area = side * side;
         System.out.printf("%.5f%n", area);
      }
   }
}
