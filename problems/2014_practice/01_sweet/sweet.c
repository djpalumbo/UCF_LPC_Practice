

#include <stdio.h>

/* ******************************************************** */

int main(void)
{
   FILE  *in_fptr, *fopen();
   int   amount, total, line_number;

   if ( (in_fptr = fopen("sweet.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit();
     }

   line_number = 0;
   total = 0;

   fscanf(in_fptr, "%d", &amount);
   while ( amount > 0 )
     {
      ++line_number;
      total = total + amount;

      if ( total >= 100 )
         printf("Input #%d: Totally Sweet!\n", line_number);
      else if ( total >= 50 )
         printf("Input #%d: Sweet!\n", line_number);

      total = total % 50;

      fscanf(in_fptr, "%d", &amount);

     }/* end while */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit();
     }

   return(0);

}/* end main */

/* ******************************************************** */

