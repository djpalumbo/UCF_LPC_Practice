
import java.util.*;

public class chocolate
{
   public static void main(String[] args)
   {
      new chocolate(new Scanner(System.in));
   }

   String[] pieces = { "SV", "SS", "SC",
                       "RV", "RS", "RC",
                       "TV", "TS", "TC" };   
   String[][] sol;
   boolean[] seen;
   int numClues;
   Clue[] clues;

   boolean doesMatch(String piece, String pattern)
   {
      for (int i=0; i<2; i++)
      {
         if (pattern.charAt(i) == '_') continue;
         if (pattern.charAt(i) != piece.charAt(i))
            return false;
      }
      return true;
   }

   boolean doesMatch(int sr, int sc, Clue c)
   {
      for (int i=0; i<c.numRows; i++)
         for (int j=0; j<c.numCols; j++)
            if (!doesMatch(sol[sr+i][sc+j], c.grid[i][j]))
               return false;
      return true;
   }

   boolean go(int i, int j)
   {
      if (i == 3)
         return go(0, j+1);
      if (j == 3)
      {
         // Check clues
         for (Clue c : clues)
         {
            boolean didPass = false;
            for (int sr=0; sr+c.numRows<=3; sr++)
               for (int sc=0; sc+c.numCols<=3; sc++)
                  if (doesMatch(sr, sc, c))
                     didPass = true;
            
            if (!didPass)
               return false;
         }

         return true;
      }

      // Permute the pieces
      for (int k=0; k<9; k++)
      {
         if (seen[k]) continue;

         seen[k] = true;

         sol[i][j] = pieces[k];
         if (go(i+1, j))
            return true;
      
         seen[k] = false;
      }
   
      return false;
   }

   public chocolate(Scanner in)
   {
      int T = in.nextInt();
      for (int tc=1; tc<=T; tc++)
      {
         System.out.printf("Puzzle #%d:%n", tc);
         
         sol = new String[3][3];
         seen = new boolean[9];
         numClues = in.nextInt();
         clues = new Clue[numClues];
         for (int i=0; i<numClues; i++)
            clues[i] = new Clue(in);
         
         boolean didPass = go(0, 0);
         if (!didPass)
            System.out.println("BADNESS!!!!!!!!");
      
         for (int i=0; i<3; i++)
            System.out.printf("%s %s %s%n", sol[i][0], sol[i][1], sol[i][2]);
         System.out.println();
      }
   }
}

class Clue
{
   int numRows, numCols;
   String[][] grid;

   public Clue(Scanner in)
   {
      numRows = in.nextInt();
      numCols = in.nextInt();
      grid = new String[numRows][numCols];
      for (int i=0; i<numRows; i++)
         for (int j=0; j<numCols; j++)
            grid[i][j] = in.next();
   }
}

