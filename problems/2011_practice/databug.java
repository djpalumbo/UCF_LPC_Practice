import java.util.*;
import java.io.*;

/*
 * Solution is based on the following simplified grammar. Every string
 * in the original grammar has a string produced by this grammar as a substring
 * and no non-infected strings contain strings produced by this grammar.
 * ****************************************

<Infection> = <classA> | <classB> | <classC>
<classA> = <core><beta>
<classB> = <beta><core><alpha>
<classC> = <padding><core><tail>

<alpha> = a | b | c
<beta> = x | y | z

<padding> = xyz(xy)*
<tail> = txyz | ta<padding>il

<core> = (r | m | n | o)*

 ***************************************** 
 */

public class databug {
	public static void main(String[] args) throws IOException
	{
		int character = 1;
		Scanner fin = new Scanner(new File("databug.in"));
		
		while(true)
		{
			int lines = fin.nextInt();
			if(lines == 0)
				return;
			String guy = "";
			String name = fin.next();
			
			for(int i=0;i<lines;i++)
				guy+=fin.next();
			
			int pos = 0;
			int infections=0;
			while(pos<guy.length())
			{
				int infectionlength = infection(guy,pos);
				if(infectionlength == -1)
					pos++;
				else
				{
					pos += infectionlength;
					infections++;
				}
			}
			System.out.println("Character #"+character+": "+name+" has "+infections+" infection(s)!");
			System.out.println();
			character++;
		}
	}
	
	// pos must be less than guy.length()
	// <Infection> = <classA> | <classB> | <classC>
	private static int infection(String guy, int pos)
	{
		int result = classA(guy,pos);
		if(result != -1)
			return result;
		result = classB(guy,pos);
		if(result != -1)
			return result;
		return classC(guy,pos);
	}
	
	// pos must be less than guy.length()
	// <classA> = <core><beta>
	private static int classA(String guy, int pos)
	{
		int resultcore = core(guy,pos);
		if(resultcore == -1)
			return -1;
		pos += resultcore;
		if(pos >= guy.length())
			return -1;
		
		int resultbeta = beta(guy,pos);
		if(resultbeta == -1)
			return -1;
		
		return resultcore + resultbeta;
		
	}
	
	// <classB> = <beta><core><alpha>
	private static int classB(String guy, int pos)
	{
		if(guy.length() - pos < 3)
			return -1;
		
		if(beta(guy,pos) == -1)
			return -1;
		pos++;
		int result = core(guy,pos);
		if(result == -1)
			return -1;
		pos += result;
		
		if(guy.length() - pos < 1 || alpha(guy,pos) == -1)
			return -1;
				
		return result+2;
		
	}
	
	// <classC> = <padding><core><tail>
	private static int classC(String guy, int pos)
	{
		int padlen = padding(guy,pos);
		if(padlen == -1)
			return -1;
		pos += padlen;
		
		if(guy.length() <= pos)
			return -1;
		int corelen = core(guy,pos);
		if(corelen == -1)
			return -1;
		pos += corelen;
		
		int taillen = tail(guy,pos);
		if(taillen ==-1)
			return -1;
		return padlen + corelen + taillen;
		
	}
	
	// pos must be less than guy.length()
	// <alpha> = a | b | c
	private static int alpha(String guy, int pos)
	{
		char c = guy.charAt(pos);
		if(c=='a' || c=='b' || c=='c')
			return 1;
		return -1;
	}
	
	// pos must be less than guy.length()
	// <beta> = x | y | z
	private static int beta(String guy, int pos)
	{
		char c = guy.charAt(pos);
		if(c=='x' || c=='y' || c=='z')
			return 1;
		return -1;
	}
	
	// pos must be less than guy.length()
	// <core> = (r | m | n | o)*
	private static int core(String guy, int pos)
	{
		char c = guy.charAt(pos);
		int result = 0;
		while((c == 'r' || c=='m' || c=='n' || c=='o'))
		{
			result++;
			pos++;
			if(pos >= guy.length())
				return result;
			c = guy.charAt(pos);
		}
		if(result == 0)
			return -1;
		return result;
	}
	
	// <padding> = xyz(xy)*
	private static int padding(String guy, int pos)
	{
		if(guy.length() - pos < 3)
			return -1;
		if(guy.substring(pos,pos+3).compareTo("xyz") != 0)
			return -1;
		int result = 3;
		pos += 3;
		
		while(guy.length() - pos >= 2 && guy.substring(pos,pos+2).compareTo("xy") == 0)
		{
			pos += 2;
			result += 2;
		}
		return result;
	}
	
	// <tail> = txyz | ta<padding>il
	private static int tail(String guy, int pos)
	{
		if(guy.length() - pos < 4)
			return -1;
		if(guy.substring(pos,pos+4).compareTo("txyz") == 0)
			return 4;
		if(guy.substring(pos,pos+2).compareTo("ta") != 0)
			return -1;
		
		pos+=2;
		int padlen = padding(guy,pos);
		if(padlen == -1)
			return -1;
		pos+=padlen;
		
		if(guy.length() - pos < 2 || guy.substring(pos,pos+2).compareTo("il") != 0)
			return -1;
		return padlen+4;
	}
}
/*
 * Solution is based on the following simplified grammar. Every string
 * in the original grammar has a string produced by this grammar as a substring
 * and no non-infected strings contain strings produced by this grammar.
 * ****************************************

<classC> = <padding><core><tail>


 ***************************************** 
 */
