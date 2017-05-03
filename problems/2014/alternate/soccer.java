
import java.util.*;

public class soccer
{
   public static void main(String[] args)
   {
      new soccer(new Scanner(System.in));
   }

   public soccer(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         int nGames = in.nextInt();
         int nPoints = in.nextInt();
         System.out.printf("Team #%d%n", tc);
         System.out.printf("Games: %d%n", nGames);
         System.out.printf("Points: %d%n", nPoints);
         System.out.printf("Possible records:%n");
         for (int numWins=nGames; numWins>=0; numWins--)
         {
            int points = numWins*3;
            int numTies = nPoints-points;
            int gamesLeft = nGames-numWins;
            if (numTies >= 0 && gamesLeft >= numTies)
               System.out.printf("%d-%d-%d%n", numWins, numTies, gamesLeft-numTies);
         }
         System.out.println();
      }
   }
}
