package bearmaps;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void nullTest() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        assertFalse(a.contains(0));
        assertEquals(0, a.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException(){
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.getSmallest();
        a.changePriority(1, 0.7);
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
}
