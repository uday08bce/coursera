package coursera.algorithms.week1.unionfind;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static coursera.algorithms.Util.lg;
import static coursera.algorithms.Util.round;

/**
 * @author Denis Zhdanov
 * @since 8/18/12 3:49 PM
 */
public class ComplexityPowCalculator {
  
  private static final int LIMIT_IN_SECONDS = 3;
  private static final int SEED = 327211;
  
  public static void main(String[] args) throws Exception {
    long durationLimit = TimeUnit.SECONDS.toNanos(LIMIT_IN_SECONDS);
    Class<?> clazz = Class.forName("Timing");
    Method method = clazz.getMethod("trial", int.class, long.class);
    double[] point1 = new double[2];
    double[] point2 = new double[2];
    long start;
    boolean oneIsNewer = true;
    for (int i = 1; i > 0 ; i *= 2) {
      start = System.nanoTime();
      method.invoke(null, i, SEED);
      long duration = System.nanoTime() - start;
      if (oneIsNewer) {
        point2[0] = i;
        point2[1] = duration;
      } else {
        point1[0] = i;
        point1[1] = duration;
      }
      oneIsNewer = !oneIsNewer;
      if (duration >= durationLimit) {
        break;
      }
    }
    
    point1[0] = lg(point1[0]);
    point1[1] = lg(point1[1]);
    point2[0] = lg(point2[0]);
    point2[1] = lg(point2[1]);

    System.out.println(round((point1[1] - point2[1]) / (point1[0] - point2[0]), 10));
  }
}
