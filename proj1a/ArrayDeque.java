public class ArrayDeque<T> {

    private T[] items;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[8];
        size = 0;

        for (int i = 0; i < other.size(); i ++) {
            addLast((T) other.get(i));
        }
    }

//    public void addFirst(T item) {
//        if (size == items.length) {
//            resize(size );
//        }
//
//    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size ++);
        }
        items[size] = item;
        size ++;
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

    public void printDeque(){
        for (int i = 0; i < size; i ++){
            System.out.print(items[i] + " ");
        }

    }

    public void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;

    }
//    public T removeFirst() {
//        return x;
//    }
//
    public T removeLast() {
        return items[size --];
    }

    public T get(int index){
        if (index >= size) {
            return null;
        }
        return items[index];
    }
}
