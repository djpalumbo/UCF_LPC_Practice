// Stephen Fulwider
//	2010 UCF Local Contest - Lowest Common Ancestor

// This solution finds the longest common prefix of the binary representation of
//	the two nodes. This is a common trick when dealing with binary trees.

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class lca
{

	public static void main(String[] args) throws Exception
	{
		new lca();
	}
	
	String[] B=new String[16]; // B[i] := binary repr. of i (e.g., B[6]=110)
	String[] BZ=new String[16]; // BZ[i] := binary repr. of i w/ leading zeros (e.g., B[6]=0110)
	
	lca() throws Exception
	{
		for (int i=0; i<16; ++i)
		{
			B[i]=Integer.toBinaryString(i);
			BZ[i]=B[i];
			while (BZ[i].length()!=4)
				BZ[i]="0"+BZ[i];
		}	
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out=new PrintWriter(new BufferedOutputStream(System.out));
		
		for (int T=new Integer(in.readLine()),TC=1; T-->0; ++TC)
		{
			String line=in.readLine();
			StringTokenizer st=new StringTokenizer(line);
			String X=st.nextToken();
			String Y=st.nextToken();
			
			out.println("Case #"+TC+": "+solve(X,Y));
			out.println();
		}
		out.close();
	}
	
	StringBuilder solve(String X, String Y)
	{
		StringBuilder x=toBinary(X);
		StringBuilder y=toBinary(Y);
		
		// find the length of the longest common prefix of x and y
		int len=Math.min(x.length(), y.length());
		int to;
		for (to=0; to<len; ++to)
			if (x.charAt(to)!=y.charAt(to))
				break;
		
		return hex(x,to-1);
	}
	
	// convert the hex string to binary
	StringBuilder toBinary(String x)
	{
		StringBuilder res=new StringBuilder(x.length()*4);
		for (int i=0; i<x.length(); ++i)
		{
			int index=value(x.charAt(i));
			if (i==0)
				res.append(B[index]);
			else
				res.append(BZ[index]);
		}
		return res;
	}
	
	// convert the the binary string to hex
	StringBuilder hex(StringBuilder binary, int end)
	{
		StringBuilder res=new StringBuilder(end/4+1);
		while (end>=0)
		{
			res.insert(0,hex(dec(binary,end)));
			end-=4;
		}
		return res;
	}
	
	// convert s[end-3...end] to decimal (s is in binary)
	int dec(StringBuilder s, int end)
	{
		int res=0;
		for (int i=0; i<4; ++i)
			if (end-3+i>=0)
				res=res*2+s.charAt(end-3+i)-'0';
		return res;
	}
	
	// get hex char of decimal number n
	char hex(int n)
	{
		if (n<=9)
			return (char)(n+'0');
		return (char)(n-10+'a');
	}
	
	// get decimal value of hex char c
	int value(char c)
	{
		if (c>='0' && c<='9')
			return c-'0';
		return c-'a'+10;
	}

}
