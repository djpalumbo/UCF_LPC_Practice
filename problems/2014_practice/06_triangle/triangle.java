// Stephen Fulwider
// Solution to Sierpinski Triangle - 2009 UCF Local Contest

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class triangle
{
	boolean[][] G = new boolean[1 << 10][1 << 10];

	triangle() throws Exception
	{
		Scanner in = new Scanner(new File("triangle.in"));
		PrintWriter out = new PrintWriter(System.out);
		for (int T=in.nextInt(),ds=1; T-->0; ++ds)
		{
			int k = in.nextInt();
			int n = 1 << k;
			for (int y = 0; y < n; y++)
				for (int x = 0; x < n; x++)
					G[y][x] = true;
			go(0, n - 1, 0, n - 1);

			out.printf("Triangle #%d:%n", ds);
			for (int y = 0; y < n; y++, out.println())
				for (int x = 0; x < n; x++)
					out.print(G[y][x] ? 'X' : ' ');
			out.println();
		}
		out.close();
	}

	void go(int x, int X, int y, int Y)
	{
		// 2x2 is base case
		if (x + 1 == X && y + 1 == Y)
			return;

		// get mid points
		int mx = (x + X) / 2;
		int my = (y + Y) / 2;
		int mlx = (x + mx) / 2;
		int mrx = (mx + 1 + X) / 2 + 1;
		
		// turn off appropriate regions
		turnOff(x, mlx, y, my);
		turnOff(mrx, X, y, my);
		
		// apply function to 3 smaller shapes
		go(mlx + 1, mrx - 1, y, my);
		go(x, mx, my + 1, Y);
		go(mx + 1, X, my + 1, Y);
	}

	void turnOff(int x, int X, int y, int Y)
	{
		for (int j = y; j <= Y; j++)
			for (int i = x; i <= X; i++)
				G[j][i] = false;
	}

	public static void main(String[] args) throws Exception
	{
		new triangle();
	}

}
