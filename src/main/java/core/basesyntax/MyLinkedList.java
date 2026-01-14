package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(Node prev, T element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node l = tail;
        Node newNode = new Node(l, value, null);
        tail = newNode;

        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node current = getNodeByIndex(index);
        Node neighborLeft = current.prev;

        Node newNode = new Node(neighborLeft, value, current);
        current.prev = newNode;

        if (neighborLeft == null) {
            head = newNode;
        } else {
            neighborLeft.next = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("It's not a valid index");
        }

        Node current;

        if (index < size / 2) {
            current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private T unlink(Node node) {
        final T element = node.item;
        final Node prev = node.prev;
        final Node next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        node.item = null;
        size--;

        return element;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node x = head; x != null; x = x.next) {
            if (object == x.item || (object != null && object.equals(x.item))) {
                unlink(x);

                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
