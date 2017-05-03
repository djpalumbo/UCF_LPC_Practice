

/*
   UCF 2015 (Fall) Local Programming Contest
   Problem: brownie
*/

#include <stdio.h>

/* ************************************************************ */

int main(void)
{
   int   num_of_data_sets, k, j;
   int   num_of_students_in_practice, num_of_brownies, 
         num_of_groups, num_of_students_in_group;
   FILE  *in_fptr;

   in_fptr = stdin;

   fscanf(in_fptr, "%d", &num_of_data_sets);
   for ( k = 1;  k <= num_of_data_sets;  ++k )
   {
      fscanf(in_fptr, "%d %d", &num_of_students_in_practice,
                               &num_of_brownies);
      printf("Practice #%d: %d %d\n", k, num_of_students_in_practice,
                                         num_of_brownies);

      fscanf(in_fptr, "%d", &num_of_groups);
      for ( j = 1;  j <= num_of_groups;  ++j )
        {
         fscanf(in_fptr, "%d", &num_of_students_in_group);

         while ( num_of_brownies <= num_of_students_in_group )
            num_of_brownies *= 2;

         num_of_brownies -= num_of_students_in_group;

         printf("%d %d\n", num_of_students_in_group, num_of_brownies);

        }/* end for ( j ) */

      printf("\n");

   }/* end for ( k ) */

   return(0);

}/* end main */

/* ************************************************************ */

