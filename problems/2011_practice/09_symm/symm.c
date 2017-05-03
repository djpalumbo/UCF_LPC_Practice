

#include <stdio.h>

FILE  *in_fptr,
      *fopen();
char  matrix[16][16];  /* matrix[0][] and matrix [][0] are not used */
                       /* so that the subscripts will be 1-15 (to   */
                       /* make the code simpler)                    */
int   data_set_count, matrix_size;

/* ******************************************************** */

main()
{
   void  process_one_matrix();

   if ( (in_fptr = fopen("symm.in", "r")) == NULL )
     {
      printf("*** can't open input file *** \n");
      exit();
     }

   data_set_count = 0;
   fscanf(in_fptr, "%d\n", &matrix_size);
   while ( matrix_size > 0 )
     {/* process one matrix */
      ++data_set_count;
      process_one_matrix();
      fscanf(in_fptr, "%d\n", &matrix_size);
     }/* end while ( more input ) */

   if ( fclose(in_fptr) == EOF )
     {
      printf("*** can't close input file *** \n");
      exit();
     }

}/* end main */

/* ******************************************************** */

void process_one_matrix()
{
   int   r, c, request_count, diag, j;
   char  space_or_newline;
   void  prnt_diag();

   /* read and print the matrix */
   printf("Input matrix #%d:\n", data_set_count);
   for ( r = 1;   r <= matrix_size;   ++r )
      for ( c = 1;   c <= matrix_size;   ++c )
        {
         fscanf(in_fptr, "%c%c", &matrix[r][c], &space_or_newline);
         printf("%c%c", matrix[r][c], space_or_newline);
        }




   /* read and process the diagonal requests for this matrix */
   fscanf(in_fptr, "%d\n", &request_count);
   for ( j = 1;   j <= request_count;   ++j )
     {
      fscanf(in_fptr, "%d", &diag);
      printf("Symmetric diagonals %d:\n", diag);
      prnt_diag(1,diag); /* print upper diagonal */
      if ( diag > 1 )
         prnt_diag(diag,1); /* print lower diagonal */
     }/* end for */

   printf("\n");      

}/* end process_one_matrix */

/* ******************************************************** */

void prnt_diag(int start_row, int start_col)
{
   int  r, c;

   for ( r = start_row, c = start_col;
             r <= matrix_size  &&  c <= matrix_size;   ++r, ++c )
     {
      if ( r > start_row )
         /* need a space before the 2nd char, 3rd char, etc. */
         printf(" ");
      printf("%c", matrix[r][c]);
     }
   printf("\n");

}/* end prnt_diag */

/* ******************************************************** */

