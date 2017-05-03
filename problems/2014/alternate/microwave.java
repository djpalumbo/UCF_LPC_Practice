
import java.util.*;

public class microwave
{
   public static void main(String[] args)
   {
      new microwave(new Scanner(System.in));
   }

   int convertTime(String input)
   {
      String[] ss = input.split(":");
      int m = Integer.parseInt(ss[0]);
      int s = Integer.parseInt(ss[1]);
      return m*60+s;
   }

   String makeClean(String s)
   {
      s = s.replaceFirst(":", "");
      while (s.length() > 1 && s.charAt(0) == '0')
         s = s.substring(1);
      return s;
   }

   int score(String events)
   {
      int res = -1;
      char last = 'z';
      for (char c : events.toCharArray())
      {
         if (c != last)
            res++;
         last = c;
         res++;
      }
      return res;
   }

   int oo = 987654321;
   public microwave(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int targetTime = convertTime(in.next());
         int percent = in.nextInt();
         int offset = (targetTime*percent)/100;
        
         int lo = targetTime-offset;
         int hi = targetTime+offset;

         int res = oo;
         int bestDist = oo;
         String best = "failed";

         for (int hours = 0; hours < 100; hours++)
         {
            for (int mins = 0; mins < 100; mins++)
            {
               String time = String.format("%02d:%02d", hours, mins);
               int seconds = convertTime(time);

               if (lo <= seconds && seconds <= hi)
               {
                  String events = makeClean(time);
                  int rr = score(events);
                  int dist = Math.abs(seconds-targetTime);
                  if (rr < res || (res == rr && dist < bestDist))
                  {
                     res = rr;
                     bestDist = dist;
                     best = events;
                  }
               }
            }
         }

         System.out.printf("Case #%d: %s%n", tc, best);
      }
   }
}
