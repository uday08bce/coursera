package coursera.algorithms.week2;

import java.util.*;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 10:54 AM
 */
public class FeasibleQueue {
  public static void main(String[] args) {
    String[] input = {
      "A B F I H D G E C J",
      "A H C B F D E I G J",
      "H F J B C I A G D E",
      "A B C D E F G H I J",
      "D C E J F B H I G A"
    };
    for (String s : input) {
      System.out.printf("%s %s%n", isValid(s) ? "possible:  " : "impossible:", s);
    }
  }

  private static boolean isValid(String s) {
    final List<String> input = new ArrayList<String>();
    for (char i = 'A'; i <= 'J'; i++) {
      input.add(String.valueOf(i));
    }
    List<String> output = new ArrayList<String>(Arrays.asList(s.split(" ")));

    LinkedList<String> queue = new LinkedList<String>();
    for (String symbol : output) {
      if (!queue.isEmpty() && queue.getLast().equals(symbol)) {
        queue.removeLast();
        continue;
      }

      if (input.isEmpty()) {
        return false;
      }

      String inputSymbol = input.remove(0);
      if (!inputSymbol.equals(symbol)) {
        return false;
      }
    }
    return true;
  }

}
