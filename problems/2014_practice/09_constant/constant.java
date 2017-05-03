// Stephen Fulwider
// Solution to A Constant Struggle - 2009 UCF Local Contest
// Implements an O(N^3) recursive divide and conquer approach
//	as well as an O(8*N)=O(N) dynamic programming approach

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class constant
{

	public static void main(String[] args) throws Exception
	{
		new constant();
	}
	
	int[] C = new int[8];
	int N;
	
	long[][] memo = new long[8][100];
	
	constant() throws Exception
	{
		Scanner in = new Scanner(new File("constant.in"));
		for (int T=in.nextInt(),ds=1; T-->0; ++ds)
		{
			for (int i=0; i<8; ++i)
				C[i] = in.nextInt();
			N = in.nextInt();
			
			for (long[] m : memo)
				Arrays.fill(m, -1);
			
			long res = go(0,0);
//			long res = go(0,7,N);
			System.out.printf("Equation #%d: %d%n",ds,res);
		}
	}
	
	// recursively solve w/ divide & conquer - O(N^3)
	long go(int from, int to, int N)
	{
		if (from == to)
		{
			if (N%C[from] == 0)
				return 1;
			return 0;
		}
		
		int mid = (from+to)/2;
		long res = 0;
		for (int i=0; i<=N; ++i)
		{
			res += go(from,mid,i)*go(mid+1,to,N-i);
		}
		return res;
	}
	
	// solve using DP with memoization - O(N)
	long go(int c, int n)
	{
		if (n==N)
			return 1;
		if (n>N || c==8)
			return 0;
		if (memo[c][n] > -1)
			return memo[c][n];
		return memo[c][n] = go(c,n+C[c])+go(c+1,n);
	}

}
