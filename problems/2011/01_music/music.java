// Stephen Fulwider
// Solution to An (Almost) Perfect Match from 2011 Fall Local Contest

import java.util.Arrays;
import java.util.Scanner;

public class music {

	public static void main(String[] args) {
		new music();
	}
	
	final int oo=(int)1e9;
	
	int K,T;
	int En;
	int[][] E=new int[100][];
	int[] U;
	
	int[][] memo=new int[100][100];
	
	music() {
		Scanner in=new Scanner(System.in);
		for (int TC=in.nextInt(),Tn=1; TC-->0; ++Tn) {
			K=in.nextInt();
			T=in.nextInt();
			
			En=in.nextInt();
			for (int i=0; i<En; ++i) {
				E[i]=new int[in.nextInt()];
				for (int j=0; j<E[i].length; ++j) {
					E[i][j]=in.nextInt();
				}
			}
			
			System.out.printf("Case #%d:%n", Tn);
			int Un=in.nextInt();
			for (int i=0; i<Un; ++i) {
				U=new int[in.nextInt()];
				for (int j=0; j<U.length; ++j) {
					U[j]=in.nextInt();
				}
				boolean match=false;
				for (int e=0; e<En && !match; ++e) {
					for (int j=0; j<E[e].length; ++j) {
						Arrays.fill(memo[j], 0, U.length, -1);
					}
					match = go(0,0,e) <= K;
				}
				System.out.printf("Track #%d: %s%n", i+1, match ? 
						"Match found!" : "Need to upload this track.");
			}
			System.out.println();
			
		}
	}
	
	// e:existing u:user en:existing track number
	int go(int e, int u, int en) {
		if (u==U.length) {
			return (E[en].length-e+4)/5;
		} else if (e==E[en].length) {
			return oo;
		} else if (memo[e][u] > -1) {
			return memo[e][u];
		}
		
		int res=oo;
		
		// Match these blocks if they're within the tolerance.
		if (Math.abs(E[en][e]-U[u]) <= T) {
			res=Math.min(res,go(e+1,u+1,en));
		}
		
		// Delete between 1 and 5 blocks (i.e. delete a section).
		for (int i=1; i<=5; ++i) {
			if (e+i<=E[en].length) {
				res=Math.min(res, 1+go(e+i,u,en));
			}
		}
		
		return memo[e][u]=res;
	}

}
