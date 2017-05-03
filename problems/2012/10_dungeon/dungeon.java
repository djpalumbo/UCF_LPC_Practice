import java.util.Arrays;
import java.util.Scanner;

/**
 * Dungeon Trouble! Solution --- UCF Local Contest - September 1, 2012
 *
 *  This problem breaks down to deciding whether the graph composed of unnumbered nodes is
 * 2-colorable. In graph theory, a graph is k-colorable if, given k colors to use, you can assign a
 * color to each node so that no two adjacent nodes share the same color. In general, this problem
 * is NP-Complete, but for the case of 2 colors, you can solve it in polynomial time by simply
 * trying the first color on the first node you see, which then forces the color of all of its
 * neighbors, when then force the color of their neighbors, and so on. In this way you never have to
 * backtrack, so it only takes a single pass through the graph to determine whether the graph is 2
 * colorable.
 *
 * A solution implementing this walk over the graph using a dfs is implemented below.
 *
 * @author Stephen Fulwider
 */
public class dungeon {

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


  /** Number of dungeons */
  int N;

  /** Adj. matrix rep. of graph */
  boolean[][] G = new boolean[26][26];

  /** Number assigned to each dungeon */
  int[] C = new int[26];

  dungeon() {
    Scanner in = new Scanner(System.in);
    for (int T = in.nextInt(), TC = 1; T-- > 0; ++TC) {
      readGraph(in);
      boolean canComplete = solveGraph();
      System.out.printf(
          "Dungeon #%d: %s%n%n", TC, canComplete ? COMPLETION_MESSAGE : NOT_COMPLETION_MESSAGE);
    }
  }

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

  /**
   * Read the graph from the input. Clears the graph from memory first.
   *
   * @param in the input scanner.
   */
  void readGraph(Scanner in) {
    // Clear the graph.
    for (int i = 0; i < G.length; ++i) {
      Arrays.fill(G[i], false);
    }
    Arrays.fill(C, NO_NUMBER);

    // Build the graph.
    N = in.nextInt();
    int m = in.nextInt();
    while (m-- > 0) {
      int i = in.next().charAt(0) - 'A';
      int j = in.next().charAt(0) - 'A';
      G[i][j] = G[j][i] = true;
    }

    // Assign the default numbers.
    int k = in.nextInt();
    while (k-- > 0) {
      int i = in.next().charAt(0) - 'A';
      int c = in.nextInt();
      C[i] = c;
    }
  }

  public static void main(String[] args) {
    new dungeon();
  }

}
