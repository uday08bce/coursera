import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 4:36 PM
 */
public class DequeTest {
  
  private Deque<Integer> deque;

  @Before
  public void setUp() {
    deque = new Deque<Integer>();
  }

  @Test(expected = NullPointerException.class)
  public void addEmptyFirst() {
    deque.addFirst(null);
  }
  
  @Test(expected = NullPointerException.class)
  public void addEmptyLast() {
    deque.addLast(null);
  }
  
  @Test
  public void addLastRemoveFirst() {
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
    
    deque.addLast(1);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    
    deque.addLast(2);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());
    
    assertEquals(Integer.valueOf(1), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    
    deque.addLast(3);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    deque.addLast(4);
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    deque.addLast(5);
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    deque.addLast(6);
    assertFalse(deque.isEmpty());
    assertEquals(5, deque.size());
    
    assertEquals(Integer.valueOf(2), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    assertEquals(Integer.valueOf(3), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    assertEquals(Integer.valueOf(4), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(5), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(6), deque.removeFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
    
    deque.addLast(7);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    
    assertEquals(Integer.valueOf(7), deque.removeFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  public void addLastRemoveLast() {
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addLast(1);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addLast(2);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(2), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addLast(3);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    deque.addLast(4);
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    deque.addLast(5);
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    deque.addLast(6);
    assertFalse(deque.isEmpty());
    assertEquals(5, deque.size());

    assertEquals(Integer.valueOf(6), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    assertEquals(Integer.valueOf(5), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    assertEquals(Integer.valueOf(4), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(3), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(1), deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addLast(7);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(7), deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  public void addFirstRemoveFirst() {
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addFirst(1);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addFirst(2);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(2), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addFirst(3);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    deque.addFirst(4);
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    deque.addFirst(5);
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    deque.addFirst(6);
    assertFalse(deque.isEmpty());
    assertEquals(5, deque.size());

    assertEquals(Integer.valueOf(6), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    assertEquals(Integer.valueOf(5), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    assertEquals(Integer.valueOf(4), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(3), deque.removeFirst());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(1), deque.removeFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addFirst(7);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(7), deque.removeFirst());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test
  public void addFirstRemoveLast() {
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addFirst(1);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addFirst(2);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(1), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    deque.addFirst(3);
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    deque.addFirst(4);
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    deque.addFirst(5);
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    deque.addFirst(6);
    assertFalse(deque.isEmpty());
    assertEquals(5, deque.size());

    assertEquals(Integer.valueOf(2), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(4, deque.size());

    assertEquals(Integer.valueOf(3), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(3, deque.size());

    assertEquals(Integer.valueOf(4), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(2, deque.size());

    assertEquals(Integer.valueOf(5), deque.removeLast());
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());

    assertEquals(Integer.valueOf(6), deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());

    deque.addFirst(7);
    assertFalse(deque.isEmpty());
    assertEquals(1, deque.size());
    
    assertEquals(Integer.valueOf(7), deque.removeLast());
    assertTrue(deque.isEmpty());
    assertEquals(0, deque.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void nextOnEmptyIterator() {
    deque.iterator().next();
  }

  @Test(expected = NoSuchElementException.class)
  public void invalidNext() {
    deque.addLast(1);
    Iterator<Integer> iterator = deque.iterator();
    assertEquals(Integer.valueOf(1), iterator.next());
    iterator.next();
  }

  @Test(expected = ConcurrentModificationException.class)
  public void concurrentModification() {
    deque.addLast(1);
    deque.addLast(2);
    
    Iterator<Integer> iterator = deque.iterator();
    assertEquals(Integer.valueOf(1), iterator.next());
    assertTrue(iterator.hasNext());
    
    deque.addLast(3);
    iterator.next();
  }

  @Test
  public void multipleIterator() {
    deque.addLast(1);
    deque.addLast(2);
    deque.addLast(3);

    Iterator<Integer> it1 = deque.iterator();
    Iterator<Integer> it2 = deque.iterator();
    
    assertTrue(it1.hasNext());
    assertTrue(it2.hasNext());
    
    assertEquals(Integer.valueOf(1), it1.next());
    assertTrue(it1.hasNext());
    assertTrue(it2.hasNext());

    assertEquals(Integer.valueOf(2), it1.next());
    assertTrue(it1.hasNext());
    assertTrue(it2.hasNext());

    assertEquals(Integer.valueOf(1), it2.next());
    assertTrue(it1.hasNext());
    assertTrue(it2.hasNext());

    assertEquals(Integer.valueOf(3), it1.next());
    assertFalse(it1.hasNext());
    assertTrue(it2.hasNext());

    assertEquals(Integer.valueOf(2), it2.next());
    assertFalse(it1.hasNext());
    assertTrue(it2.hasNext());

    assertEquals(Integer.valueOf(3), it2.next());
    assertFalse(it1.hasNext());
    assertFalse(it2.hasNext());
  }

  @Test
  public void iterator() {
    deque.addFirst(2);
    deque.addFirst(1);
    deque.addLast(3);

    Iterator<Integer> iterator = deque.iterator();
    assertTrue(iterator.hasNext());
    
    assertEquals(Integer.valueOf(1), iterator.next());
    assertTrue(iterator.hasNext());

    assertEquals(Integer.valueOf(2), iterator.next());
    assertTrue(iterator.hasNext());

    assertEquals(Integer.valueOf(3), iterator.next());
    assertFalse(iterator.hasNext());
    assertFalse(iterator.hasNext());
  }
}
