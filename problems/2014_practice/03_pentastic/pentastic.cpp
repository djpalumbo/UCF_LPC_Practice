#include <iostream>
#include <iosfwd>
#include <fstream>

const bool debug = true;
const bool debug2 = false;

#define dprint(dbg_msg) \
   if(debug) std::cerr << "DEBUG ### " << dbg_msg << std::endl;

#define d2print(dbg_msg) \
   if(debug2) std::cerr << "DEBUG ### " << dbg_msg << std::endl;

unsigned nextIndex( unsigned currentIndex )
{
   return 4 == currentIndex ? 0 : 1 + currentIndex;
}

unsigned prevIndex( unsigned currentIndex )
{
   return 0 == currentIndex ? 4 : currentIndex - 1;
}

const unsigned INVALID_INDEX = 9999;

unsigned findIndexOfLowestNegative( int p[] )
{
   d2print( "find lowest: [" << p[0] << " "
                             << p[1] << " "
                             << p[2] << " "
                             << p[3] << " "
                             << p[4] << "]" );

   unsigned indexOfLowestNegative = INVALID_INDEX; // start with invalid number
   int lowestNegativeValue = 0; // start too high

   for( unsigned i=0; i<5; ++i )
   {
      d2print( "i=" << i );

      if( p[i] < lowestNegativeValue )
      {
         indexOfLowestNegative = i;
         lowestNegativeValue = p[i];
         d2print( "new lowest value=" << p[i] << ", index=" << i );
      }
   }

   return indexOfLowestNegative;
}

int main()
{
   std::ifstream fin( "pentastic.in" );

   unsigned numPentagons;
   fin >> numPentagons;

   dprint( "Number of pentagons: " << numPentagons );

   for( unsigned caseNum = 1; caseNum <= numPentagons; ++caseNum )
   {
      dprint( "Case #" << caseNum << ":" );

      int p[5];
      fin >> p[0] >> p[1] >> p[2] >> p[3] >> p[4];

      dprint( "Input was: [" << p[0] << " "
                             << p[1] << " "
                             << p[2] << " "
                             << p[3] << " "
                             << p[4] << "]" );

      // check that sum is positive
      int sum = p[0]+p[1]+p[2]+p[3]+p[4];
      dprint( "Sum of inputs is " << sum );
      if( sum < 1 ) {
         dprint( "Sum of inputs is not positive. Invalid test case." );
         return 1;
      }

      unsigned stepNum, indexOfLowestNegative;
      for( stepNum = 1, indexOfLowestNegative = findIndexOfLowestNegative( p );
         INVALID_INDEX != indexOfLowestNegative;
         ++stepNum, indexOfLowestNegative = findIndexOfLowestNegative( p ) )
      {
         if( 1000 < stepNum ) {
            dprint( "Exceeded 1000 steps! Invalid test case." );
            return 1;
         }
         dprint( "Step #" << stepNum << ":" );
         dprint( "Lowest Negative: " << p[indexOfLowestNegative] << ", index=" << indexOfLowestNegative );

         p[indexOfLowestNegative] = -p[indexOfLowestNegative];

         unsigned neighborIndex;

         neighborIndex = nextIndex( indexOfLowestNegative );
         dprint( "Next Neighbor: " << p[neighborIndex] << ", index=" << neighborIndex );
         p[neighborIndex] -= p[indexOfLowestNegative];

         neighborIndex = prevIndex( indexOfLowestNegative );
         dprint( "Previous Neighbor: " << p[neighborIndex] << ", index=" << neighborIndex );
         p[neighborIndex] -= p[indexOfLowestNegative];

         dprint( "Values now: [" << p[0] << " "
                                 << p[1] << " "
                                 << p[2] << " "
                                 << p[3] << " "
                                 << p[4] << "]" );
      }
      dprint( "Last Step #" << stepNum-1 );

      dprint( "BEGIN Official output for case" );
      std::cout << "Pentagon #" << caseNum << ":" << std::endl;
      std::cout << p[0] << " "
                << p[1] << " "
                << p[2] << " "
                << p[3] << " "
                << p[4] << std::endl;
      std::cout << std::endl;
      dprint( "END Official output for case" );
   }


   return 0;
} 

