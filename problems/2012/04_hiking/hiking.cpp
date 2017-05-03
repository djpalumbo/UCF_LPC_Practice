
#include <stdio.h>
#include <string.h>
#include <limits.h>

#define DEBUG   0

int main()
{
   FILE *   infile;
   char     line[1024];
   int      numTrails;
   int      t;
   int      adj[30][30];
   int      cost[30][30];
   bool     visited[30];
   int      numNodes;
   int      i;
   int      j;
   int      k;
   char     src;
   char     dest;
   int      weight;
   char     queue[256];
   char     at;
   char     going;
   int      distance;
   char     tmp[256];

   // Open the input file
   infile = fopen("hiking.in", "r");

   // Read the number of trails
   fgets(line, sizeof(line), infile);
   sscanf(line, "%d", &numTrails);

   // Loop through the trails
   for (t=0; t < numTrails; t++)
   {
      // Output "header"
      printf("Trail #%d:\n", t+1);

      // We will use an adjacency matrix to hold the "tree" (graph) and
      // a visited array for our search so initialize them 
      // (we use "max int" to represent unconnected); also keep a separate
      // "cost" matrix (for use below)
      for (i=0; i < 30; i++)
      {
         for (j=0; j < 30; j++)
         {
            adj[i][j] = INT_MAX;
            cost[i][j] = INT_MAX;
         }

         cost[i][i] = 0;
         visited[i] = false;
      }

      // Read the number of junctions and points of interest
      fgets(line, sizeof(line), infile);
      sscanf(line, "%d", &numNodes);

      // Read each edge and store it in the adjacency matrix (to make
      // things simpler we add it both directions)
      for (i=0; i < numNodes-1; i++)
      {
         // Read the edge
         fgets(line, sizeof(line), infile);
         sscanf(line, "%c %c %d", &src, &dest, &weight);

         // Add it to adjacency matrix (both directions)
         adj[src-'A'][dest-'A'] = weight;
         adj[dest-'A'][src-'A'] = weight;

         // Also keep a separate cost matrix (for next step)
         cost[src-'A'][dest-'A'] = weight;
         cost[dest-'A'][src-'A'] = weight;
      }

      // To make things easy for us, let's run Floyd's to get all
      // the shortest paths 
      // http://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
      for (k=0; k < 26; k++)
      {
         for (i=0; i < 26; i++)
         {
            for (j=0; j < 26; j++)
            {
               // Use the shorter path (through "k") if it's better
               if ( (cost[i][k] != INT_MAX) && (cost[k][j] != INT_MAX) &&
                    (cost[i][k] + cost[k][j] < cost[i][j]) )
               {
                  cost[i][j] = cost[i][k] + cost[k][j];
               }
            }
         }
      }

      // Now, we run a BFS using the alphabetical order rule from the problem;
      // since the tree nodes are letters, let's use a string as our queue
      // and enqueue "A" (where Arup always starts)
      strcpy(queue, "A");
      visited['A'-'A'] = true;

      // Now loop until the queue is empty (i.e, until the string is 0-length)
      at = 'A';
      distance = 0;
      while (strlen(queue) > 0)
      {
         // Dequeue the first node by sliding the string up (note: we do not
         // subtract 1 from strlen() so that we also move up the NULL char)
         if (DEBUG)
            printf("Queue: %s\n", queue);
         going = queue[0];
         memmove(&queue[0], &queue[1], strlen(queue));

         // Add the distance to get to this node
         distance += cost[at-'A'][going-'A'];
         if (DEBUG)
         {
            printf("Adding %d by going from %c to %c\n", 
                   cost[at-'A'][going-'A'], at, going);
         }

         // And actually go there
         at = going;

         // Enqueue the children of the node we're currently at (note that
         // this loop goes through the children in alphabetical order!)
         for (i=0; i < 26; i++)
         {
            // See if we're connected to this node (weight < max int) and
            // we haven't visited it already
            if ( (adj[at-'A'][i] < INT_MAX) && (visited[i] == false) )
            {
               // We're connected to enqueue this child by adding it to
               // the end of the queue (string) and marking it visited
               sprintf(tmp, "%c", 'A'+i);
               strcat(queue, tmp);
               visited[i] = true;
            }
         }
      }

      // The BFS is finished but we need to return to the root (start) 
      distance += cost[at-'A']['A'-'A'];

      // Output total distance traveled
      printf("BFS hike will cost %d\n", distance);

      // Output blank line after each trail
      printf("\n");
   }

   // Close input
   fclose(infile);
}

