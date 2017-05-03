

/*
   UCF 2011 (Fall) Local Programming Contest
   Problem: smart
*/

#include <stdio.h>

#define TRUE  1
#define FALSE 0

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k;
   int   num, prev_prime, next_prime;
   FILE  *in_fptr, *fopen();
   int   is_prime();

   if ( (in_fptr = fopen("smart.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit(1);
     }

   fscanf(in_fptr, "%d", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%d", &num);
      printf("Input value: %d\n", num);

      if ( is_prime(num) )
         printf("Would you believe it; it is a prime!\n\n");
      else
        {
         /* find prime less than num */
         for ( prev_prime = (num - 1);  !is_prime(prev_prime);
                                                     --prev_prime )
	    ;

         /* find prime greater than num */
         for ( next_prime = (num + 1);  !is_prime(next_prime);
                                                     ++next_prime )
	    ;

         if ( (num - prev_prime) <= (next_prime - num) )
            printf("Missed it by that much (%d)!\n\n", num - prev_prime);
         else
            printf("Missed it by that much (%d)!\n\n", next_prime - num);

        }/* end else ( checking prime) */

     }/* end for ( k ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit(1);
     }

   return(0);

}/* end main */

/* ************************************************************ */

int is_prime(int num)
{
   int k;

   for ( k = 2;  k <= (num / 2);  ++k )
      if ( (num % k) == 0 )
         return(FALSE);

   return(TRUE);

}/* end is_prime */

/* ************************************************************ */
