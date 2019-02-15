public class LinkedListDeque<Item> implements Deque<Item> {

    private class Node {
        private Item x;
        private Node next;
        private Node prev;

        private Node(Node p, Item i, Node n) {
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

    //@source: https://www.youtube.com/watch?v=JNroRiEG7U4
    //

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i++) {
            addLast((Item) other.get(i));
        }

        size = other.size;
        sentinel = other.sentinel;
    }

    @Override
    public void addFirst(Item item) {
        size++;
        if (size == 1) {
            Node newNode = new Node(sentinel, item, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node firstNode = sentinel.next;
            Node temp = new Node(sentinel, item, firstNode);
            firstNode.prev = temp;
            sentinel.next = temp;
        }
    }

    @Override
    public void addLast(Item item) {
        size++;
        if (size == 1) {
            Node newNode = new Node(sentinel, item, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node lastNode = sentinel.prev;
            Node temp = new Node(lastNode, item, sentinel);
            sentinel.prev = temp;
            lastNode.next = temp;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p.x != null) {
            System.out.print(p.x + " ");
            p = p.next;
        }
    }

    @Override
    public Item removeFirst() {
        Node removedNode = sentinel.next;
        if (removedNode.x == null) {
            return null;
        }
        size--;
        Item removed = removedNode.x;
        Node newFirst = removedNode.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        return removed;

    }

    @Override
    public Item removeLast() {
        Node removedNode = sentinel.prev;
        if (removedNode.x == null) {
            return null;
        }
        size--;
        Item removed = removedNode.x;
        Node newLast = removedNode.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        return removed;

    }

    @Override
    public Item get(int index) {
        if (index == 0) {
            return sentinel.next.x;
        }
        if (index >= size) {
            return null;
        }
        Node curr = sentinel.next;
        int i = 0;
        while (i < index && curr.next != null) {
            i++;
            curr = curr.next;
        }

        return curr.x;

    }

    public Item getRecursive(int index) {
        return getRecursiveHelper(index,sentinel);
    }

    private Item getRecursiveHelper(int index, Node node) {
        if (node.next.x == null) {
            return null;
        }
        if (index == 0) {
            return node.next.x;
        }
        return getRecursiveHelper(index - 1,node.next);
    }
}
