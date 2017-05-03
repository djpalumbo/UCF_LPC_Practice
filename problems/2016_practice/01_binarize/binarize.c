

/*
   UCF 2010 (Fall) Local Programming Contest
   Problem: Binarize
*/

#include <stdio.h>

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k;
   int   num, answer;
   FILE  *in_fptr, *fopen();

   if ( (in_fptr = fopen("binarize.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit();
     }

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%d", &num);
      printf("Input value: %d\n", num);

      answer = 2;
      while ( answer < num )
         answer = answer * 2;

      printf("%d\n\n", answer);

     }/* end for */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit();
     }

   return(0);

}/* end main */

/* ************************************************************ */
