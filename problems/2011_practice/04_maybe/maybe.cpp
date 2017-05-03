
#include <fstream>
#include <iostream>
#include <string>


static const unsigned debug = 0;

bool
isPresent( char c, const std::string s )
{
   return (s.find( c ) != std::string::npos) ? true : false;
}

std::string
substituteVar( char oldch, char newch, const std::string expr )
{
   std::string s = expr;
   std::string newstr;
   newstr.assign( 1, newch );

   std::string::size_type n;
   while( (n = s.find( oldch )) != std::string::npos )
   {
      s.replace( n, 1, newstr );
   }

   if( debug > 1 ) std::cerr << " ##### " << s << " \t(substitute " << oldch << " with " << newch << ")" << std::endl;

   return s;
}

std::string
evaluateNots( const std::string expr )
{
   std::string s = expr;

   while( isPresent( '!', s ) )
   {
      std::string::size_type n;

      if( (n = s.find( "!0" )) != std::string::npos )
      {
         // substitute !0 with 2
         s.replace( n, 2, "2" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted !0 with 2)" << std::endl;
      }
      else if( (n = s.find( "!1" )) != std::string::npos )
      {
         // substitute !1 with 1
         s.replace( n, 2, "1" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted !1 with 1)" << std::endl;
      }
      else if( (n = s.find( "!2" )) != std::string::npos )
      {
         // substitute !2 with 0
         s.replace( n, 2, "0" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted !2 with 0)" << std::endl;
      }
      else
      {
         if( debug > 0 ) std::cerr << "Bad NOT, original=" << expr << " result=" << s << std::endl;
         break;
      }
   }

   return s;
}

std::string
evaluateAnds( const std::string expr )
{
   std::string s = expr;

   std::string::size_type a, b;

   // substitute all 0&x with 0, all x&0 with 0
   while( (a = s.find( "0&" )) != std::string::npos || (b = s.find( "&0" )) != std::string::npos )
   {
      if( a != std::string::npos )
      {
         s.replace( a, 3, "0" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted 0&x with 0)" << std::endl;
      }
      else if( b != std::string::npos )
      {
         s.replace( b-1, 3, "0" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted x&0 with 0)" << std::endl;
      }
   }

   // substitute all 1&x with 1, all x&1 with 1
   while( (a = s.find( "1&" )) != std::string::npos || (b = s.find( "&1" )) != std::string::npos )
   {
      if( a != std::string::npos )
      {
         s.replace( a, 3, "1" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted 1&x with 1)" << std::endl;
      }
      else if( b != std::string::npos )
      {
         s.replace( b-1, 3, "1" );
         if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted x&1 with 1)" << std::endl;
      }
   }

   // substitute all 2&2 with 2
   while( (a = s.find( "2&2" )) != std::string::npos )
   {
      s.replace( a, 3, "2" );
      if( debug > 1 ) std::cerr << " ##### " << s << " \t(substituted 2&2 with 2)" << std::endl;
   }

   if( isPresent( '&', s ) )
   {
      if( debug > 0 ) std::cerr << "Did not evaluate all ANDs, original=" << expr << " result=" << s << std::endl;
   }

   return s;
}

std::string
evaluateOrs( const std::string expr )
{
   if( isPresent( '2', expr ) )
   {
      return std::string( "2" );
   }
   if( isPresent( '1', expr ) )
   {
      return std::string( "1" );
   }

   return std::string( "0" );
}

void
processOps( const std::string notExpr, unsigned& numMaybes, std::string values )
{
   std::string andExpr = evaluateNots( notExpr );
   std::string orExpr = evaluateAnds( andExpr );
   std::string answer = evaluateOrs( orExpr );
   if( debug > 1 ) std::cerr << " ##### " << answer << " \t(or'd)" << std::endl;

   if( answer == "1" ) ++numMaybes;

   if( debug > 1 )
   {
      std::cerr << values << " = " << answer << "  (" << numMaybes << ")" << std::endl;
   }
}

void
processVar( char v, const std::string expr, unsigned& numMaybes, std::string values )
{
   unsigned n = isPresent( v, expr ) ? 3 : 1; 

   if( debug > 2 ) std::cerr << v << ".n=" << n << std::endl;

   for( unsigned i = 0; i < n; ++i )
   {
      if( debug > 2 ) std::cerr << v << ".i=" << i << std::endl;

      char value = char( '0' + i );
      std::string nextExpr = substituteVar( v, value, expr );

      if( 'G' == v )
         processOps( nextExpr, numMaybes, values + " " + (n<3 ? '-' : value) );
      else
         processVar( char( v + 1 ), nextExpr, numMaybes, values + " " + (n<3 ? '-' : value) );
   }
}

int
main()
{
   std::ifstream fin( "maybe.in" );
   unsigned numExpressions;
   fin >> numExpressions;
   if( debug > 2 ) std::cerr << "numExpressions=" << numExpressions << std::endl;

   for( unsigned i = 0; i < numExpressions; ++i )
   {
      unsigned numMaybes = 0;

      std::string aExpr;
      fin >> aExpr;

      if( debug > 1 )
      {
         std::cerr << " >>> A B C D E F G = " << aExpr << std::endl;
      }

      if( debug > 1 ) std::cerr << " ##### " << aExpr << " \t(input)" << std::endl;

      // begin recursion
      processVar( 'A', aExpr, numMaybes, " >>>" );

      std::cout << "Expression #" << (i+1) << ": " << aExpr << std::endl
            << numMaybes << " MAYBE result(s) possible" << std::endl
            << std::endl;
   }
}

