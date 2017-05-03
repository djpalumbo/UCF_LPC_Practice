
import java.util.*;
import java.math.*;

public class twins
{
   public static void main(String[] args)
   {
      new twins(new Scanner(System.in));
   }

   public twins(Scanner in)
   {
      int T = Integer.parseInt(in.nextLine());
      while (T-->0)
      {
         TreeSet<String> seen = new TreeSet<>();
         String curLine = in.nextLine();
         Scanner lineScan = new Scanner(curLine);
         System.out.println(curLine);
         while (lineScan.hasNextInt())
         {
            int v = lineScan.nextInt();
            if (v == 17)
               seen.add("zack");
            else if (v == 18)
               seen.add("mack");
         }

         if (seen.size() == 0)
            System.out.println("none");
         else if (seen.size() == 2)
            System.out.println("both");
         else
            System.out.println(seen.first());

         System.out.println();
      }
   }
}
