#include <iostream>
#include <iosfwd>
#include <fstream>
#include <sstream>
#include <string>


const bool debug = true;
const bool debug2 = false;

#define dprint(dbg_msg) \
   if(debug) std::cerr << "DEBUG ### " << dbg_msg << std::endl;

#define d2print(dbg_msg) \
   if(debug2) std::cerr << "DEBUG ### " << dbg_msg << std::endl;


unsigned greatestCommonFactor( unsigned a, unsigned b )
{
   if( 0 == b ) return a;

   return greatestCommonFactor( b, a % b );
}


bool areCoprime( unsigned a, unsigned b )
{
   unsigned gcf = greatestCommonFactor( a, b );
   d2print( "gcf is " << gcf );
   return 1 == gcf;
}


int main( int argc, char* argv[] )
{
   bool leadingZero = false;
   if( argc > 1 &&  std::string( argv[1] ) == "-z" ) leadingZero = true;

   std::ifstream fin( "coprime.in" );

   unsigned numTickets;
   fin >> numTickets;

   dprint( "Number of tickets: " << numTickets );

   for( unsigned caseNum = 1; caseNum <= numTickets; ++caseNum )
   {
      dprint( "Case #" << caseNum << ":" );

      std::string ticketDigits;
      fin >> ticketDigits;

      dprint( "Input was: [" << ticketDigits << "]" );

      // check length, >=3 and <=9
      dprint( "Length of input is " << ticketDigits.length() );
      if( ticketDigits.length() < 3 )
      {
         dprint( "Input is not at least 3 digits. Invalid test case." );
         return 1;
      }
      if( ticketDigits.length() > 9 )
      {
         dprint( "Input is more than 8 digits. Invalid test case." );
         return 1;
      }

      // check for leading zero
      if( '0' == ticketDigits[0] )
      {
         dprint( "Input starts with zero. Invalid test case." );
         return 1;
      }

      // check for ending zero
      if( '0' == ticketDigits[ticketDigits.length()-1] )
      {
         dprint( "Input ends with zero. Invalid test case." );
         return 1;
      }

      // check for multiple consecutive zeroes
      if( std::string::npos != ticketDigits.find( "00") )
      {
         dprint( "Input contains consecutive zeroes. Invalid test case." );
         return 1;
      }

      std::cout << "Ticket #" << caseNum << ":" << std::endl;

      bool foundCoprimes = false;
      for( unsigned i = 1; i < ticketDigits.length(); ++i )
      {
         dprint( "Iteration #" << i << ":" );

         int leftNum;
         std::string leftNumStr( ticketDigits.substr( 0, i ) );
         std::stringstream( leftNumStr ) >> leftNum;

         dprint( "Left string=[" << leftNumStr << "], value=" << leftNum );

         int rightNum;
         std::string rightNumStr( ticketDigits.substr( i ) );
         std::stringstream( rightNumStr ) >> rightNum;

         dprint( "Right string=[" << rightNumStr << "], value=" << rightNum );

         if( areCoprime( leftNum, rightNum ) )
         {
            dprint( "Found coprimes" );
            foundCoprimes = true;

            if( leadingZero )
            {
               std::cout << leftNumStr << " " << rightNumStr << std::endl << std::endl;
            }
            else
            {
               std::cout << leftNum << " " << rightNum << std::endl << std::endl;
            }

            break;
         }

         dprint( "not coprimes at this iteration" );
      }

      if( foundCoprimes ) continue;

      std::cout << "Not relative" << std::endl << std::endl;
   }

   return 0;
}

