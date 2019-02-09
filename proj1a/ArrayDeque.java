public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextF;
    private int nextL;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextF = 0;
        nextL = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        size = 0;

        for (int i = 0; i < other.items.length; i++) {
            items[i] = (T) other.items[i];
        }
        nextF = other.nextF;
        nextL = other.nextL;
        size = other.size;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size == 0) {
            items[0] = item;
            size++;
            nextF = 7;
            nextL = 1;
        } else {
            items[nextF] = item;
            size++;
            nextF = minusOne(nextF);
        }
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size == 0) {
            items[0] = item;
            size++;
            nextF = 7;
            nextL = 1;
        } else {
            items[nextL] = item;
            nextL++;
            size++;
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[i] + " ");
        }
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = get(i);
        }

        items = a;
        nextF = items.length;
        nextL = size;

    }

    public T removeFirst() {
        if (items == null) {
            return null;
        }
        T removed = items[plusOne(nextF)];
        size--;
        return removed;
    }

    public T removeLast() {
        if (items == null) {
            return null;
        }
        T removed = items[minusOne(nextF)];
        size--;
        return removed;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int realIndex = plusOne(nextF)+index;
        if (realIndex >= size) {
            realIndex -= 8;
        }
        return items[realIndex];
    }

    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }
}
