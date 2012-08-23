package coursera.algorithms.week2;

import java.util.Arrays;

import static coursera.algorithms.Util.less;
import static coursera.algorithms.Util.swap;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 4:10 PM
 */
public class SelectionSorter {

  public static void main(String[] args) {
    String s = "Q M K G T B H C X U";
    String[] data = s.split(" ");
    sort(data, 4);
    System.out.println(Arrays.toString(data));
  }

  private static void sort(String[] data, int iterations) {
    for (int i = 0, limit = Math.min(data.length, iterations); i < limit; i++) {
      int min = i;
      for (int j = i + 1; j < data.length; j++) {
        if (less(data[j], data[min])) {
          min = j;
        }
      }
      swap(data, min, i);
    }
  }
}
