import java.util.Arrays;

/**
 * @author Denis Zhdanov
 * @since 9/9/12 4:10 PM
 */
public class Board {
  
  private final char[][] blocks;
  
  private int hammingValue = -1;
  private int manhattanValue = -1;

  public Board(int[][] blocks) {
    this(toCharArray(blocks));
  }

  private Board(char[][] blocks) {
    this.blocks = blocks;
  }

  private static char[][] toCharArray(int[][] data) {
    char[][] result = new char[data.length][];
    for (int i = 0; i < data.length; i++) {
      int[] srcRowData = data[i];
      char[] dstRowData = new char[srcRowData.length];
      for (int j = 0; j < srcRowData.length; j++) {
        dstRowData[j] = (char) srcRowData[j];
      }
      result[i] = dstRowData;
    }
    return result;
  }
  
  public int dimension() {
    return blocks.length;
  }
  
  public int hamming() {
    if (hammingValue < 0) {
      hammingValue = 0;
      int dimension = dimension();
      for (int row = 0; row < dimension; row++) {
        for (int column = 0; column < dimension; column++) {
          final char expected;
          if (row == dimension - 1 && column == dimension - 1) {
            expected = 0;
          }
          else {
            expected = (char) (row * dimension + column + 1);
          }
          if (expected != 0 && expected != blocks[row][column]) {
            hammingValue++;
          }
        }
      }
    }
    return hammingValue;
  }
  
  public int manhattan() {
    if (manhattanValue < 0) {
      manhattanValue = 0;
      int dimension = dimension();
      for (int row = 0; row < dimension; row++) {
        for (int column = 0; column < dimension; column++) {
          char actual = blocks[row][column];
          final char expected;
          if (row == dimension - 1 && column == dimension - 1) {
            expected = 0;
          }
          else {
            expected = (char) (row * dimension + column + 1);
          }
          if (actual != 0 && expected != actual) {
            manhattanValue += Math.abs(row - (actual - 1) / dimension);
            manhattanValue += Math.abs(column - (actual - 1) % dimension);
          }
        }
      }
    }
    return manhattanValue;
  }
  
  public boolean isGoal() {
    return manhattan() == 0;
  }
  public Board twin() {
    char[][] twinBlocks = copy(blocks);
    for (int i = twinBlocks.length - 1; i >= 0; i--) {
      char[] rowData = twinBlocks[i];
      for (int j = rowData.length - 1; j > 0; j--) {
        if (rowData[j] != 0 && rowData[j - 1] != 0) {
          char t = rowData[j];
          rowData[j] = rowData[j - 1];
          rowData[j - 1] = t;
          return new Board(twinBlocks);
        }
      }
    }
    return new Board(twinBlocks);
  }

  private static char[][] copy(char[][] src) {
    char[][] result = new char[src.length][];
    for (int i = 0; i < src.length; i++) {
      result[i] = Arrays.copyOf(src[i], src[i].length);
    }
    return result;
  }
  
  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }
    if (y == this) {
      return true;
    }
    if (getClass() != y.getClass()) {
      return false;
    }
    return Arrays.deepEquals(blocks, ((Board) y).blocks);
  }
  
  public Iterable<Board> neighbors() {
    Queue<Board> result = new Queue<Board>();
    int dimension = dimension();
    for (int row = 0; row < dimension; row++) {
      for (int column = 0; column < dimension; column++) {
        if (blocks[row][column] == 0) {
          
          // Down.
          if (row > 0) {
            char[][] neighborBlocks = copy(blocks);
            neighborBlocks[row][column] = blocks[row - 1][column];
            neighborBlocks[row - 1][column] = 0;
            result.enqueue(new Board(neighborBlocks));
          }
          
          // Left.
          if (column < dimension - 1) {
            char[][] neighborBlocks = copy(blocks);
            neighborBlocks[row][column] = blocks[row][column + 1];
            neighborBlocks[row][column + 1] = 0;
            result.enqueue(new Board(neighborBlocks));
          }
          
          // Up.
          if (row < dimension - 1) {
            char[][] neighborBlocks = copy(blocks);
            neighborBlocks[row][column] = blocks[row + 1][column];
            neighborBlocks[row + 1][column] = 0;
            result.enqueue(new Board(neighborBlocks));
          }

          // Right.
          if (column > 0) {
            char[][] neighborBlocks = copy(blocks);
            neighborBlocks[row][column] = blocks[row][column - 1];
            neighborBlocks[row][column - 1] = 0;
            result.enqueue(new Board(neighborBlocks));
          }
          return result;
        }
      }
    }
    return result;
  }
  
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    int dimension = dimension();
    buffer.append(dimension).append("\n");
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        buffer.append(String.format("%2d ", (int) blocks[i][j]));
      }
      buffer.append("\n");
    }
    return buffer.toString();
  }
}
