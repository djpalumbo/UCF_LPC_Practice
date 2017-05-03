import java.io.*;
import java.util.*;

public class tipit
{
    public static void main(String[] args) throws Exception
    {
        new tipit();
    }

    boolean FILE_INPUT = true;
    public tipit() throws Exception
    {
        Scanner sc = new Scanner(System.in);
        if(FILE_INPUT)
            sc = new Scanner(new File("tipit.in"));
        
        int T = sc.nextInt();
        while(T-- > 0)
        {
            int cost = sc.nextInt();
            System.out.printf("Input cost: %d\n", cost);
            int tip = (cost + 4)/5;
            while(!palindrome(cost+tip))
                tip++;
            System.out.printf("%d %d\n", tip, cost+tip);
            System.out.println();
        }
     
    }

    boolean palindrome(int num)
    {
        int x = 0, y = num;
        while(num > 0)
        {
            x = x*10 + (num % 10);
            num /= 10;
        }
        return x == y;
    }
}

