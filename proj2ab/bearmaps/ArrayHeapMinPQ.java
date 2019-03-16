package bearmaps;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Entry> entries;

    private class Entry {
        private T item;
        private double priority;

        private Entry(T i, double p) {
            this.item = i;
            this.priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        entries = new ArrayList<>();
        entries.add(new Entry(null, Double.MIN_VALUE));

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

        this.entries.add(new Entry(item, priority));
        pushUp(this.entries.size() - 1);
    }


    /* Return true if the given item is present in ArrayHeap,
       else, return false. */
    @Override
    public boolean contains(T item) {
        if (entries.size() == 1) {
            return false;
        }
        for (Entry entry: this.entries) {
            if (entry.item.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /* Return the item with smallest priority in ArrayHeap. */
    @Override
    public T getSmallest() {
        if (this.size() == 0) {
            throw new NoSuchElementException("No item exists");
        }
        return this.entries.get(1).item;
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
        T min = getSmallest();
        Entry newFirst = this.entries.get(this.entries.size() - 1);
        swap(1, this.entries.size() - 1);
        this.entries.remove(this.entries.size() - 1);
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

        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).item.equals(item)) {
                Entry entry = entries.get(i);
                double oldPriority = entry.priority;
                entry.priority = priority;
                pushDown(i);
                pushUp(i);
            }
        }

    }

    /* Swap the two entries given their index. */
    private void swap(int index1, int index2) {
        Entry entry1 = entries.get(index1);
        Entry entry2 = entries.get(index2);

        this.entries.set(index1, entry2);
        this.entries.set(index2, entry1);
    }

    /* Move the entry up one level (swap the entry with its parent) */
    private void pushUp(int index) {

        //if the index is 1 (or negative) or has no entry, code cannot proceed
        if (index > 1 && this.entries.get(index) != null) {
            int pIdx = parentIdx(index);

            if (this.entries.get(index).priority < this.entries.get(pIdx).priority) {
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

            if (this.entries.get(index).priority > this.entries.get(smaller).priority) {
                swap(index, smaller);
                pushDown(smaller);
            }
        }
    }

    /* Return the index of the entry with a smaller priority. */
    private int smaller(int index1, int index2) {
        if (this.entries.get(index1) == null) {
            return index2;
        } else if (this.entries.get(index2) == null) {
            return index1;
        } else if (this.entries.get(index1).priority < this.entries.get(index2).priority) {
            return index1;
        } else {
            return index2;
        }
    }

}

