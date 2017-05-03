

/*
   UCF 2015 (Fall) Local Programming Contest
   Problem: medal
*/

#include <stdio.h>

#define TRUE  1
#define FALSE 0

int usa_medal[3], russia_medal[3];

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k, j;
   int   win_by_count, win_by_color;
   int   check_win_by_count(), check_win_by_color();
   FILE  *in_fptr;

   in_fptr = stdin;

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
   {
      for ( j = 0;  j <= 2;  ++j )
         fscanf(in_fptr, "%d", &usa_medal[j]);
      for ( j = 0;  j <= 2;  ++j )
         fscanf(in_fptr, "%d", &russia_medal[j]);
      printf("%d %d %d %d %d %d",
                usa_medal[0], usa_medal[1], usa_medal[2],
                russia_medal[0], russia_medal[1], russia_medal[2]);
 
      win_by_count = check_win_by_count();
      win_by_color = check_win_by_color();

      if ( win_by_count && win_by_color )
         printf("\nboth\n\n");
      else if ( win_by_count )
         printf("\ncount\n\n");
      else if ( win_by_color )
         printf("\ncolor\n\n");
      else
         printf("\nnone\n\n");

   }/* end for ( k ) */

   return(0);

}/* end main */

/* ************************************************************ */

int check_win_by_count()
{
   if ( (usa_medal[0] + usa_medal[1] + usa_medal[2]) >
        (russia_medal[0] + russia_medal[1] + russia_medal[2]) )
      return(TRUE);

   return(FALSE);

}/* end check_win_by_count */

/* ************************************************************ */

int check_win_by_color()
{
   int j;

   for ( j = 0;  j <= 2;  ++j )
      if ( usa_medal[j] > russia_medal[j] )
         return(TRUE);
      else if ( usa_medal[j] < russia_medal[j] )
         return(FALSE);

   return(FALSE);

}/* end check_win_by_color */

/* ************************************************************ */
