package es.datastructur.synthesizer;
import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;

    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

//    /**
//     * Take an index in the buffer array and return its corresponding actual position
//     */
//    private int actualIndex(int pInd) {
//        if (last < first) {
//            return pInd + capacity() - first;
//        }
//        return pInd - first;
//    }
//    /**
//     * Take an actual position and return its corresponding index in the buffer array
//     */
//    private int bufferIndex(int aInd) {
//        if ((aInd + first) > capacity()) {
//            return aInd + first - capacity();
//        }
//        return aInd + first;
//    }


    private int plusOne(int index) {
        if (index == capacity() - 1) {
            return 0;
        }
        return index + 1;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow!!");
        }
        rb[last] = x;
        fillCount += 1;
        last = plusOne(last);

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow!!");
        }
        T dequeued = rb[first];
        rb[first] = null;
        first = plusOne(first);
        fillCount -= 1;
        return dequeued;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow!!");
        }
        return rb[first];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }
        if (!(o instanceof ArrayRingBuffer)) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (this.fillCount != other.fillCount) {
            return false;
        }

        if (this.capacity() != other.capacity()) {
            return false;
        }

        Iterator<T> otherI = other.iterator();
        Iterator<T> thisI = this.iterator();
        boolean boo = true;
        while (thisI.hasNext() && boo) {
            if (thisI.next() != otherI.next()) {
                boo = false;
            }
        }
        return boo;
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {

        private int pos;
        private int count;

        public ArrayRingBufferIterator() {
            pos = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return (count < fillCount);
        }

        public T next() {
            T returnItem = rb[pos];
            pos = plusOne(pos);
            count++;
            return returnItem;
        }
    }
}
