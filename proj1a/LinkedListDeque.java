public class LinkedListDeque<T> {
    public class Node {
        public T x;
        public Node next;
        public Node prev;

        public Node(Node p, T i, Node n) {
            prev = p;
            x = i;
            next = n;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new Node(null, null, null);
        sentinel.next = new Node (null, item, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    //@source: https://www.youtube.com/watch?v=JNroRiEG7U4
    //
    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i ++) {
            addLast((T) other.get(i));
        }
    }

    public void addFirst(T item) {
        size ++;
        Node firstNode = sentinel.next;
        Node p = new Node(null, item, firstNode);
        sentinel.next = p;
    }


    public void addLast(T item) {
        size ++;
        Node lastNode = sentinel.prev;
        Node p = new Node(lastNode, item, null);
        sentinel.prev = p;
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
        Node p = sentinel.next;
        while (p.x != null){
            System.out.print(p.x + " ");
            p = p.next;
        }
    }

    public T removeFirst() {
        Node p = sentinel.next;
        if (p.x == null) {
            return null;
        }
        size --;
        T removed = p.x;
        sentinel.next= null;
        return removed;

    }

    public T removeLast() {
        Node p = sentinel.prev;
        if (p.x == null) {
            return null;
        }
        size --;
        T removed = p.x;
        sentinel = p.prev;
        return removed;

    }

    public T get(int index) {
        if (index == 0) {
            return sentinel.next.x;
        }
        if (index >= size) {
            return null;
        }
        Node curr = sentinel.next.next;
        int i = 0;
        while (i < index && curr.next != null) {
            i ++;
            curr = curr.next;
        }

        return curr.x;

    }

    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.x;
        }
        Node curr = sentinel.next.next;
        if (curr.next == null || index == 0) {
            return curr.x;
        }
        else {
           index --;
           return getRecursive(index);
        }
    }
}