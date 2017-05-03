// Dungeon Trouble! Input Generator
// Author: Stephen Fulwider

import java.util.*;
import java.io.*;

public class dungeons_generator {

  dungeons_generator() {
    
    for (int i=7; i<=26; ++i) {
      genRandomPossible(i);
    }
    for (int i=7; i<=26; ++i) {
      genRandomImpossible(i);
    }
    
    
    
    for (int i=7; i<=26; ++i) {
      getRandom(i);
      getRandom(i);
    }
    
  }
  
  Random rng = new Random(0);
  
  void init(int size) {
    N=size;
    G=new boolean[N][N];
    C=new int[N];
    Arrays.fill(C,-1);
  }
  
  void genRandomPossible(int size) {
    init(size);
    
    for (int i=0; i<500; ++i) {
      int a=rng.nextInt(size);
      int b=rng.nextInt(size);
      while (a==b) {
        b=rng.nextInt(size);
      }
      if (G[a][b]) {
        continue;
      }
      G[a][b]=G[b][a]=true;
      Arrays.fill(C,-1);
      if (!solveGraph()) {
        G[a][b]=G[b][a]=false;
      }
    }
    
    boolean[] V=new boolean[size];
    dfs(0,V);
    for (int i=0; i<size; ++i) {
      if (!V[i]) {
        System.err.println("made a graph that's not connected of size " + size);
        genRandomPossible(size);
        return;
      }
    }
    
    int edges=0;
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) ++edges;
      }
    }
    
    System.out.printf("%d %d%n",size,edges);
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) {
          System.out.printf("%c %c%n",i+'A',j+'A');
        }
      }
    }
    
    System.out.println(0);
  }
  
  void genRandomImpossible(int size) {
    init(size);
    
    for (int i=0; i<500; ++i) {
      int a=rng.nextInt(size);
      int b=rng.nextInt(size);
      while (a==b) {
        b=rng.nextInt(size);
      }
      if (G[a][b]) {
        continue;
      }
      G[a][b]=G[b][a]=true;
      Arrays.fill(C,-1);
      if (!solveGraph()) {
        i+=50;
      }
    }
    
    if (solveGraph()) {
      System.err.printf("Didn't fuck up the graph!");
      System.exit(1);
    }
    
    boolean[] V=new boolean[size];
    dfs(0,V);
    for (int i=0; i<size; ++i) {
      if (!V[i]) {
        System.err.println("made a graph that's not connected of size " + size);
        genRandomImpossible(size);
        return;
      }
    }
    
    int edges=0;
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) ++edges;
      }
    }
    
    System.out.printf("%d %d%n",size,edges);
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) {
          System.out.printf("%c %c%n",i+'A',j+'A');
        }
      }
    }
    
    System.out.println(0);
  }
  
  
  void getRandom(int size) {
    init(size);
    
    for (int i=0; i<1.1*size; ++i) {
      int a=rng.nextInt(size);
      int b=rng.nextInt(size);
      while (a==b) {
        b=rng.nextInt(size);
      }
      if (G[a][b]) {
        continue;
      }
      G[a][b]=G[b][a]=true;
    }
    
    boolean[] V=new boolean[size];
    dfs(0,V);
    for (int i=0; i<size; ++i) {
      if (!V[i]) {
        System.err.println("made a graph that's not connected of size " + size);
        getRandom(size);
        return;
      }
    }
    
    int edges=0;
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) ++edges;
      }
    }
    
    System.out.printf("%d %d%n",size,edges);
    for (int i=0; i<size; ++i) {
      for (int j=i+1; j<size; ++j) {
        if (G[i][j]) {
          System.out.printf("%c %c%n",i+'A',j+'A');
        }
      }
    }
    
    Arrays.fill(C,-1);
    int cnt=0;
    for (int i=0; i<size; ++i) {
      if (rng.nextInt(100)<10) {
        boolean num2=true,num3=true,num5=true;
        for (int j=0; j<size; ++j) {
          if (G[i][j]) {
            if (C[j]==2) num2=false;
            if (C[j]==3) num3=false;
            if (C[j]==5) num5=false;
          }
        }
        if (num2) C[i]=2;
        else if (num3) C[i]=3;
        else if (num5) C[i]=5;
        if (C[i]!=-1) ++cnt;
      }
    }
    
    System.out.println(cnt);
    for (int i=0; i<size; ++i) {
      if (C[i]!=-1) {
        System.out.printf("%c %d%n",i+'A',C[i]);
      }
    }
  }
  
  
  void dfs(int at, boolean[] V) {
    if (V[at]) return;
    V[at]=true;
    for (int i=0; i<V.length; ++i) {
      if (G[at][i]) {
        dfs(i,V);
      }
    }
  }
  
  
  
  int N;
  boolean[][] G;
  int[] C;
  
  /** Sentinel value for a dungeon which hasn't yet been numbered. */
   final int NO_NUMBER = -1;

   /** Message for playing to completion. */
   final String COMPLETION_MESSAGE = "Ash and Hazel played this game to completion";

   /** Message for not playing to completion. */
   final String NOT_COMPLETION_MESSAGE = "Ash and Hazel did not complete this game";

   /** The first candidate value a node can take on. */
   final int CANDIDATE_1 = 1;

   /** The second candidate value a node can take on. */
   final int CANDIDATE_2 = 4;
  
  
  /**
   * Determine if the graph can be played to completion.
   *
   * @return true if this graph can be played to completion, false otherwise.
   */
  boolean solveGraph() {
    boolean can = true;

    // Perform a search starting at each unnumbered node.
    for (int i = 0; i < N && can; ++i) {
      if (C[i] == NO_NUMBER) {
        can &= go(i);
      }
    }
    return can;
  }

  /**
   * Perform a dfs of the graph from the given node, coloring each previously unnumbered node as it
   * goes.
   *
   * @param at the current node
   * @return true if this graph is 2-colorable, false otherwise.
   */
  boolean go(int at) {
    if (C[at] != NO_NUMBER) {
      return true;
    }

    // This graph is not numbered, get a candidate value.
    int candidate = getCandidate(at);
    if (candidate == NO_NUMBER) {
      // This graph is not 2-colorable!
      return false;
    }
    C[at] = candidate;

    // Now walk to all adjacent nodes and solve the same problem.
    boolean res = true;
    for (int i = 0; i < N && res; ++i) {
      if (G[at][i]) {
        res &= go(i);
      }
    }
    return res;
  }

  /**
   * Get a candidate value for this node that doesn't conflict with any adjacent nodes.
   *
   * @param at the node to consider.
   * @return the candidate value, or the no number sentinel value if no candidate value exists.
   */
  int getCandidate(int at) {
    boolean can1 = true;
    boolean can2 = true;

    // For each adjacent node, make sure it's not already one of the 2 candidate values.
    for (int i = 0; i < N; ++i) {
      if (G[at][i]) {
        can1 &= (C[i] != CANDIDATE_1);
        can2 &= (C[i] != CANDIDATE_2);
      }
    }

    // Return whichever candidate works (or if neither work).
    if (can1) {
      return CANDIDATE_1;
    } else if (can2) {
      return CANDIDATE_2;
    } else {
      return NO_NUMBER;
    }
  }
  
  
  
  
  
  
  
  public static void main(String[] args) {
    new dungeons_generator();
  }

}