import java.io.*;
import java.util.*;
import java.text.*;

public class holdem
{
	public PrintStream out=System.out;
	public PrintStream err=System.err;
	public Scanner in=new Scanner(System.in);
	public DecimalFormat fmt=new DecimalFormat("0.000000");
	
	public String s;
	public char[] v;

	public void main()
	{
		try
		{
//			err=new PrintStream(new FileOutputStream("error.log"),true);			
				int TCase,cc;
				
				long startTime=System.currentTimeMillis();
				
				TCase=in.nextInt();
				for(cc=1;cc<=TCase;++cc)
				{
					s=Token();
					out.println("UCF Hold-em #"+cc+": "+s);

					v=s.toCharArray();
					Arrays.sort(v);

					if(K(4))
					{
						out.println("Best possible hand: FOUR OF A KIND");
					}
					else if(Full())
					{
						out.println("Best possible hand: FULL HOUSE");
					}
					else if(K(3))
					{
						out.println("Best possible hand: THREE OF A KIND");
					}
					else if(K(2))
					{
						out.println("Best possible hand: TWO OF A KIND");
					}
					else
					{
						out.println("Best possible hand: BUST");
					}
					out.println();
				}				
				err.println("Time Spent: "+(System.currentTimeMillis()-startTime)+"ms");
		}
		catch(Exception e) { e.printStackTrace(); }
	}

	public boolean K(int k)
	{
		for(int i=0;i+k<=v.length;++i)
		if(v[i]==v[i+k-1]) return true;
		return false;
	}

	public boolean Full()
	{
		for(int i=0;i+3<=v.length;++i)
		if(v[i]==v[i+2])
		{
			for(int j=0;j+2<=v.length;++j)
			if(v[j]==v[j+1] && (j+1<i || i+2<j)) return true;
		}
		return false;
	}
	
	public int Int() { return in.nextInt(); }
	public long Long() { return in.nextLong(); }
	public String Token() { return in.next(); }
	public String Line() { return in.nextLine(); }

	public static void main(String[] args)
	{
		(new holdem()).main();
	}
}
