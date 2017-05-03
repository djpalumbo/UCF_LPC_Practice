
import java.util.*;

public class vowel
{
   public static void main(String[] args)
   {
      new vowel(new Scanner(System.in));
   }

   public vowel(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         String s = in.next();
         System.out.println(s);
         int len1 = s.length();
         int len2 = s.replaceAll("[aeiou]", "").length();
         len1 = len1-len2;
         System.out.println(len1 > len2 ? 1 : 0);
      }
   }
}
