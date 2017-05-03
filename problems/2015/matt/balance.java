
import java.util.*;
import java.math.*;

public class balance
{
   public static void main(String[] args)
   {
      new balance(new Scanner(System.in));
   }

   String vowels = "aeiouy";
   public balance(Scanner in)
   {
      BigInteger MAX_ANSWER = BigInteger.valueOf(Long.MAX_VALUE);
      BigInteger numVowels = BigInteger.valueOf(vowels.length());
      BigInteger numConsonants = BigInteger.valueOf(26-vowels.length());

      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         char[] vs = in.next().toCharArray();
         BigInteger numWays = BigInteger.ZERO;
         for (int par = 0; par < 2; par++)
         {
            BigInteger waysPar = BigInteger.ONE;
            for (int i=0; i<vs.length; i++)
            {
               boolean isVowel = vowels.indexOf(vs[i]) >= 0;
               if ((i+par) % 2 == 0)
               {
                  if (vs[i] == '?')
                     waysPar = waysPar.multiply(numVowels);
                  else if (!isVowel)
                     waysPar = BigInteger.ZERO;
               }
               else
               {
                  if (vs[i] == '?')
                     waysPar = waysPar.multiply(numConsonants);
                  else if (isVowel)
                     waysPar = BigInteger.ZERO;
               }
            }
            numWays = numWays.add(waysPar);
         }

         if (numWays.compareTo(MAX_ANSWER) > 0)
            System.out.printf("BAD DATA: MAX ANSWER EXCEEDED TEST CASE %d%n", tc);
         else
            System.out.printf("String #%d: %d%n", tc, numWays.longValue());
         System.out.println();
      }
   }
}
