package coursera.algorithms.week4;

/**
 * @author Denis Zhdanov
 * @since 9/9/12 3:31 PM
 */
public class Week4 {
  public static void main(String[] args) {
    String[] i = "E D M F W I O Y H J Z K".split(" ");
    BTree<String> tree = new BTree<String>();
    for (String s : i) {
      tree.put(s);
    }
    
    //tree.get("E", true);

    tree.delete("O");
    tree.delete("J");
    tree.delete("M");

    System.out.println(tree.toString());
  }
}
