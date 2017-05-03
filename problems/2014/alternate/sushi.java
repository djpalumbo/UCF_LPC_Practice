
import java.util.*;

public class sushi
{
   public static void main(String[] args)
   {
      new sushi(new Scanner(System.in));
   }

   public sushi(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int nCars = in.nextInt();
         int[] carTimes = new int[nCars];
         for (int i=0; i<nCars; i++)
            carTimes[i] = in.nextInt();

         int[] studentTimes = new int[4*nCars];
         for (int i=0; i<4*nCars; i++)
            studentTimes[i] = in.nextInt();
        
         Arrays.sort(carTimes);
         Arrays.sort(studentTimes);
         int res = 0;
         for (int i=0; i<nCars; i++)
         {
            for (int j=0; j<4; j++)
            {
               int k = 4*(nCars-i)-j-1;
               res = Math.max(res, carTimes[i]+studentTimes[k]);
            }
         }
         
         System.out.printf("Trip #%d: %d%n", tc, res);
      }
   }
}
