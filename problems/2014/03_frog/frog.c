
/*
   UCF 2014 (Fall) Local Programming Contest
   Problem: frog
*/

#include <stdio.h>
#include <stdlib.h>

#define  BLOCKED_CELL  'X'

/* ************************************************************ */

int main(void)
{
   int   data_set_count, 
         cell_count, how_far, /* the two input values per data set */
         jump_count, curr_loc,
         k, j;
   char  cell[60]; /* cell[0] is not used to make programming easier */
   FILE  *in_fptr;

   if ( (in_fptr = fopen("frog.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit(-1);
     }

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      printf("Day #%d\n", k);
      fscanf(in_fptr, "%d %d\n", &cell_count, &how_far);
      printf("%d %d\n", cell_count, how_far);
      for ( j = 1;  j <= cell_count;  ++j )
        {
         fscanf(in_fptr, "%c", &cell[j]);
         printf("%c", cell[j]);
        }

      printf("\n");

      jump_count = 0;
      curr_loc = 1;

      while ( curr_loc < cell_count )
        {
         /* jump as far as possible */
         j = how_far + 1;
         while ( (j > 0)  &&
                 ((curr_loc + j) <= cell_count)  &&
                 (cell[curr_loc + j] == BLOCKED_CELL) )
            --j;

         if ( j > 0 )
           {/* was able to jump */
            curr_loc += j;
            ++jump_count;
           }
         else
            /* could not jump */
            break;

        } /* end while ( curr_loc < cell_count ) */

      if ( curr_loc >=  cell_count )
         printf("%d\n\n", jump_count);
      else
         printf("0\n\n");

     }/* end for ( k ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit(-1);
     }

   return(0);

}/* end main */

/* ************************************************************ */

