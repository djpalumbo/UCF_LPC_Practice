import java.io.*;
import java.util.*;
import java.text.*;

public class robot
{
	public PrintStream out=System.out;
	public PrintStream err=System.err;
	public Scanner in=new Scanner(System.in);
	public DecimalFormat fmt=new DecimalFormat("0.000000");
	
	public int[] dx={1,0,-1,0};
	public int[] dy={0,-1,0,1};

	public int x,y;
	public int dir;

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
					out.println("Robot Program #"+cc+":");

					x = y = 0;
					dir = 0;

					sim();
					out.println("The robot is at ("+x+","+y+")");

					go(Int(),Int());
					go(Int(),Int());

					out.println();
				}
				
				err.println("Time Spent: "+(System.currentTimeMillis()-startTime)+"ms");
		}
		catch(Exception e) { e.printStackTrace(); }
	}

	public void sim()
	{
		int n = Int();
		String cmd;
		int amt;
		for(int i=0;i<n;++i)
		{ 
			cmd=Token();
			if(cmd.equals("MOVE"))
			{
				amt = Int();
				x+=dx[dir]*amt;
				y+=dy[dir]*amt;
			}
			else if(cmd.equals("LEFT"))
			{
				dir=(dir+3)%4;
			}
			else if(cmd.equals("RIGHT"))
			{
				dir=(dir+1)%4;
			}
			else if(cmd.equals("UTURN"))
			{
				dir=(dir+2)%4;
			}
		}
	}

	public void go(int tx,int ty)
	{
		int tdir;
		if(x!=tx)
		{
			tdir = (x<tx?0:2);
			goDir(tdir);		
			out.println("MOVE "+Math.abs(x-tx));
			x=tx;
		}
		
		if(y!=ty)
		{
			tdir = (y<ty?3:1);
			goDir(tdir);
			out.println("MOVE "+Math.abs(y-ty));
			y=ty;
		}
	}

	public void goDir(int tdir)
	{
		if((dir+1)%4==tdir)
		{
			out.println("RIGHT");
		}
		else if((dir+3)%4==tdir)
		{
			out.println("LEFT");
		}
		else if((dir+2)%4==tdir)
		{
			out.println("UTURN");
		}
		dir=tdir;
	}
	
	public int Int() { return in.nextInt(); }
	public long Long() { return in.nextLong(); }
	public String Token() { return in.next(); }
	public String Line() { return in.nextLine(); }

	public static void main(String[] args)
	{
		(new robot()).main();
	}
}
