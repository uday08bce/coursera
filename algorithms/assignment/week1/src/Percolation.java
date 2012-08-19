/**
 * Not thread-safe.
 * 
 * @author Denis Zhdanov
 * @since 8/18/12 5:29 PM
 */
public class Percolation {

  private static final String OPEN_OPERATION_NAME    = "open";
  private static final String IS_OPEN_OPERATION_NAME = "isOpen";
  private static final String IS_FULL_OPERATION_NAME = "isFull";

  private static final String ROW_ROLE_NAME    = "row";
  private static final String COLUMN_ROLE_NAME = "column";
  
  private final WeightedQuickUnionUF data;
  private final boolean[]            openStates;
  private final int                  bottomSiteIndex;
  private final int                  gridSideLength;
  
  private boolean systemPercolates;

  public Percolation(int N) {
    if (N <= 0) {
      throw new IllegalArgumentException(String.format(
        "Can't create a Percolation object. Reason: given grid side length is "
        + "non-positive (%d)", N
      ));
    }
    
    bottomSiteIndex = N * N + 1;
    if (bottomSiteIndex <= N) {
      throw new IllegalArgumentException(String.format(
        "Can't create a Percolation object. Reason: given grid side length is too "
        + "big for the standard 'weighted quick union find' data type - it limits "
        + "data array length to the max signed four-bytes value (%d) but we "
        + "need %d (pow(%d <given grid side length pow>, 2) + 2 <virtual top and "
        + "bottom sites>)",
        Integer.MAX_VALUE, ((long) N * N) + 2, N
      ));
    }
    
    data = new WeightedQuickUnionUF(bottomSiteIndex + 1);
    openStates = new boolean[bottomSiteIndex + 1];
    openStates[0] = true;
    openStates[bottomSiteIndex] = true;
    gridSideLength = N;
  }
  
  /**
   * Opens a site located at the (i; j) grid cell.
   * 
   * @param i  row, starts from 1
   * @param j  column, starts from 1
   */
  public void open(int i, int j) {
    checkCellArgument(i, OPEN_OPERATION_NAME, ROW_ROLE_NAME);
    checkCellArgument(j, OPEN_OPERATION_NAME, COLUMN_ROLE_NAME);

    int dataIndex = toDataIndex(i, j);
    if (openStates[dataIndex]) {
      return;
    }
    
    openStates[dataIndex] = true;
    
    // Connect to the top site.
    if (i == 1) {
      data.union(0, dataIndex);
    } else {
      unionIfPossible(toDataIndex(i - 1, j), dataIndex);
    }
    
    // Connect to the right site.
    if (j < gridSideLength) {
      unionIfPossible(dataIndex + 1, dataIndex);
    }

    // Connect to the bottom site.
    if (i == gridSideLength) {
      data.union(bottomSiteIndex, dataIndex);
    }
    else {
      unionIfPossible(toDataIndex(i + 1, j), dataIndex);
    }
    
    // Connect to the right site.
    if (j > 0) {
      unionIfPossible(dataIndex - 1, dataIndex);
    }
  }

  /**
   * Connects two sites with the given indexes if a neighbour site is open.
   * 
   * @param neighbour  neighbour site index
   * @param target     target side index
   */
  private void unionIfPossible(int neighbour, int target) {
    if (openStates[neighbour]) {
      data.union(neighbour, target);
    }
  }
  
  /**
   * Allows to answer if a site located at the (i; j) grid cell is open.
   *
   * @param i  row, starts from 1
   * @param j  column, starts from 1
   */
  public boolean isOpen(int i, int j) {
    checkCellArgument(i, IS_OPEN_OPERATION_NAME, ROW_ROLE_NAME);
    checkCellArgument(j, IS_OPEN_OPERATION_NAME, COLUMN_ROLE_NAME);
    return openStates[toDataIndex(i, j)];
  }

  /**
   * Allows to answer if a site located at the (i; j) grid cell is full, i.e.
   * connects to the top row.
   *
   * @param i  row, starts from 1
   * @param j  column, starts from 1
   */
  public boolean isFull(int i, int j) {
    checkCellArgument(i, IS_FULL_OPERATION_NAME, ROW_ROLE_NAME);
    checkCellArgument(j, IS_FULL_OPERATION_NAME, COLUMN_ROLE_NAME);
    return data.connected(0, toDataIndex(i, j));
  }
  
  private void checkCellArgument(int arg, String operationName, String argRole) {
    if (arg < 0) {
      throw new IllegalArgumentException(String.format(
        "Can't perform Percolation.%s() operation. Reason: given %s is too low "
        + "(%d). Minimum allowed value is 1", operationName, argRole, arg
      ));
    }
    if (arg > gridSideLength) {
      throw new IllegalArgumentException(String.format(
        "Can't perform Percolation.%s() operation. Reason: given %s is too big "
        + "(%d). Maximum allowed value is %d",
        operationName, argRole, arg, gridSideLength
      ));
    }
  }

  private int toDataIndex(int row, int column) {
    return (row - 1)/*because rows are zero-based*/ * gridSideLength
           + column; // don't decrement the column because we have a virtual
                     // 'top' site which adds one
  }
  
  public boolean percolates() {
    if (!systemPercolates) {
      systemPercolates = data.connected(0, bottomSiteIndex);
    }
    return systemPercolates;
  }
}
