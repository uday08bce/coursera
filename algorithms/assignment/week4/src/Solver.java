import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Denis Zhdanov
 * @since 9/9/12 4:58 PM
 */
public class Solver {

  private static final Comparator<SearchNode> COMPARATOR =
    new Comparator<SearchNode>()
    {
      @Override
      public int compare(SearchNode o1, SearchNode o2) {
        return o1.moves + o1.board.manhattan() - o2.moves - o2.board.manhattan();
      }
    };

  private Queue<Board> resultTrace;

  public Solver(Board initial) {
    MinPQ<SearchNode> basePq = new MinPQ<SearchNode>(COMPARATOR);
    basePq.insert(new SearchNode(null, initial, 0));
    MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(COMPARATOR);
    twinPq.insert(new SearchNode(null, initial.twin(), 0));
    SearchNode result;
    
    while (true) {
      SearchNode base = basePq.delMin();
      //System.out.println("---------------------------------------");
      //System.out.println("Choosing the board below");
      //System.out.println(base);
      if (base.board.isGoal()) {
        result = base;
        break;
      }

      SearchNode twin = twinPq.delMin();
      if (twin.board.isGoal()) {
        result = null;
        break;
      }
      
      addNeighbors(base, basePq);
      addNeighbors(twin, twinPq);
      //printQueue(basePq);
    }
    
    if (result != null) {
      Stack<Board> tmp = new Stack<Board>();
      for (SearchNode n = result; n != null; n = n.previous) {
        tmp.push(n.board);
      }
      
      resultTrace = new Queue<Board>();
      while (!tmp.isEmpty()) {
        resultTrace.enqueue(tmp.pop());
      }
    }
  }

  private void printQueue(MinPQ<SearchNode> queue) {
    System.out.println("-------------------> Queue: <---------------------------");
    for (Iterator<SearchNode> iterator = queue.iterator(); iterator.hasNext();) {
      SearchNode next = iterator.next();
      System.out.println("manhattan = " + (next.board.manhattan() + next.moves));
      System.out.println(next);
    }
  }

  private void addNeighbors(SearchNode node, MinPQ<SearchNode> queue) {
    Board previous = null;
    if (node.previous != null) {
      previous = node.previous.board;
    }
    for (Board neighbor : node.board.neighbors()) {
      if (!neighbor.equals(previous)) {
        queue.insert(new SearchNode(node, neighbor, node.moves + 1));
      }
    }
  }
  
  public boolean isSolvable() {
    return resultTrace != null;
  }
  
  public int moves() {
    if (resultTrace == null) {
      return -1;
    }
    else {
      return resultTrace.size() - 1;
    }
  }
  
  public Iterable<Board> solution() {
    return resultTrace;
  }
  
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
  
  private static class SearchNode {

    private final SearchNode previous;
    private final Board      board;
    private final int        moves;

    SearchNode(SearchNode previous, Board board, int moves) {
      this.previous = previous;
      this.board = board;
      this.moves = moves;
    }

    @Override
    public String toString() {
      return board.toString();
    }
  }
}
