package coursera.algorithms.week2;

import java.util.Arrays;

import static coursera.algorithms.Util.less;
import static coursera.algorithms.Util.swap;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 4:18 PM
 */
public class InsertionSorter {

  public static void main(String[] args) {
    String s = "D G I L M W A S O Q";
    String[] data = s.split(" ");
    sort(data, 6);
    System.out.println(Arrays.toString(data));
  }

  private static void sort(String[] data, int maxExchangesNumber) {
    for (int i = 1, exchanges = 0; i < data.length; i++) {
      for (int j = i; j > 0; j--) {
        if (less(data[j], data[j - 1])) {
          swap(data, j, j -1);
          if (++exchanges >= maxExchangesNumber) {
            return;
          }
          continue;
        }
        break;
      }
    }
  }
}
