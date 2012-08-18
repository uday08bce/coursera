package coursera.algorithms.week1.unionfind;

import java.util.*;

/**
 * @author Denis Zhdanov
 * @since 8/18/12 11:37 AM
 */
public class WeightedQuickUnion {

  private static final int         SIZE       = 10;
  private static final int[]       DATA       = new int[SIZE];
  private static final int[]       WEIGHT     = new int[SIZE];
  static {
    for (int i = 0; i < DATA.length; i++) {
      DATA[i] = i;
      WEIGHT[i] = 1;
    }
  }
  
  public static void main(String[] args) {
    union(5, 8);
    union(6, 2);
    union(1, 6);
    union(9, 0);
    union(1, 7);
    union(0, 5);
    union(2, 4);
    union(4, 3);
    union(5, 3);
  }

  private static void union(int p, int q) {
    int rootP = root(p);
    int rootQ = root(q);
    if (rootP == rootQ) {
      return;
    }
    if (WEIGHT[rootP] < WEIGHT[rootQ]) {
      DATA[rootP] = DATA[rootQ];
      WEIGHT[rootQ] += WEIGHT[rootP];
    }
    else {
      DATA[rootQ] = DATA[rootP];
      WEIGHT[rootP] += WEIGHT[rootQ];
    }
    System.out.printf("%d-%d: %s%n", p, q, Arrays.toString(DATA));
  }

  private static int root(int i) {
    int result = DATA[i];
    while (result != DATA[result]) {
      result = DATA[result];
    }
    return result;
  }
}
