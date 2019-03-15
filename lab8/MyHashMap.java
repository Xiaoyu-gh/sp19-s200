import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private class Node {
        /* Each pair of key and value is stored in the Node. */
        private K k;
        private V v;

        private Node(K key, V val) {
            this.k = key;
            this.v = val;
        }

        public boolean equals(Node other) {
            return k.equals(other.k);
        }


    }

    private int size;
    private double lf;
    private ArrayList<Node>[] bucket;
    private ArrayList<K> keys;


    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize,0.75);
    }


    public MyHashMap(int initialSize, double loadFactor) {
        this.size = 0;
        this.lf = loadFactor;
        this.bucket = new ArrayList[initialSize];
        this.keys = new ArrayList<>();
        this.clear();
    }

    @Override
    public void clear() {
        this.size = 0;
        for(int i = 0; i < this.bucket.length; i++){
            this.bucket[i] = new ArrayList<Node>();
        }
    }

    private int hash(K key) {
        return ((key.hashCode() & 0x7fffffff) % bucket.length);
    }

    private int hash(K key, int bucketSize) {
        return Math.floorMod(key.hashCode(), bucketSize);
    }

    @Override
    public boolean containsKey(K key) {
        return (searchKey(key) != null);
    }

    @Override
    //TODO
    public V get(K key) {
        if (searchKey(key) !=null) {
            return searchKey(key).v;
        }
        return null;
    }

    private Node searchKey(K key){
        int index = hash(key);
        ArrayList<Node> l = this.bucket[index];
        if (l != null) {
            for (Node node: l) {
                if (node.k.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if ((double)this.size / this.bucket.length > lf) {
            resize(bucket.length * 2);
        }

        Node newNode = new Node(key, value);
        ArrayList<Node> l = bucket[hash(key)];
        if (containsKey(key)) {
            int index = l.indexOf(searchKey(key));
            Node curr = l.get(index);
            curr.v = value;
        } else {
            l.add(newNode);
            size++;
            this.keys.add(key);
        }
    }

    private void resize(int newSize) {
        ArrayList<Node>[] newBucket = (ArrayList<Node>[]) new ArrayList[newSize];
        for (int i = 0; i < newSize; i++) {
            newBucket[i] = new ArrayList<Node>();
        }

        for (K key : this.keys) {
            int idx = hash(key, newBucket.length);
            ArrayList<Node> box = newBucket[idx];
            box.add(searchKey(key));
        }

        this.bucket = newBucket;
    }


    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Do not support!");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Do not support!");
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }



}
