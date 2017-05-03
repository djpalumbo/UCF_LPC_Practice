// Arup Guha
// 8/17/09
// Solution to 2009 UCF Local Contest Problem: Walking in the Sun (sunwalk)

import java.util.*;
import java.io.*;

// Stores a circle and allows for distance calculations between two
// circle objects.
class circle {

    private double x;
    private double y;
    private double r;

    // A degenerate circle, which is a point.
    public circle(double myx, double myy) {
        x = myx;
        y = myy;
        r = 0;
    }

    // To be used for a regular circle.
    public circle(double myx, double myy, double myr) {
        x = myx;
        y = myy;
        r = myr;
    }

    // Calculates the distance between this circle and p.
    public double dist(circle p) {

        // This is the distance between the centers of the circles.
        double regDist = Math.sqrt(Math.pow(x-p.x,2)+Math.pow(y-p.y,2));

        // This calculation works between the circles so long as they don't touch.
        double adjDist = regDist - r - p.r;

        // If the circles touch, the distance between them is 0.
        if (adjDist < 0)
            adjDist = 0;

        return adjDist;
    }
}


public class sunwalk {

    public static void main(String[] args) throws Exception {

        Scanner fin = new Scanner(new File("sunwalk.in"));
        int numcases = fin.nextInt();

        // main loop to process each case.
        for (int loop=1; loop<=numcases; loop++) {

            // Case header
            System.out.println("Campus #"+loop+":");
            
            // First read in the shade areas.
            int numshade = fin.nextInt();
            circle[] shade = new circle[numshade];

			// This data comes first, three items per shade area.
            for (int i=0; i<numshade; i++) {
                double x = fin.nextDouble();            
                double y = fin.nextDouble();
                double r = fin.nextDouble();
                shade[i] = new circle(x,y,r);
            }

            // Now read in the campus locations, but store them in a new array that
            // stores all locations, with the shade first. Note: locations come in pairs.
            int numlocations = fin.nextInt();
	        circle[] all = new circle[numshade + 2*numlocations]; 
	        
	        // Copy in the shade locations.
            for (int i=0; i<numshade; i++)
                all[i] = shade[i];
 
            // Now place the campus locations in the same array at the end.
            for (int i=numshade; i<all.length; i++) {
                double x = fin.nextDouble();
                double y = fin.nextDouble();
                all[i] = new circle(x,y);
            }        

            // Create the adjacency matrix for this campus.
            double[][] adjMatrix = new double[all.length][all.length];
            for (int i=0; i<adjMatrix.length; i++)
                for (int j=0; j<adjMatrix[i].length; j++) 
                    adjMatrix[i][j] = all[i].dist(all[j]);
            
            // Run Floyd-Warshall's Algorithm.    
            for (int k=0; k<adjMatrix.length; k++)
                for (int i=0; i<adjMatrix.length; i++)
                    for (int j=0; j<adjMatrix.length; j++)
                        adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k]+adjMatrix[k][j]);

            // Answer Queries (there are numlocations of these).
            for (int i=0; i<numlocations; i++) {

                // Calculate the two indexes in the adjacency matrix for this query.
                // Note: They are staggered every 2 items, this explains the 2*i term.
                int locOne = numshade + 2*i;
                int locTwo = numshade + 2*i + 1;

                // Output the answer.
                System.out.printf("  Path #%d: Shortest sun distance is %.1f.\n", i+1, adjMatrix[locOne][locTwo]);
            }
            
            System.out.println();
        }

        fin.close();     
    }
}
