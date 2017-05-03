// Reference solution: suffixgame
// UCF Local Contest, September 2008.
// Author: Nadeem Mohsin.
import java.util.*;
import java.io.*;

public class suffixgame 
{
    public static void main(String[] args) throws Exception
    {
        Scanner sc = new Scanner(new File("suffixgame.in"));
        int G = sc.nextInt();
        for(int g = 1; g <= G; g++)
        {
            String one = sc.next(), two = sc.next();
            int m = one.length(), n = two.length(), i = 0;
            while(m-i > 1 && n-i > 1 && one.charAt(m-1-i) == two.charAt(n-1-i))
                i++;
            System.out.printf("Game #%d:\n", g);
            System.out.printf("   The input words are %s and %s.\n", one, two);
            System.out.printf("   The words entered in the notebook are " +
                    "%s and %s.\n\n", one.substring(0, m-i), two.substring(0, n-i));
        }
        sc.close();
    }
}
