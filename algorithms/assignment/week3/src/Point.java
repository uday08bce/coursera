/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

  // compare points by slope
  public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
    @Override
    public int compare(Point p1, Point p2) {
      return doCompare(p1, p2);
    }
  };

  private final int x;                              // x coordinate
  private final int y;                              // y coordinate

  // create the point (x, y)
  public Point(int x, int y) {
        /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  private int doCompare(Point p1, Point p2) {
    if (p1.x == p2.x && p1.y == p2.y) {
      return 0;
    }

    double s1 = this.slopeTo(p1);
    double s2 = this.slopeTo(p2);
    if (Double.isInfinite(s1) && Double.isInfinite(s2)) {
      if (s1 < 0 ^ s2 < 0) {
        if (s1 < 0) {
          return -1;
        }
        else {
          return 1;
        }
      }
      else {
        return 0;
      }
    }
    if (Double.isInfinite(s1)) {
      if (s1 > 0) {
        return 1;
      }
      else {
        return -1;
      }
    }
    else if (Double.isInfinite(s2)) {
      if (s2 > 0) {
        return -1;
      }
      else {
        return 1;
      }
    }
    double diff = s1 - s2;
    if (diff == 0) {
      return 0;
    }
    else if (diff < 0) {
      return -1;
    }
    else {
      return 1;
    }
  }

  // plot this point to standard drawing
  public void draw() {
        /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
      if (x == that.x && y == that.y) {
        return Double.NEGATIVE_INFINITY;
      }
      else if (x == that.x) {
        return Double.POSITIVE_INFINITY;
      }
      else if (y == that.y) {
        return 0;
      }
      return (that.y - y) / (double) (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      int diff = y - that.y;
      if (diff == 0) {
        diff = x - that.x;
      }
      return diff;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
