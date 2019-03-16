package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<T> entries;
    private HashMap<T, Double> priorityMap;
    private HashMap<T, Integer> indexMap;


    public ArrayHeapMinPQ() {
        entries = new ArrayList<>();
        entries.add(null);
        priorityMap = new HashMap<>();
        indexMap = new HashMap<>();
    }

    /* Return the left child index of the given parent index. */
    private int leftChildIdx(int index) {
        return 2 * index;
    }

    /* Return the right child index of the given parent index. */
    private int rightChildIdx(int index) {
        return 2 * index + 1;
    }

    /* Return the index of the given child index. */
    private int parentIdx(int index) {
        return index / 2;
    }

    /* Return the index of the given child index. */
    @Override
    public void add(T item, double priority) {
        if (this.contains(item)) {
            throw new IllegalArgumentException("Item already exists, try changePriority function");
        }

        entries.add(item);
        priorityMap.put(item, priority);
        pushUp(this.entries.size() - 1);
    }


    /* Return true if the given item is present in ArrayHeap,
       else, return false. */
    @Override
    public boolean contains(T item) {
        if (this.size() == 0) {
            return false;
        }

        return priorityMap.containsKey(item);
    }

    /* Return the item with smallest priority in ArrayHeap. */
    @Override
    public T getSmallest() {
        if (this.size() == 0) {
            throw new NoSuchElementException("No item exists");
        }
        return entries.get(1);
    }

    /* Return the size of the ArrayHeap. */
    @Override
    public int size() {
        return (this.entries.size() - 1);
    }

    /* Return the item with smallest priority in ArrayHeap
       Remove the smallest item from the root note
     */
    @Override
    public T removeSmallest() {
        if (this.size() == 1) {
            priorityMap.remove(getSmallest());
            entries.remove(1);
        }

        T min = getSmallest();
        swap(1, this.size());
        entries.remove(this.size());
        pushDown(1);
        return min;
    }

    /* Change the priority of a given item.
       Change the position of the entry accordingly if need to.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!this.contains(item)) {
            throw new NoSuchElementException("No such item exists");
        }

        priorityMap.replace(item, priority);
        int idx = indexMap.get(item);
        pushUp(idx);
        pushDown(idx);

    }

    /* Swap the two entries given their index. */
    private void swap(int index1, int index2) {
        T item1 = entries.get(index1);
        T item2 = entries.get(index2);

        this.entries.set(index1, item1);
        this.entries.set(index2, item2);

        indexMap.replace(item1, index2);
        indexMap.replace(item2, index1);
    }

    /* Move the entry up one level (swap the entry with its parent) */
    private void pushUp(int index) {

        //if the index is 1 (or negative) or has no entry, code cannot proceed
        if (index > 1 && this.entries.get(index) != null) {
            int pIdx = parentIdx(index);

            if (priorityMap.get(entries.get(index)) < priorityMap.get(entries.get(pIdx))) {
                swap(index, pIdx);
                //built in recursive again
                pushUp(pIdx);
            }
        }

    }

    /* Move the entry down one level (swap the entry with one of its children) */
    private void pushDown(int index) {

        //if the index has no children or has no entry, code cannot proceed
        int lcIdx = leftChildIdx(index);
        int rcIdx = rightChildIdx(index);
        if (this.entries.get(lcIdx) != null && this.entries.get(rcIdx) != null
                && this.entries.get(index) != null) {
            int smaller = smaller(lcIdx, rcIdx);

            if (priorityMap.get(entries.get(index)) > priorityMap.get(entries.get(smaller))) {
                swap(index, smaller);
                pushDown(smaller);
            }
        }
    }

    /* Return the index of the entry with a smaller priority. */
    private int smaller(int index1, int index2) {
        if (index1 > this.size()) {
            return index2;
        } else if (index2 > this.size()) {
            return index1;
        } else if (priorityMap.get(entries.get(index1)) < priorityMap.get(entries.get(index2))) {
            return index1;
        } else {
            return index2;
        }
    }


}

