// Nadeem Mohsin
// Solution to A Prickly Problem
// UCF Locals 2011.

import java.util.*;
import java.io.*;

public class cactus2 {

	final int MOD = 1007;
	final int MAXV = 50000;
	
	public cactus2() throws Exception {
		Scanner sc = new Scanner(new File("cactus.in"));
		int T = sc.nextInt();
		for(int t = 1; t <= T; t++) {
			V = sc.nextInt(); E = sc.nextInt();
			for(int i = 0; i < V; i++) {
				adj[i] = new ArrayList<Integer>();
				L[i] = -1;
			}
			
			for(int i = 0; i < E; i++) {
				int u = sc.nextInt()-1, v = sc.nextInt()-1;
				adj[u].add(v); 
				adj[v].add(u);
			}

			System.out.printf("Case #%d: %d\n\n", t, go(0, -1, 0));
		}
	}
	
	int V, E;
	ArrayList<Integer>[] adj = new ArrayList[MAXV];
	int[] L = new int[MAXV];
	
	int go(int u, int p, int len) {
		if(L[u] >= 0) return len >= L[u] ? (len-L[u]) % MOD : 1;
		L[u] = len;
		
		int ret = 1;
		for(int v: adj[u])
			if(v != p)
				ret = (ret * go(v, u, len+1)) % MOD;
			
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		new cactus2();
	}

}
