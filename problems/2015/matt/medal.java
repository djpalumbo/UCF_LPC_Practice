
import java.util.*;
import java.math.*;

public class medal
{
   public static void main(String[] args)
   {
      new medal(new Scanner(System.in));
   }

   int sum(int[] vs)
   {
      int res = 0;
      for (int v : vs)
         res += v;
      return res;
   }

   boolean testWinColor(int[] a, int[] b)
   {
      if (a[0] > b[0]) return true;
      if (a[0] < b[0]) return false;
      if (a[1] > b[1]) return true;
      if (a[1] < b[1]) return false;
      return a[2] > b[2];
   }

   public medal(Scanner in)
   {
      int T = Integer.parseInt(in.nextLine());
      while (T-->0)
      {
         String curLine = in.nextLine();
         Scanner lineReader = new Scanner(curLine);
         System.out.println(curLine);

         int[] usaMedal = new int[3];
         int[] russiaMedal = new int[3];
         for (int i=0; i<3; i++) usaMedal[i] = lineReader.nextInt();
         for (int i=0; i<3; i++) russiaMedal[i] = lineReader.nextInt();
         
         int usaCount = sum(usaMedal);
         int russiaCount = sum(russiaMedal);
         int canWinCount = usaCount > russiaCount ? 1 : 0;
         int canWinColor = testWinColor(usaMedal, russiaMedal) ? 1 : 0;
         String[][] response = {{"none", "color"}, {"count", "both"}};
         System.out.println(response[canWinCount][canWinColor]);
         System.out.println();
      }
   }
}
