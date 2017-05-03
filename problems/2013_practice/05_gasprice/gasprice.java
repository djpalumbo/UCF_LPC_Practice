import java.io.*;
import java.util.*;
import java.text.*;

public class gasprice
{
	public PrintStream out=System.out;
	public PrintStream err=System.err;
	public Scanner in=new Scanner(System.in);
	public DecimalFormat fmt=new DecimalFormat("0.000000");
	
	public String[] s;
	public char[][] v;

	public void main()
	{
		try
		{
//			err=new PrintStream(new FileOutputStream("error.log"),true);			
				int TCase,cc;
				
				long startTime=System.currentTimeMillis();
				
				s=new String[3];
				v=new char[3][];

				int i;
				TCase=Int();
				for(cc=1;cc<=TCase;++cc)
				{
					for(i=0;i<3;++i)
					{
						s[i]=Token();
						v[i]=s[i].toCharArray();
					}

					out.println("Gas Station #"+cc+":");
					out.println("   Input:  "+s[0]+" "+s[1]+" "+s[2]);
					go(0,200);
					out.println();
				}
				
				err.println("Time Spent: "+(System.currentTimeMillis()-startTime)+"ms");
		}
		catch(Exception e) { e.printStackTrace(); }
	}

	public boolean go(int i,int low)
	{
		if(i>=3) 
		{
			out.println("   Output: "+str(v[0])+" "+str(v[1])+" "+str(v[2]));
			return true;
		}

		int x = s[i].indexOf('-');
		int val;
		if(x>=0)
		{
			for(v[i][x]='0';v[i][x]<='9';++v[i][x])
			{
				val = new Integer(str(v[i]));
				if(low<=val && val<=500)
				{
					if(go(i+1,val+1)) return true;
				}
			}
		}
		else
		{
			val = new Integer(s[i]);
			if(low<=val && val<=500)
			{
				if(go(i+1,val+1)) return true;
			}
		}

		return false;
	}
	
	public String str(char[] a) { return new String(a); }

	public int Int() { return in.nextInt(); }
	public long Long() { return in.nextLong(); }
	public String Token() { return in.next(); }
	public String Line() { return in.nextLine(); }

	public static void main(String[] args)
	{
		(new gasprice()).main();
	}
}
