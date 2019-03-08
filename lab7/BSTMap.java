import java.util.Iterator;
import java.util.Set;

/**

 @source: Lab 7 Th 3-5 pm Soda 273 Staff solution
 Attempted to created the structure with help of TA, implementation of
 */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    private class Node {
        /* Each pair of key and value is stored in the Node. */
        private K k;
        private V v;

        /* Record the children of this Node. */
        private Node left;
        private Node right;

        private Node(K key, V val) {
            this.k = key;
            this.v = val;
        }
    }

    public BSTMap() {
        this.clear();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }


    @Override
    public V get(K key) {

        return getHelper(key, root);
    }

    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.k);

        if(cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.v;
        }
    }

    /*
    return the size of the tree
     */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = putHelper(key, value, root);

    }

    private Node putHelper(K key, V value, Node p){

        Node newPair = new Node(key, value);

        if (p == null) {
            size++;
            return newPair;
        }

        int cmp = key.compareTo(p.k);

        if(cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.v = value;
        }

        return p;
    }

    public void printInOrder(Node p) {
        if (p == null) {
            return;
        }

        printInOrder(p.left);
        System.out.println("Key" + p.k.toString() + "Value: " + p.v.toString());
        printInOrder(p.right);
    }


    public Set<K> keySet() {
        throw new UnsupportedOperationException("Do not support!");
    }

    public V remove(K key) {
        throw new UnsupportedOperationException("Do not support!");
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Do not support!");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Do not support!");
    }
}
