import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Denis Zhdanov
 * @since 8/23/12 4:34 PM
 */
@SuppressWarnings("unchecked")
public class Deque<Item> implements Iterable<Item> {
  
  private static final int INITIAL_CAPACITY = 4;

  private Item[] data = (Item[])new Object[INITIAL_CAPACITY];
  /** Points to the first element. */
  private int head = -1;
  /** Points to the last element. */
  private int tail = -1;
  
  private int version;

  public boolean isEmpty() {
    return head < 0;
  }

  public int size() {
    if (isEmpty()) {
      return 0;
    }
    else if (head <= tail) {
      return tail - head + 1;
    }
    else {
      return (data.length - head) + (tail + 1);
    }
  }
  
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    
    version++;
    if (isEmpty()) {
      head = tail = 0;
      data[head] = item;
      return;
    }
    
    if (head > 0 && (tail >= head || head - tail > 1)) {
      data[--head] = item;
      return;
    }
    else if (head == 0 && tail < data.length - 1) {
      data[head = data.length - 1] = item;
      return;
    }
    resize(data.length * 2);
    addFirst(item);
  }
  
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    
    version++;
    if ((tail >= head && tail < data.length - 1) || head > tail + 1) {
      data[++tail] = item;
      if (head < 0) {
        head = 0;
      }
      return;
    }
    else if (head > 0 && tail >= head) {
      data[tail = 0] = item;
      return;
    }
    resize(data.length * 2);
    addLast(item);
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    final Item result = data[head];
    data[head] = null;
    if (head == tail) {
      // The deque is empty now.
      head = tail = -1;
    }
    else if (head == data.length - 1) {
      head = 0;
    }
    else {
      head++;
    }
    shrinkIfNecessary();
    return result;
  }
  
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    final Item result = data[tail];
    data[tail] = null;
    if (head == tail) {
      // The deque is empty now.
      head = tail = -1;
    }
    else if (tail > 0) {
      tail--;
    }
    else {
      tail = data.length - 1;
    }
    shrinkIfNecessary();
    return result;
  }

  private void shrinkIfNecessary() {
    if (data.length > INITIAL_CAPACITY && size() <= data.length / 4) {
      resize(data.length / 2);
    }
  }

  private void resize(int newSize) {
    Item[] newData = (Item[])new Object[newSize];
    int newStartOffset = (newSize - size()) / 2; 
    if (tail < head) {
      System.arraycopy(data, head, newData, newStartOffset, data.length - head);
      System.arraycopy(data, 0, newData, newStartOffset + data.length - head, tail + 1);
    }
    else {
      System.arraycopy(data, head, newData, newStartOffset, tail - head + 1);
    }
    tail = newStartOffset + size() - 1;
    head = newStartOffset;
    data = newData;
  }
  
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
  
  private class DequeIterator implements Iterator<Item> {

    private final int itVersion = version;
    private       int itHead    = head;

    @Override
    public boolean hasNext() {
      return itHead >= 0;
    }

    @Override
    public Item next() {
      if (itVersion != version) {
        throw new ConcurrentModificationException();
      }

      if (itHead < 0) {
        throw new NoSuchElementException();
      }

      final Item result = data[itHead];
      if (itHead < tail) {
        itHead++;
      }
      else if (itHead == tail) {
        itHead = -1; 
      }
      else if (itHead < data.length - 1) {
        itHead++;
      }
      else {
        itHead = 0;
      }
      return result;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException(); 
    }
  }
}