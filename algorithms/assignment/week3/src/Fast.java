import java.util.Comparator;

/**
 * @author Denis Zhdanov
 * @since 9/5/12 8:12 PM
 */
public class Fast {
  public static void main(String[] args) {

    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    
    In in = new In(args[0]);
    int pairsNumber = in.readInt();
    PointWrapper[] wrappers = new PointWrapper[pairsNumber];
    PointWrapper[] workWrappers = new PointWrapper[pairsNumber];
    for (int i = 0; i < wrappers.length; i++) {
      PointWrapper wrapper = new PointWrapper(i, in.readInt(), in.readInt());
      wrappers[i] = wrapper;
      workWrappers[i] = wrapper;
      wrapper.point.draw();
    }

    for (int i = 0; i < wrappers.length; i++) {
      for (PointWrapper wrapper : workWrappers) {
        wrapper.setBase(wrappers[i]);
      }
      Quick3way.sort(workWrappers);
      int startIndex = 1;
      double slope = workWrappers[1].slope;
      boolean good = workWrappers[1].i >= i;
      for (int j = 2; j < workWrappers.length; j++) {
        PointWrapper current = workWrappers[j];
        if (current.slope == slope
            || (Double.isInfinite(slope) && Double.isInfinite(current.slope)))
        {
          if (current.i < i) {
            good = false;
          }
          else if (j == workWrappers.length - 1) {
            if (good) {
              if (j - startIndex > 1) {
                StdOut.printf("%s -> %s", wrappers[i], workWrappers[startIndex]);
                wrappers[i].point.drawTo(workWrappers[startIndex].point);
                for (int k = startIndex + 1; k <= j; k++) {
                  StdOut.printf(" -> %s", workWrappers[k]);
                  workWrappers[k - 1].point.drawTo(workWrappers[k].point);
                }
                StdOut.println();
              }
            }
          }
          continue;
        }

        if (good) {
          if (j - startIndex > 2) {
            StdOut.printf("%s -> %s", wrappers[i], workWrappers[startIndex]);
            wrappers[i].point.drawTo(workWrappers[startIndex].point);
            for (int k = startIndex + 1; k < j; k++) {
              StdOut.printf(" -> %s", workWrappers[k]);
              workWrappers[k - 1].point.drawTo(workWrappers[k].point);
            }
            StdOut.println();
          }
        }

        good = current.i >= i;
        startIndex = j;
        slope = current.slope;
      }
    }
  }

  private static class PointWrapper implements Comparable<PointWrapper> {
    
    private final int i;
    private final Point point;
    private double slope;
    private Comparator<Point> comparator;

    PointWrapper(int i, int x, int y) {
      this.i = i;
      point = new Point(x, y);
    }

    public void setBase(PointWrapper base) {
      slope = base.point.slopeTo(point);
      comparator = base.point.SLOPE_ORDER;
    }
    
    @Override
    public int compareTo(PointWrapper w) {
      return comparator.compare(point, w.point);
    }

    @Override
    public String toString() {
      return point.toString();
    }
  }
}
