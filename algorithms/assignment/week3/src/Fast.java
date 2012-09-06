import java.util.Arrays;
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
    Point[] points = new Point[pairsNumber];
    for (int i = 0; i < points.length; i++) {
      points[i] = new Point(in.readInt(), in.readInt());
    }
    Quick3way.sort(points);

    PointWrapper[] workWrappers = new PointWrapper[pairsNumber];
    for (int i = 0; i < points.length; i++) {
      Point point = points[i];
      point.draw();
      workWrappers[i] = new PointWrapper(i, point);
    }

    if (pairsNumber < 4) {
      return;
    }
    
    SlopeComparator comparator = new SlopeComparator();
    
    for (int i = 0; i < points.length; i++) {
      for (PointWrapper wrapper : workWrappers) {
        wrapper.setBase(points[i]);
      }
      comparator.c = points[i].SLOPE_ORDER;
      Arrays.sort(workWrappers, comparator);
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
                onLine(workWrappers, points[i], startIndex, j + 1);
              }
            }
          }
          continue;
        }

        if (good) {
          if (j - startIndex > 2) {
            onLine(workWrappers, points[i], startIndex, j);
          }
        }

        good = current.i >= i;
        startIndex = j;
        slope = current.slope;
      }
    }
  }

  private static void onLine(PointWrapper[] data, Point base, int start, int end) {
    Arrays.sort(data, start, end);
    StdOut.printf("%s -> %s", base, data[start]);
    Point min = base;
    Point max = min;
    for (int k = start + 1; k < end; k++) {
      StdOut.printf(" -> %s", data[k]);
      if (min.compareTo(data[k].point) > 0) {
        min = data[k].point;
      }
      if (max.compareTo(data[k].point) < 0) {
        max = data[k].point;
      }
    }
    StdOut.println();
    min.drawTo(max);
  }

  private static class PointWrapper implements Comparable<PointWrapper> {

    private final int               i;
    private final Point             point;
    private       double            slope;

    PointWrapper(int i, Point p) {
      this.i = i;
      point = p;
    }

    public void setBase(Point base) {
      slope = base.slopeTo(point);
    }

    @Override
    public int compareTo(PointWrapper w) {
      return point.compareTo(w.point);
    }

    @Override
    public String toString() {
      return point.toString();
    }
  }
  
  private static class SlopeComparator implements Comparator<PointWrapper> {
    
    private Comparator<Point> c;

    @Override
    public int compare(PointWrapper o1, PointWrapper o2) {
      return c.compare(o1.point, o2.point);
    }
  }
}
