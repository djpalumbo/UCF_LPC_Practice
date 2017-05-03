

/*
   UCF 2011 (Fall) Local Programming Contest
   Problem: smartGen
*/

#include <stdio.h>

#define TRUE  1
#define FALSE 0

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k;
   int   num, prev_prime, next_prime, diff;
   int   is_prime();

   for ( num = 2;  num <= 10000;  ++num )
     {
      if ( !is_prime(num) )
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
            diff = num - prev_prime;
         else
            diff = next_prime - num;

         if ( diff > 9 )
            printf("Missed it by that much (%d)! num = %d \n\n", diff, num);

        }/* end ( checking prime) */

     }/* end for ( num ) */

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
