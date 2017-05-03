

/*
   UCF 2012 (Fall) Local Programming Contest
   Problem: fold
*/

#include <stdio.h>

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k, side1, side2, fold_count, j;
   FILE  *in_fptr, *fopen();

   if ( (in_fptr = fopen("fold.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit();
     }

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%d %d %d", &side1, &side2, &fold_count);
      printf("Data set: %d %d %d\n", side1, side2, fold_count);

      for ( j = 1;  j <= fold_count;  ++j )
         if ( side1 > side2 )
            side1 /= 2;
         else
            side2 /= 2;

      if ( side1 > side2 )
         printf("%d %d\n\n", side1, side2);
      else
         printf("%d %d\n\n", side2, side1);

     }/* end for ( k ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit();
     }

   return(0);

}/* end main */

/* ************************************************************ */

