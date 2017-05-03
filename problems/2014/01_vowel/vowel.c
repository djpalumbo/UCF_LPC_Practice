

/*
   UCF 2014 (Fall) Local Programming Contest
   Problem: vowel
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/* ************************************************************ */

int main(void)
{
   int   data_set_count, k, vowel_count, j;
   char  name[25];
   FILE  *in_fptr;

   if ( (in_fptr = fopen("vowel.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit(-1);
     }

   fscanf(in_fptr, "%d\n", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%s\n", &name[0]);
      printf("%s\n", name);
      vowel_count = 0;

      for ( j = 0;  j < strlen(name);  ++j )
         switch(name[j])
           {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
               ++vowel_count;
           }

      if ( vowel_count > (strlen(name) - vowel_count) )
         printf("1\n");
      else
         printf("0\n");

     }/* end for ( k ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit(-1);
     }

   return(0);

}/* end main */

/* ************************************************************ */

