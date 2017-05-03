

/*
   UCF 2014 (Fall) Local Programming Contest
   Problem: soccer
*/

#include <stdio.h>
#include <stdlib.h>

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k, games, points, win, tie;
   FILE  *in_fptr;

   if ( (in_fptr = fopen("soccer.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit(-1);
     }

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%d %d", &games, &points);
      printf("Team #%d\nGames: %d\nPoints: %d\nPossible records:\n",
                                                       k, games, points);

      for ( win = (points / 3);  win >= 0;  --win )
        {
         tie = points - (3 * win);
         if ( games >= (win + tie) )
            printf("%d-%d-%d\n", win, tie, (games - win - tie) );
        }

      printf("\n");

     }/* end for ( k ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit(-1);
     }

   return(0);

}/* end main */

/* ************************************************************ */

