/**
 * @author Denis Zhdanov
 * @since 8/18/12 6:26 PM
 */
public class PercolationStats {

  private final double myMean;
  private final double myStdDev;

  /**
   * @param N  grid side length
   * @param T  number of experiments to perform
   */
  public PercolationStats(int N, int T) {
    if (T <= 0) {
      throw new IllegalArgumentException(String.format(
        "Can't perform percolation data calculation. Reason: given experiments "
        + "number is not positive (%d)", T
      ));
    }
    
    // We don't need to check N here because Percolation constructor already does
    // that. The check is here only because we're explicitly forced to put it
    // here by the specification.
    if (N <= 0) {
      throw new IllegalArgumentException(String.format(
        "Can't perform percolation data calculation. Reason: given grid side "
        + "length is not positive (%d)", N
      ));
    }
    
    double[] fractions = new double[T];
    double sum = 0;
    int openSitesNumber;
    
    for (int i = 0; i < fractions.length; i++) {
      openSitesNumber = 0;
      for (Percolation percolation = new Percolation(N); !percolation.percolates();)
      {
        int row;
        int column;
        
        // Find non-open site. It's possible to choose a starting site at random
        // and increment its index one-by-one until a closed site is found.
        // However, we're forced to repeat the random choose:
        // http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
        // 
        // - How do I generate a site uniformly at random among all blocked sites 
        // for use in PercolationStats?
        // - Pick a site at random (by using StdRandom to generate two integers 
        // between 1 and N) and use this site if it is blocked; if not, repeat.
        do {
          row = StdRandom.uniform(N) + 1;
          column = StdRandom.uniform(N) + 1;
        }
        while (percolation.isOpen(row, column));
        
        // Open a site.
        percolation.open(row, column);
        openSitesNumber++;
      }
      fractions[i] = ((double) openSitesNumber) / N / N;
      sum += fractions[i];
    }
    
    myMean = sum / T;

    if (T == 1) {
      // As pointed at the
      // http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
      myStdDev = Double.NaN;
    }
    else {
      sum = 0;
      for (double fraction : fractions) {
        double tmp = fraction - myMean;
        sum += tmp * tmp;
      }
      myStdDev = Math.sqrt(sum / (T - 1));
    }
  }
  
  /**
   * @return  sample mean of percolation threshold
   */
  public double mean() {
    return myMean;
  }

  /**
   * @return  sample standard deviation of percolation threshold
   */
  public double stddev() {
    return myStdDev;
  }
  
  public static void main(String[] args) {
    if (args.length < 2) {
      StdOut.printf(
        "Can't compute percolation stats. Reason: given arguments number (%d) is "
        + "less than expected (2)%n", args.length
      );
      printUsage();
      return;
    }
    
    
    final int N;
    final int T;
    try {
      N = Integer.parseInt(args[0]);
    }
    catch (NumberFormatException e) {
      StdOut.printf(
        "Can't compute percolation stats. Reason: given grid side length argument "
        + "(%s) is not a number%n", args[0]
      );
      return;
    }
    try {
      T = Integer.parseInt(args[1]);
    }
    catch (NumberFormatException e) {
      StdOut.printf(
        "Can't compute percolation stats. Reason: given experiments number argument "
        + "(%s) is not a number%n", args[1]
      );
      return;
    }
    PercolationStats stats = new PercolationStats(N, T);

    double d = 1.96 * stats.stddev() / Math.sqrt(T);
    double confidenceStart = stats.mean() - d;
    double confidenceEnd = stats.mean() + d;
    
    StdOut.println("mean                    = " + stats.mean());
    StdOut.println("stddev                  = " + stats.stddev());
    StdOut.println("95% confidence interval = " + confidenceStart 
                   + ", " + confidenceEnd);
  }

  private static void printUsage() {
    StdOut.printf("Usage: %s <grid side length> <experiments number>%n",
                  Percolation.class.getSimpleName());
  }
}
