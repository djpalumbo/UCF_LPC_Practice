// Arup Guha
// 8/10/2014
// Solution to 2014 UCF Locals Problem: Chocolate Fix

import java.util.*;
import java.io.*;

public class chocolate {

	// Constants we'll use
    final public static int SIZE = 3;
    final public static String[] PIECES = {"SV", "SS", "SC", "RV", "RS", "RC", "TV", "TS", "TC"};

    // Store common information for a case, for eas of use.
    public static String[][] perm;
    public static boolean[] used;
    public static clue[] myClues;

    public static void main(String[] args) throws Exception {

		// Open input file.
        Scanner stdin = new Scanner(new File("chocolate.in"));
        int numCases = stdin.nextInt();

        // Go through each puzzle.
        for (int loop=1; loop<=numCases; loop++) {

            // Set up storage for clues.
            int numClues = stdin.nextInt();
            myClues = new clue[numClues];

            // Read in each clue.
            for (int k=0; k<numClues; k++) {
                int numr = stdin.nextInt();
                int numc = stdin.nextInt();
                String[][] pieces = new String[numr][numc];
                for (int i=0; i<numr; i++)
                    for (int j=0; j<numc; j++)
                        pieces[i][j] = stdin.next();
                myClues[k] = new clue(pieces);
            }

            // Set storage for permutation.
            perm = new String[SIZE][SIZE];
            used = new boolean[SIZE*SIZE];

            // Case header.
            System.out.println("Puzzle #"+loop+":");
            boolean ans = solve(0, 0);
            print();
        }

        stdin.close();
    }

    public static boolean solve(int row, int col) {

        // Created a permutation.
        if (row == SIZE) {

            // See if it works...
            for (int i=0; i<myClues.length; i++)
                if (!myClues[i].isMatch(perm))
                    return false;

            // It does!
            return true;
        }

		// Typical recursion for permutation - try each unused piece in slot (row,col).
        for (int i=0; i<used.length; i++) {

        	// If we haven't used it, go ahead and see if this leads to a solution.
            if (!used[i]) {
                used[i] = true;
                perm[row][col] = PIECES[i];
                boolean res = solve((3*row+col+1)/3,(col+1)%3);
                if (res) return true;
                used[i] = false;
            }
        }

        // None of these ideas worked...
        return false;
    }

    public static void print() {
        for (int i=0; i<SIZE; i++) {
            for (int j=0; j<SIZE; j++)
                System.out.print(perm[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }
}

class clue {

    public int R;
    public int C;
    public String[][] blocks;

    public clue(String[][] pieces) {
        blocks = pieces;
        R = pieces.length;
        C = pieces[0].length;
    }

    // Returns true iff the full board all is a match for this clue.
    public boolean isMatch(String[][] all) {

        // Try each starting spot for a match.
        for (int i=0; i<=3-R; i++)
            for (int j=0; j<=3-C; j++)
                if (isMatch(all, i, j))
                    return true;

        // Never found one.
        return false;
    }

    // Returns true iff this clue matches all at starting position (sr, sc);
    public boolean isMatch(String[][] all, int sr, int sc) {

        // It's not a match if any one block doesn't match.
        for (int i=0; i<R; i++)
            for (int j=0; j<C; j++)
                if (!match(blocks[i][j], all[sr+i][sc+j]))
                    return false;

        // We go it!
        return true;
    }

    public static boolean match(String tryBlock, String sol) {

        // It's not a match if it's not wildcard AND it doesn't match the exact letter.
        for (int i=0; i<tryBlock.length(); i++)
            if (tryBlock.charAt(i) != '_' && tryBlock.charAt(i) != sol.charAt(i))
                return false;

        // We passed!
        return true;
    }
}
