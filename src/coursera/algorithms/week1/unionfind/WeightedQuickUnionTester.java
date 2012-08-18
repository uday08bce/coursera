package coursera.algorithms.week1.unionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author Denis Zhdanov
 * @since 8/18/12 12:29 PM
 */
public class WeightedQuickUnionTester {

  public static void main(String[] args) {
    test(new int[] { 2, 3, 2, 4, 2, 2, 2, 6, 3, 3 });
    test(new int[] { 1, 1, 1, 0, 6, 8, 1, 1, 6, 1 });
    test(new int[] { 0, 1, 2, 3, 7, 5, 7, 7, 3, 9 });
    test(new int[] { 9, 0, 0, 0, 1, 0, 0, 1, 1, 4 });
    test(new int[] { 4, 8, 5, 8, 5, 8, 3, 7, 9, 9 });
  }

  private static void test(int[] data) {
    String error = null;
    String message = checkCycle(data);
    if (message != null) {
      error = "contains cycle " + message;
    }

    if (error == null) {
      message = checkMaxDepth(data);
      if (message != null) {
        error = String.format("max depth (%d) is exceeded - %s", maxDepth(data.length), message);
      }
    }

    if (error == null) {
      message = checkWeight(data);
      if (message != null) {
        error = message;
      }
    }
    
    System.out.printf("%s: %s%n", error == null ? "good" : String.format("bad - %s", error), Arrays.toString(data));
  }
  
  private static String checkCycle(int[] data) {
    Set<Integer> cache = new HashSet<Integer>();
    for (int i = 0; i < data.length; i++) {
      cache.clear();
      int j = i;
      cache.add(j);
      while (data[j] != j) {
        if (!cache.add(data[j])) {
          StringBuilder buffer = new StringBuilder();
          int stop = j;
          j = i;
          buffer.append(j).append("-");
          while (j != stop) {
            j = data[j];
            buffer.append(j).append("-");
          }
          buffer.setLength(buffer.length() - 1);
          return buffer.toString();
        }
        j = data[j];
      }
    }
    return null;
  }

  private static int maxDepth(int size) {
    double d = Math.log(size) / Math.log(2);
    return (int)Math.ceil(d);
  }
  
  private static String checkMaxDepth(int[] data) {
    int maxDepth = maxDepth(data.length);
    for (int i = 0; i < data.length; i++) {
      int depth = 1;
      int j = i;
      while (data[j] != j) {
        j = data[j];
        depth++;
        if (depth > maxDepth) {
          StringBuilder buffer = new StringBuilder();
          buffer.append(i).append("-");
          j = i;
          while (data[j] != j) {
            j = data[j];
            buffer.append(j).append("-");
          } 
          buffer.setLength(buffer.length() - 1);
          return buffer.toString();
        }
      }
    }
    return null;
  }

  private static String checkWeight(int[] data) {
    for (int i = 0; i < data.length; i++) {
      if (i == data[i]) {
        continue;
      }
      int childSize = treeSize(data, i);
      int parentSize = treeSize(data, data[i]);
      if (parentSize >= childSize * 2) {
        continue;
      }
      return String.format("tree rooted at %d has less size (%d) than 2 * child tree size (child tree is rooted at %d and has size %d)",
                           data[i], parentSize, i, childSize);
    }
    return null;
  }

  private static int treeSize(int[] data, int i) {
    int result = 1;
    Stack<Integer> toProcess = new Stack<Integer>();
    toProcess.push(i);
    while (!toProcess.isEmpty()) {
      Integer root = toProcess.pop();
      for (int j = 0; j < data.length; j++) {
        if (j == root) {
          continue;
        }
        if (data[j] == root) {
          result++;
          toProcess.push(j);
        }
      }
    }
    return result;
  }
}
