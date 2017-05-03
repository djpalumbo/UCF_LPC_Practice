import java.io.File;
import java.util.Scanner;

public class bulk {
	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(new File("bulk.in"));

		int cases = in.nextInt();

		for (int run = 0; run < cases; run++) {

			int c = in.nextInt();
			int p = in.nextInt();

			// tally up the first item
			int total = p;

			// for each item beyond the first, add 2 less than the price
			for (int i = 1; i < c; i++) {
				total += p - 2;
			}
			System.out.println(c + " " + p);
			System.out.println(total);
		}
	}
}
