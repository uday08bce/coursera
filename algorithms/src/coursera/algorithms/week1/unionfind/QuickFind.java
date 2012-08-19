package coursera.algorithms.week1.unionfind;

import java.util.Arrays;

/**
 * @author Denis Zhdanov
 * @since 8/18/12 11:28 AM
 */
public class QuickFind {
  
  private static final int SIZE = 10;
  private static final int[] DATA = new int[SIZE];
  static {
    for (int i = 0; i < DATA.length; i++) {
      DATA[i] = i;
    }
  }

  public static void main(String[] args) {
    union(0, 5);
    union(2, 1);
    union(1, 5);
    union(0, 3);
    union(4, 5);
    union(6, 5);
  }

  private static void union(int p, int q) {
    int pid = DATA[p];
    int qid = DATA[q];
    for (int i = 0; i < DATA.length; i++) {
      if (DATA[i] == pid) {
        DATA[i] = qid;
      }
    }
    System.out.printf("%d-%d: %s%n", p, q, Arrays.toString(DATA));
  }
}
