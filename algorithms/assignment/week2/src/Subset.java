/**
 * @author Denis Zhdanov
 * @since 8/26/12 4:23 PM
 */
public class Subset {
  public static void main(String[] args) {
    if (args.length <= 0) {
      throw new IllegalArgumentException();
    }
    int toPrint = Integer.parseInt(args[0]);
    RandomizedQueue<String> queue = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      queue.enqueue(StdIn.readString());
    }

    while (toPrint-- > 0) {
      StdOut.println(queue.dequeue());
    }
  }
}
