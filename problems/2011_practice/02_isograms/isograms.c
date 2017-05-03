

#include <stdio.h>

FILE  *in_fptr,
      *fopen();

char  word[80];
int   count[26];

/* ******************************************************** */

main()
{
   int   data_set_count, k;
   void find_freq(), print_result();

   if ( (in_fptr = fopen("isograms.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit();
     }

   fscanf(in_fptr, "%d\n", &data_set_count);
   for ( k = 1;  k <= data_set_count;  ++k )
     {
      fscanf(in_fptr, "%s\n", &word[0]);
      find_freq();
      print_result();
     }/* end for loop */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit();
     }

}/* end main */

/* ******************************************************** */

void find_freq()
{
   int  k;

   for ( k = 0;  k < 26;  ++k )
      count[k] = 0;

   for ( k = 0;  k < strlen(word);  ++k )
      count[word[k] - 'a'] += 1;
   
}/* end find_freq */

/* ******************************************************** */

void print_result()
{
   int  k;

   for ( k = 0;  k < 26;  ++k )
      if ( ( count[k] != 0 )  &&  ( count[k] != 2 ) )
        {
         printf("%s --- not pair isograms\n\n", word);
         return;
        }

   printf("%s --- pair isograms\n\n", word);
   
}/* end print_result */

/* ******************************************************** */

