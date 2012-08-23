package coursera.algorithms;

/**
 * @author Denis Zhdanov
 * @since 8/18/12 4:06 PM
 */
public class Util {

  private Util() {
  }

  public static double lg(double arg) {
    return Math.log(arg) / Math.log(2);
  }

  public static double round(double num, int significantDigitsNumber) {
    if (num == 0) {
      return 0;
    }

    final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
    final int power = significantDigitsNumber - (int)d;

    final double magnitude = Math.pow(10, power);
    final long shifted = Math.round(num * magnitude);
    return shifted / magnitude;
  }

  public static <T extends Comparable<T>> boolean less(T first, T second) {
    return first.compareTo(second) < 0;
  }

  public static <T> void swap(T[] data, int i, int j) {
    T tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }
}
