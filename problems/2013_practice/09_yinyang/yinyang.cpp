
#include <stdio.h>


#define PI 3.14159


int main(void)
{
   FILE *fp;
   int  numTaijitu;
   int  a, b;
   double overall, upper, lower;
   double yin, yang;
   int  i;

   // Open the input file
   fp = fopen("yinyang.in", "r");

   // Read the number of taijitu in the input
   fscanf(fp, "%d\n", &numTaijitu);
   for (i = 0; i < numTaijitu; i++)
   {
      // Read the two radius measurements
      fscanf(fp, "%d %d\n", &a, &b);

      // Calculate the overall area
      overall = PI * a * a;

      // Calculate the areas of the two circles
      upper = PI * b * b;
      lower = PI * (a - b) * (a - b);

      // Calculate the area of the yang
      yang = overall / 2.0 + upper / 2.0 - lower / 2.0;
      yin  = overall / 2.0 - upper / 2.0 + lower / 2.0;
      yang = (int)((yang + 0.00501) * 100) / 100.0;
      yin  = (int)((yin + 0.00501) * 100) / 100.0;

      // Print the results
      printf("Taijitu #%d: yin %0.2lf, yang %0.2lf\n\n", i+1, yin, yang);
   }

   // Close the file
   fclose(fp);
}

