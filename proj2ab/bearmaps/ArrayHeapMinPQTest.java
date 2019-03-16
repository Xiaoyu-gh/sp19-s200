package bearmaps;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    /**
     * Author: @Shirley Zhou
     */

    @Test
    public void nullTest() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        assertFalse(a.contains(0));
        assertEquals(0, a.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.getSmallest();
        a.changePriority(1, 0.7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(1, 0);
        a.add(1, 1);
    }


    @Test
    public void basicTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
        a.add("third", 3);
        a.add("first", 1);
        a.add("second", 2);
        a.add("fourth", 4);
        assertFalse(a.contains("fifth"));
        assertTrue(a.contains("first"));
    }

    /**
     * source: @lab8 MyHashMapTest
     */
    @Test
    public void pushUpTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
        for (int i = 2; i < 257; i++) {
            a.add("hi" + i, (double) i);
        }
        a.add("hi" + 1, 1.0);
        assertEquals("hi1", a.getSmallest());
    }

    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
        for (int i = 1; i < 257; i++) {
            a.add("hi" + i, (double) i);
        }
        assertEquals("hi1", a.removeSmallest());
        assertEquals("hi2", a.getSmallest());
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
        for (int i = 1; i < 257; i++) {
            a.add("hi" + i, (double) i);
        }
        a.changePriority("hi256", 1);
        assertEquals("hi1", a.getSmallest());
        a.removeSmallest();
        assertEquals("hi256", a.getSmallest());

    }

    @Test
    public void runTimeTest() {
        long startTime = System.nanoTime();
        Random r = new Random();
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
        for (int i = 0; i < 200000; i++) {
            a.add("hi" + i, r.nextDouble());
        }
        for (int i = 0; i < 200000; i++) {
            a.changePriority("hi" + i, r.nextDouble());
        }
//
//        for (int i = 0; i < 200000; i++) {
//            a.contains("hi" + i);
//        }
//        for (int i = 0; i < 200000; i++) {
//            a.removeSmallest();
//        }
        long endTime   = System.nanoTime();
        long totalTime = (endTime - startTime) / (long) Math.pow(10, 9);

        assertTrue(totalTime < 1);
    }


}
