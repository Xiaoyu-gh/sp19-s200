import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.Quick;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> qs = QuickSort.quickSort(tas);

        assertTrue(isSorted(qs));
        assertTrue(!tas.isEmpty());
    }

    @Test
    public void testAllSameQS() {
        Queue<Integer> q1 = new Queue<>();
        for (int i = 0; i < 10; i++) {
            q1.enqueue(1);
        }
        Queue<Integer> qs = QuickSort.quickSort(q1);

        assertTrue(isSorted(qs));
        assertTrue(!q1.isEmpty());

    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> qs = MergeSort.mergeSort(tas);

        assertTrue(isSorted(qs));
        assertTrue(!tas.isEmpty());
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
