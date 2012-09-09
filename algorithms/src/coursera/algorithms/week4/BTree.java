package coursera.algorithms.week4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Zhdanov
 * @since 9/9/12 3:20 PM
 */
public class BTree<K extends Comparable<K>> {
  
  private class Node {
    K key;
    Node left;
    Node right;

    Node(K key) {
      this.key = key;
    }

    @Override
    public String toString() {
      return key.toString();
    }
  }
  
  private Node root;

  public K get(K key, boolean trace) {
    Node node = root;
    while (node != null) {
      int cmp = key.compareTo(node.key);
      if (trace) {
        System.out.print(node + " ");
      }
      if (cmp == 0) {
        return node.key;
      }
      else if (cmp < 0) {
        node = node.left;
      }
      else {
        node = node.right;
      }
    }
    return null;
  }
  
  public void put(K key) {
    if (root == null) {
      root = new Node(key);
      return;
    }
    put(key, root);
  }

  private Node put(K key, Node node) {
    if (node == null) {
      return new Node(key);
    }
    if (less(key, node)) {
      node.left = put(key, node.left);
    }
    else {
      node.right = put(key, node.right);
    }
    return node;
  }

  public void delete(K key) {
    delete(root, key);
  }

  private Node delete(Node x, K key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) x.left = delete(x.left, key);
    else if (cmp > 0) x.right = delete(x.right, key);
    else {
      if (x.right == null) return x.left;
      if (x.left == null) return x.right;
      Node t = x;
      x = min(t.right);
      x.right = delete(t.right, x.key);
      x.left = t.left;
    }
    return x;
  }

  private Node min(Node node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  private boolean less(K key, Node node) {
    return key.compareTo(node.key) < 0;
  }

  @Override
  public String toString() {
    if (root == null) {
      return "";
    }
    StringBuilder buffer = new StringBuilder();
    List<Node> nodes = new ArrayList<Node>();
    List<Node> children = new ArrayList<Node>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
      children.clear();
      for (Node node : nodes) {
        buffer.append(node).append(" ");
        if (node.left != null) {
          children.add(node.left);
        }
        if (node.right != null) {
          children.add(node.right);
        }
      }
      nodes.clear();
      nodes.addAll(children);
    }
    if (buffer.length() > 0) {
      buffer.setLength(buffer.length() - 1);
    }
    return buffer.toString();
  }
}
