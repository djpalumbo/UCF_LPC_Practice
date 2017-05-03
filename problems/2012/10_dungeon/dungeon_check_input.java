// Dungeon Trouble! Input Verifier
// Author: Stephen Fulwider

import java.util.*;
import java.io.*;

public class dungeon_check_input {
  
  dungeon_check_input() {
    
    Scanner in=new Scanner(System.in);
    for (int T=in.nextInt(),TC=1; T-->0; ++TC) {
      
      
      int N=in.nextInt();
      assertTrue(N>=1 && N<=26, "n out of range",TC);
      boolean[][] G=new boolean[N][N];
      int[] C=new int[N];
      Arrays.fill(C,-1);
      
      int m=in.nextInt();
      assertTrue(m>=1 && m<=325, "m out of range",TC);
      
      while (m-->0) {
        int i=in.next().charAt(0)-'A';
        assertTrue(i>=0 && i<N, "i not A-Z",TC);
        int j=in.next().charAt(0)-'A';
        assertTrue(j>=0 && j<N, "j not A-Z",TC);
        
        assertTrue(i<j, "i not strictly smaller than j",TC);
        G[i][j]=G[j][i]=true;
      }
      
      int k=in.nextInt();
      assertTrue(0<=k && k<=N, "k out of range",TC);
      
      while (k-->0) {
        int i=in.next().charAt(0)-'A';
        assertTrue(i>=0 && i<=25, "i not A-Z (dungeon #ing)",TC);
        int c=in.nextInt();
        assertTrue(c==2||c==3||c==5, "c not 2,3, or 5",TC);
        assertTrue(C[i]==-1, "dungeon already numbered",TC);
        C[i]=c;
      }
      
      for (int i=0; i<N; ++i) {
        if (C[i]!=-1) {
          for (int j=0; j<N; ++j) {
            if (G[i][j]) {
              assertTrue(C[i]!=C[j], String.format("dungeon %d,%d adjacent and have same #!",i,j),TC);
            }
          }
        }
      }
      
      boolean[] V=new boolean[N];
      dfs(0,V,G);
      for (int i=0; i<N; ++i) {
        assertTrue(V[i],"graph should be connected but "+i+" is isolated!",TC);
      }
      
      System.out.printf("Test case %d OK%n",TC);
    }
    System.out.println("All test cases look OK!");
  }
  
  void dfs(int at, boolean[] V, boolean[][] G) {
    if (V[at]) return;
    V[at]=true;
    for (int i=0; i<V.length; ++i) {
      if (G[at][i]) {
        dfs(i,V,G);
      }
    }
  }
  
  
  
  void assertTrue(boolean condition, String msg, int T) {
    if (!condition) {
      System.err.printf("Test case %d: %s%n",T,msg);
      System.exit(1);
    }
  }
  
  public static void main(String[] args) {
    new dungeon_check_input();
  }
}
