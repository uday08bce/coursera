package coursera.algorithms.week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 10:27 AM
 */
public class FeasibleStack {
  public static void main(String[] args) {
    String[] input = {
      "C B D F E G A I H J",
      "C B G I F H J E D A",
      "B D A G F E C H J I",
      "C B A D E F G H I J",
      "A G F E D C J I H B"
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
    
    Stack<String> stack = new Stack<String>();
    for (String symbol : output) {
      if (!stack.isEmpty() && stack.peek().equals(symbol)) {
        stack.pop();
        continue;
      }
      
      boolean ok = false;
      while (!input.isEmpty()) {
        String inputSymbol = input.remove(0);
        if (inputSymbol.equals(symbol)) {
          ok = true;
          break;
        }
        stack.push(inputSymbol);
      }
      if (!ok) {
        return false;
      }
    }
    return true;
  }
}
