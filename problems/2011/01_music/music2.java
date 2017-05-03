// Nadeem Mohsin
// Solution to An (Almost) Perfect Match
// UCF Local Contest 2011

import java.io.*;
import java.util.*;

public class music2 {

	public music2() throws Exception {
		Scanner sc = new Scanner(new File("music.in"));
		int C = sc.nextInt();
		
		for(int c = 1; c <= C; c++) {
			K = sc.nextInt(); T = sc.nextInt();
			
			E = sc.nextInt();
			serverTracks = new int[E][];
			for(int i = 0; i < E; i++) {
				int L = sc.nextInt();
				
				serverTracks[i] = new int[L];
				for(int j = 0; j < L; j++)
					serverTracks[i][j] = sc.nextInt();
			}
			
			System.out.printf("Case #%d:\n", c);
			int U = sc.nextInt();
			for(int i = 0; i < U; i++) {
				int L = sc.nextInt();
			
				int[] userTrack = new int[L];
				for(int j = 0; j < L; j++)
					userTrack[j] = sc.nextInt();
				
				boolean match = false;
				for(int j = 0; !match && j < E; j++) 
					match |= matches(userTrack, serverTracks[j]);
				
				System.out.printf("Track #%d: ", i+1);
				if(match) System.out.println("Match found!");
				else System.out.println("Need to upload this track.");
			}
			System.out.println();
		}
	}
	
	int K, T, E, Z = 5;
	int[][] serverTracks;

	boolean matches(int[] U, int[] E) {
		user = U;
		existing = E;
		B = existing.length;
		S = user.length;
		
		if(B-Z*K > S)
			return false;
		
		seen = new boolean[S+1][B+1][K+1];
		memo = new boolean[S+1][B+1][K+1];
		return go(0, 0, K);
	}
	
	int[] existing;
	int[] user;
	int B, S;
		
	boolean[][][] seen;
	boolean[][][] memo;
	boolean go(int i, int j, int k) {
		if(seen[i][j][k]) return memo[i][j][k];
		seen[i][j][k] = true;
		
		if(i == S) return Z*k >= B-j;
		if(j == B) return false;
		
		boolean ret = false;
		
		if(Math.abs(user[i]-existing[j]) <= T)
			ret |= go(i+1, j+1, k);
		
		for(int d = 1; k > 0 && !ret && d <= Z && j+d <= B; d++)
			ret |= go(i, j+d, k-1);
		
		return memo[i][j][k] = ret;
	}
	
	
	public static void main(String[] args) throws Exception {
		new music2();
	}

}
