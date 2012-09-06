import java.util.Comparator;

/**
 * @author Denis Zhdanov
 * @since 9/2/12 11:15 AM
 */
public class Brute {
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
    for (Point point : points) {
      point.draw();
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Comparator<Point> c = points[i].SLOPE_ORDER;
            int c1 = c.compare(points[j], points[k]);
            if (c1 != 0) {
              continue;
            }
            if (c1 == c.compare(points[k], points[l])) {
              StdOut.printf("%s -> %s -> %s -> %s%n", points[i], points[j],
                            points[k], points[l]);
              points[i].drawTo(points[j]);
              points[j].drawTo(points[k]);
              points[k].drawTo(points[l]);
            }
          }
        }
      }      
    }
  }
}
