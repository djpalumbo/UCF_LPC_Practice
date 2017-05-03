
/*

For this problem you are asked to find if a frog can jump from one square
to another on an infinite plane. The coordinates are bounded fairly large.

The frog moves in the following way:

(x+y,y)
(x-y,y)
(x,y+x)
(x,y-x)

If you are familiar with the gcd algorithm or even a little number theory
then you may realize that every movement can reach an axis somewhere.
In other words something of the form:

(g,0) or (0,g)

Notice that when you reach the axis you can move to another axis for the same
value g.

(4,0) -> (4,4) -> (0,4) -> (-4,4) -> (-4,0) -> (-4,-4) -> (0,-4)

Also notice that this movement is two directional. You can easily go back
and forth between locations.

The trick of solving this problem is figuring out that g will always be the 
gcd of the absolute value of the two numbers. So we can use the gcd formula
to define g and use it as the id for that coordinates connected component!

To find if we can move between two coordinates they will have the same gcd!

There is a special case for 0 0. It is in a component by itself.

*/

import java.util.*;
import java.io.*;

public class jump
{
   public static void main(String[] args) throws Exception
   {
      new jump(new Scanner(new File("jump.in")));
   }

   int gcd(int a, int b)
   {
      return b == 0 ? a : gcd(b, a%b);
   }

   int getGroup(Scanner in)
   {
      int a = Math.abs(in.nextInt());
      int b = Math.abs(in.nextInt());
      
      // Special case, the frog is stuck at the origin
      if (a == 0 && b == 0) return -1;

      // Avoid weirdness if a zero already exists in the location
      if (a == 0) return b;
      if (b == 0) return a;

      // Notice the the movement of the frog parallels the gcd algorithm!
      // Two locations that have the same gcd can reach the same spot (g,0)
      // where g is the gcd of the two locations. 
      return gcd(a, b);
   }

   public jump(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int a = getGroup(in);
         int b = getGroup(in);
         System.out.println(a == b?1:0);
      }
   }
}
