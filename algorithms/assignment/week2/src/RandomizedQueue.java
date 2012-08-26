import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Denis Zhdanov
 * @since 8/26/12 3:24 PM
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  private static final int INITIAL_CAPACITY = 4;

  private Item[] data = (Item[]) new Object[INITIAL_CAPACITY];
  private int version;
  private int last        = -1;
  private int sampleIndex = -1;

  public boolean isEmpty() {
    return last < 0;
  }

  public int size() {
    if (last < 0) {
      return 0;
    }
    else {
      return last + 1;
    }
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (last >= data.length - 1) {
      resize(data.length * 2);
    }
    if (last < 0) {
      data[++last] = item;
    }
    else {
      int swapIndex = StdRandom.uniform(last + 2);
      data[++last] = data[swapIndex];
      data[swapIndex] = item;
    }
    sampleIndex = -1;
    version++;
  }
  
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item result = data[last];
    data[last--] = null;
    version++;
    sampleIndex = -1;
    if (data.length > INITIAL_CAPACITY && size() <= data.length / 4) {
      resize(data.length / 2);
    }
    return result;
  }

  private void resize(int newSize) {
    data = Arrays.copyOf(data, newSize);
  }
  
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (sampleIndex < 0) {
      sampleIndex = StdRandom.uniform(last + 1);
    }
    Item result = data[sampleIndex];
    if (++sampleIndex > last) {
      sampleIndex = 0;
    }
    return result;
  }

  public Iterator<Item> iterator() {
    return new MyIterator();
  }
  
  private class MyIterator implements Iterator<Item> {
    
    private int itVersion = version;
    private int visited;
    private int itSampleIndex;

    private MyIterator() {
      if (last >= 0) {
        itSampleIndex = StdRandom.uniform(last + 1);
      }
    }

    @Override
    public boolean hasNext() {
      return visited <= last;
    }

    @Override
    public Item next() {
      if (itVersion != version) {
        throw new ConcurrentModificationException();
      }
      if (visited > last) {
        throw new NoSuchElementException();
      }
      visited++;
      Item result = data[itSampleIndex];
      if (++itSampleIndex > last) {
        itSampleIndex = 0;
      }
      return result;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}