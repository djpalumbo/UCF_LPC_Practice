import java.io.*;
import java.util.*;

public class simpoly
{
   public static void main(String[] argv)
   {
      simpoly x = new simpoly();
      try { x.runner(); } catch (IOException e) { e.printStackTrace(); return; }
      
   }

   static boolean DEBUG = false;

   public void runner() throws IOException
   {
      BufferedReader in = new BufferedReader(new FileReader("simpoly.in"));
      StringTokenizer st;
      int m, n;
      int setNumber = 1;

      st = new StringTokenizer(in.readLine().trim());
      m = Integer.parseInt(st.nextToken());
      n = Integer.parseInt(st.nextToken());
      while ((m != 0) && (n != 0))
      {
         // Declare arrays to hold the coordinates of vertical slices. As each
         // polygon moves through the space, the coordinates where it cuts
         // through a particular vertical slice are recorded. Later, these
         // coordinates are used to determine the overlapping area in each
         // slice, which is added up to yield the total area.
         int[][] aVerticalSlices = new int[1001][50];
         int[][] bVerticalSlices = new int[1001][50];
         int[] aCrossCount = new int[1001];
         int[] bCrossCount = new int[1001];

         // These variables will store the vertex data for each polygon
         int[] aVertices = new int[m * 2];
         int[] bVertices = new int[n * 2];

         // Read the coordinates for the first polygon.
         st = new StringTokenizer(in.readLine().trim());
         for (int i = 0; i < m; i++)
         {
            aVertices[(i * 2)] = Integer.parseInt(st.nextToken());
            aVertices[(i * 2) + 1] = Integer.parseInt(st.nextToken());
         }

         // Read the coordinates for the second polygon.
         st = new StringTokenizer(in.readLine().trim());
         for (int i = 0; i < n; i++)
         {
            bVertices[(i * 2)] = Integer.parseInt(st.nextToken());
            bVertices[(i * 2) + 1] = Integer.parseInt(st.nextToken());
         }

         // Fill in the slices based on the vertex data.
         if (DEBUG) System.out.println("Slicing A:");
         fillSlices(aVertices, m, aVerticalSlices, aCrossCount);

         if (DEBUG) System.out.println("Slicing B:");
         fillSlices(bVertices, n, bVerticalSlices, bCrossCount);

         // Send the slices off to a method to solve for their common area.
         int solution = solveArea(aVerticalSlices, aCrossCount,
            bVerticalSlices, bCrossCount);

         // Print the solution in the proper format.
         System.out.println("Data set #" + setNumber + ": " + solution);
         System.out.println();

         // Read the m and n values for the next data set.
         st = new StringTokenizer(in.readLine().trim());
         m = Integer.parseInt(st.nextToken());
         n = Integer.parseInt(st.nextToken());
         setNumber++;
      }
   }

   void fillSlices(int[] vertices, int vertexCount, int[][] slices,
      int[] crossCount)
   {
      int x1, y1, x2, y2;

      // Process each pair of vertices. Take advantage of modular arithmetic
      // to return back to the first vertex.
      for (int i = 0; i < vertexCount; i++)
      {
         // Read the appropriate pair of vertices, wrapping around.
         x1 = vertices[(i*2)];
         y1 = vertices[(i*2)+1];
         x2 = vertices[((i*2)+2) % (vertexCount * 2)];
         y2 = vertices[((i*2)+3) % (vertexCount * 2)];

         // See if the vertices constitute a vertical or horizontal segment.
         if (x1 == x2)
         {
            // This is a vertical segment. Ignore it completely.
         }
         else
         {
            // This is a horizontal segment. Traverse from the lower coordinate
            // to the upper, recording the y-coordinate at which each vertical
            // slice was crossed.
            if (DEBUG) System.out.println("Slicing from (" + x1 + ", " + y1 +
               ") to (" + x2 + ", " + y2 + ")");
            for (int x = Math.min(x1, x2); x < Math.max(x1, x2); x++)
            {
               slices[x][crossCount[x]] = y1;
               crossCount[x]++;

               if (DEBUG) System.out.println("Slice[" + x + "][" +
                  (crossCount[x] - 1) + "] = " + y1);
            }
         }
      }
   }

   int solveArea(int[][] aSlices, int[] aCrossCount, int[][] bSlices,
      int[] bCrossCount)
   {
      int commonArea = 0;

      // Traverse each slice, one at a time.
      for (int slice = 0; slice <= 1000; slice++)
      {
         // Create new arrays to hold slice data to be sorted.
         int[] aSortedSlice = new int[aCrossCount[slice]];
         int[] bSortedSlice = new int[bCrossCount[slice]];

         // For each slice, store its valid data into the new array and sort
         // it in default (increasing) order. This will give pairs of points
         // indicating at which value within the slice the polygon was entered
         // and at which value it exited. Since the polygon is axis-aligned and
         // simple, the number of times the edge of the polygon crosses each
         // slice will be an even number, guaranteeing that each pair will be
         // complete.
         for (int cross = 0; cross < aCrossCount[slice]; cross++)
            aSortedSlice[cross] = aSlices[slice][cross];
         Arrays.sort(aSortedSlice);

         for (int cross = 0; cross < bCrossCount[slice]; cross++)
            bSortedSlice[cross] = bSlices[slice][cross];
         Arrays.sort(bSortedSlice);

         // Now move through the sorted lists of values at which the slice is
         // crossed by the edges of the polygons, accumulating area. Rather
         // than attempting a more clever treatment, simply check each pair of
         // entry-exit points in the slice for overlap and sum the result.
         for (int aCross = 0; aCross < aCrossCount[slice]; aCross += 2)
         {
            int aStart = aSortedSlice[aCross];
            int aStop = aSortedSlice[aCross + 1];

            for (int bCross = 0; bCross < bCrossCount[slice]; bCross += 2)
            {
               int bStart = bSortedSlice[bCross];
               int bStop = bSortedSlice[bCross + 1];
            
               // The area of the overlap is equal to the minimum of the two
               // stopping points minus the maximum of the two starting points.
               // This value may not be positive, indicating no overlap.
               int overlap = Math.min(aStop, bStop) - Math.max(aStart, bStart);
               if (overlap > 0)
               {
                  commonArea += overlap;

                  if (DEBUG) System.out.println("Slice[" + slice + "]: (" +
                     aStart + ", " + aStop + ") to (" + bStart + ", " +
                     bStop + ") = " + overlap);
               }
            }
         }
      }

      return commonArea;
   }
}
