import java.io.*;
import java.util.*;

public class pickmen {
	public static void main(String[] args) throws IOException
	{
		Scanner fin = new Scanner(new File("pickmen.in"));
		int sets = fin.nextInt();
		
		
		
		for(int set = 1; set <= sets; set++)
		{
			Vector<Edge>[] adjlist;
			int enemy[];
			boolean hit[];
			int V = fin.nextInt();
			int E = fin.nextInt();
			
			LinkedList<State> deque = new LinkedList<State>();
			
			deque.addFirst(new State(fin.nextInt(),fin.nextInt(),fin.nextInt(),fin.nextInt()));
						
			hit = new boolean[V];
			enemy = new int[V];
			adjlist = new Vector[V];
			for(int i=0;i<V;i++)
			{
				hit[i]=false;
				adjlist[i] = new Vector<Edge>();
				enemy[i] = fin.nextInt();
			}
			
			
			for(int i=0;i<E;i++)
			{
				int start = fin.nextInt();
				int end = fin.nextInt();
				String hazmat = fin.next();
				
				adjlist[start-1].add(new Edge(end-1,hazmat));
				adjlist[end-1].add(new Edge(start-1,hazmat));
			}
			State crnt=null;
			while(!deque.isEmpty())
			{
				crnt = deque.removeFirst();
				
				// System.out.println("Node "+(crnt.node+1));
				
				if(crnt.node == V-1)
				{
				// System.out.println("Final destination");
					break;
				}
				for(Edge e : adjlist[crnt.node])
				{
					if(hit[e.end])
						continue;
					State next = crnt.transition(e,enemy[e.end]);
					if(next == null)
						continue;
					if(enemy[e.end] == 0)
						deque.addFirst(next);
					else
						deque.addLast(next);
					hit[e.end]=true;
				}
			}
			
			System.out.print("Cave #"+set+": Commander Oroojimar ");
			if(crnt.node == V-1)
				System.out.println("can escape with "+crnt.getTotal()+" Pick Men.");
			else
				System.out.println("is doomed.");
			System.out.println();
		}
	}
}

class State
{
	private int red;
	private int yellow;
	private int blue;
	private int white;
	
	public int node;
	
	private static int max(int x, int y)
	{
		return (x>y)?x:y;
	}
	
	public State(int red, int yellow, int blue, int white)
	{
		this.red = max(0,red);
		this.yellow = max(0,yellow);
		this.blue = max(0,blue);
		this.white = max(0,white);
		
		node = 0;
	}
	
	private State(int red, int yellow, int blue, int white, int node)
	{
		this(red,yellow,blue,white);
		this.node = node;
	}
	public int getTotal()
	{
		return red+yellow+blue+white;
	}
	
	public State transition(Edge e, int hazmat)
	{
	//	System.out.println("Attempting edge ("+(node+1)+","+(e.end+1)+"):");
		if(e.fire && red == 0)
			return null;
		if(e.water && blue == 0)
			return null;
		if(e.elec && yellow == 0)
			return null;
		if(e.pois && white == 0)
			return null;
	//	System.out.println("  Edge safe");
		if(hazmat > getTotal())
			return null;
	//	System.out.println("  Destination safe");
		if(hazmat != 0)
			return new State(red-1,yellow-1,blue-1,white-1,e.end);
		return new State(red,yellow,blue,white,e.end);
	}	
}

class Edge
{
	public boolean fire;
	public boolean elec;
	public boolean water;
	public boolean pois;
	public int end;
	
	public Edge(int end, String hazards)
	{
		this.end = end;
		
		if(hazards.charAt(0) == 'N')
			return;
		
		for(int i=0;i<hazards.length();i++)
		{
			if(hazards.charAt(i) == 'F')
				fire = true;
			if(hazards.charAt(i) == 'E')
				elec = true;
			if(hazards.charAt(i) == 'W')
				water = true;
			if(hazards.charAt(i) == 'P')
				pois = true;
		}
		
	}
}