import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Denis Zhdanov
 * @since 8/26/12 3:26 PM
 */
public class RandomizedQueueTest {
  
  private RandomizedQueue<Integer> queue;

  @Before
  public void setUp() {
    queue = new RandomizedQueue<Integer>();
  }
  
  @Test(expected = NullPointerException.class)
  public void addNull() {
    queue.enqueue(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void dequeueFromEmpty() {
    queue.dequeue();
  }

  @Test(expected = NoSuchElementException.class)
  public void sampleFromEmpty() {
    queue.sample();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void removeOnIterator() {
    queue.enqueue(1);
    queue.iterator().remove();
  }

  @Test
  public void simpleOperations() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    
    queue.enqueue(1);
    assertEquals(Integer.valueOf(1), queue.sample());
    assertFalse(queue.isEmpty());
    assertEquals(1, queue.size());
    
    assertEquals(Integer.valueOf(1), queue.dequeue());
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    queue.enqueue(2);
    assertFalse(queue.isEmpty());
    assertEquals(1, queue.size());

    assertEquals(Integer.valueOf(2), queue.dequeue());
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void enlargeAndShrink() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    
    int itemsNumber = 100;
    
    Set<Integer> expected = new HashSet<Integer>();
    for (int i = 1; i <= itemsNumber; i++) {
      queue.enqueue(i);
      assertFalse(queue.isEmpty());
      assertEquals(i, queue.size());
      expected.add(i);
    }

    while (itemsNumber-- > 0) {
      expected.remove(queue.dequeue());
    }
    
    assertTrue(expected.isEmpty());
  }

  @Test
  public void iterator() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    assertFalse(queue.iterator().hasNext());

    int itemsNumber = 4;

    Set<Integer> expected = new HashSet<Integer>();
    for (int i = 1; i <= itemsNumber; i++) {
      queue.enqueue(i);
      assertFalse(queue.isEmpty());
      assertEquals(i, queue.size());
      expected.add(i);
    }

    Iterator<Integer> iterator = queue.iterator();
    while (itemsNumber-- > 0) {
      assertTrue(String.valueOf(itemsNumber + 1), iterator.hasNext());
      expected.remove(iterator.next());
    }
    
    assertTrue(expected.isEmpty());
  }

  @Test(expected = ConcurrentModificationException.class)
  public void concurrentModification() {
    queue.enqueue(1);
    Iterator<Integer> iterator = queue.iterator();
    queue.enqueue(2);
    assertTrue(iterator.hasNext());
    iterator.next();
  }

  @Test
  public void multipleIterator() {
    queue.enqueue(0);
    queue.enqueue(1);
    int[] data = new int[2];
    
    Iterator<Integer> iterator1 = queue.iterator();
    Iterator<Integer> iterator2 = queue.iterator();
    assertTrue(iterator1.hasNext());
    assertTrue(iterator2.hasNext());
    
    data[iterator1.next()]++;
    assertTrue(iterator1.hasNext());
    assertTrue(iterator2.hasNext());
    
    data[iterator1.next()]++;
    assertFalse(iterator1.hasNext());
    assertTrue(iterator2.hasNext());

    data[iterator2.next()]++;
    assertFalse(iterator1.hasNext());
    assertTrue(iterator2.hasNext());

    data[iterator2.next()]++;
    assertFalse(iterator1.hasNext());
    assertFalse(iterator2.hasNext());
    
    assertEquals(2, data[0]);
    assertEquals(2, data[1]);
  }

  @Test
  public void dequeueFrequency() {
    int elementsNumber = 10;
    int[] frequency = new int[10];
    
    for (int i = 0; i < 100000; i++) {
      queue = new RandomizedQueue<Integer>();
      for (int j = 0; j < elementsNumber; j++) {
        queue.enqueue(j);
      }
      while (true) {
        Integer popped = queue.dequeue();
        if (popped == 0) {
          break;
        }
        else {
          frequency[popped]++;
        }
      }
    }
    
    int min = Integer.MAX_VALUE;
    int max = -1;
    for (int i = 1; i < frequency.length; i++) {
      min = Math.min(min, frequency[i]);
      max = Math.max(max, frequency[i]);
    }
    
    assertTrue(max - min < 1000);
  }
}
