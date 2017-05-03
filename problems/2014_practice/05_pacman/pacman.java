// Arup Guha
// 9/2/09
// Solution to 2009 UCF Local Contest Problem: Pac-Man

import java.util.*;
import java.io.*;

public class pacman {

    public static void main(String[] args) throws Exception {

        Scanner fin = new Scanner(new File("pacman.in"));
        int numcases = fin.nextInt();

        // main loop to process each case.
        for (int loop=1; loop<=numcases; loop++) {

            // Case header
            System.out.print("Game Board #"+loop+": ");

            // Read in board size.
            int numRows = fin.nextInt();
	    	int numCols = fin.nextInt();
            int[][] grid = new int[numRows][numCols];

            // Read in the board.
            for (int i=0; i<numRows; i++) {
                for (int j=0; j<numCols; j++) {

                    // Mathematically, it's fine to put in 0 for our two special squares.
                    if ((i==0 && j == 0) || (i==numRows-1 && j==numCols-1)) {
                        String dummy = fin.next();
                        grid[i][j] = 0;
                    }
                    else
                        grid[i][j] = fin.nextInt();
                } 
            }

            // Create an array to store all the best Pac Man scores.
            int[][] bestScores = new int[numRows][numCols];
	    	bestScores[0][0] = 0;	

            // Fill in the first row, these scores are obvious.
            for (int i=1; i<numCols; i++)
                bestScores[0][i] = bestScores[0][i-1]+grid[0][i];

            // Same for the first column.
            for (int i=1; i<numRows; i++)
                bestScores[i][0] = bestScores[i-1][0] + grid[i][0];

            // Loop through each possible Pac Man subproblem and solve it.
            for (int i=1; i<numRows; i++)
                for (int j=1; j<numCols; j++)

                    // One of these two scores is the one you want, then just add
                    // the current "goody" score.    
                    if (bestScores[i-1][j] > bestScores[i][j-1])
                        bestScores[i][j] = bestScores[i-1][j] + grid[i][j];
                    else
                        bestScores[i][j] = bestScores[i][j-1] + grid[i][j];
            
            // Now, just need to output the answer stored in lower right square.
            System.out.println(bestScores[numRows-1][numCols-1]+"\n");            
        }

        fin.close();     
    }
}
