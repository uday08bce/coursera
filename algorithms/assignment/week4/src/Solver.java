import java.util.Comparator;

/**
 * @author Denis Zhdanov
 * @since 9/9/12 4:58 PM
 */
public class Solver {

  private static final Comparator<Board> COMPARATOR = new Comparator<Board>() {
    @Override
    public int compare(Board o1, Board o2) {
      return o1.hamming() + o1.manhattan() - o2.hamming() - o2.manhattan();
    }
  };

  private Queue<Board> baseTrace = new Queue<Board>();

  public Solver(Board initial) {
    MinPQ<Board> basePq = new MinPQ<Board>(COMPARATOR);
    basePq.insert(initial);
    MinPQ<Board> twinPq = new MinPQ<Board>(COMPARATOR);
    twinPq.insert(initial.twin());
    Queue<Board> twinTrace = new Queue<Board>();
    
    while (true) {
      Board base = basePq.delMin();
      if (base.isGoal()) {
        baseTrace.enqueue(base);
        break;
      }

      Board twin = twinPq.delMin();
      if (twin.isGoal()) {
        baseTrace = null;
        break;
      }
      
      addNeighbors(base, basePq, baseTrace);
      addNeighbors(twin, twinPq, twinTrace);
      baseTrace.enqueue(base);
      twinTrace.enqueue(twin);
    }
  }

  private void addNeighbors(Board board, MinPQ<Board> queue, Queue<Board> trace) {
    Board previous = null;
    if (!trace.isEmpty()) {
      previous = trace.peek();
    }
    for (Board neighbor : board.neighbors()) {
      if (!neighbor.equals(previous)) {
        queue.insert(neighbor);
      }
    }
  }
  
  public boolean isSolvable() {
    return baseTrace != null;
  }
  
  public int moves() {
    if (baseTrace == null) {
      return -1;
    }
    else {
      return baseTrace.size() - 1;
    }
  }
  
  public Iterable<Board> solution() {
    return baseTrace;
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
}
