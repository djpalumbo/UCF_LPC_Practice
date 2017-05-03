
import java.util.*;
import java.math.*;

public class longpath
{
   public static void main(String[] args)
   {
      new longpath(new Scanner(System.in));
   }

   boolean[][] adj;

   public longpath(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         int N = in.nextInt();
         adj = new boolean[N][N];
         for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
               adj[i][j] = in.nextInt() == 1;

         ArrayList<Integer> path = new ArrayList<>();
         path.add(0);
         for (int i=1; i<N; i++)
         {
            if (adj[i][path.get(0)])
               path.add(0, i);
            else if (adj[path.get(i-1)][i])
               path.add(i);
            else
            {
               int pos = 0;
               while (true)
               {
                  if (adj[path.get(pos)][i] && adj[i][path.get(pos+1)])
                  {
                     path.add(pos+1, i);
                     break;
                  }
                  pos++;
               }
            }
         }

         StringBuilder sb = new StringBuilder();
         for (int v : path)
         {
            sb.append(v+1);
            sb.append(' ');
         }
         System.out.println(sb.toString().trim());
      }
   }
}
