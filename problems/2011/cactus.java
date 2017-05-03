// Stephen Fulwider
// Solution to A Prickly Problem from 2011 Fall Local Contest (Used to solve Gold & Black version)

import java.util.ArrayList;
import java.util.Scanner;

public class cactus {

	public static void main(String[] args) {
		new cactus();
	}
	
	final static int M = 1007;
	
	int V;
	ArrayList<Integer>[] GE = new ArrayList[50000];
	int[] D=new int[50000];
	
	cactus() {
		for (int i=0; i<GE.length; ++i) {
			GE[i]=new ArrayList<Integer>();
		}
		
		Scanner in=new Scanner(System.in);
		for (int TC=in.nextInt(),T=1; TC-->0; ++T) {
			V=in.nextInt();
			for (int i=0; i<V; ++i) {
				GE[i].clear();
				D[i]=-1;
			}
			
			int E=in.nextInt();
			if (2*E > 3*V) {
				// Sanity check for ratio.
				System.err.printf("The ratio is too damn high! Case %d",T);
				System.exit(1);
			}
			
			while (E-->0) {
				int a=in.nextInt()-1;
				int b=in.nextInt()-1;
				GE[a].add(b);
				GE[b].add(a);
			}
			
			int res = go(0,-1,0);
			System.out.printf("Case #%d: %d%n%n", T, res);
		}
	}

	// DFS, keeping track of total length traveled so far.
	int go(int at, int p, int len) {
		if (D[at] > -1) {
			if (D[at] < len) {
				// We check this condition to ensure we haven't just entered a loop we've already counted.
				return len-D[at];
			}
			// We've already seen this loop, don't change our result.
			return 1;
		}
		
		D[at]=len;
		int res=1;
		for (int to : GE[at]) {
			if (to != p) {
				res=(res*go(to,at,len+1))%M;
			}
		}
		return res;
	}
}
