package practice.MyLinkedList;

import java.util.AbstractList;

public class MyLinkedList<E> extends AbstractList<E> {

    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    public MyLinkedList () {
        size = 0;
        head = new ListNode<E>(null);
        tail = new ListNode<E>(null);
        head.next = tail;
        tail.prev = head;
    }

    public int size () {
        return size;
    }

    public boolean isEmpty () {
        if (size == 0) return true;
        return false;
    }

    public boolean add (E data) {
        add(size, data);
        return true;
    }

    public void add (int index, E data) throws NullPointerException, IndexOutOfBoundsException {
        if (data == null) throw new NullPointerException("Can't store a null value");
        if (index > size || index < 0) throw new IndexOutOfBoundsException("index is out of range");
        ListNode<E> thisNode = this.head;
        for (int i = 0; i <= index; i++) {
            thisNode = thisNode.next;
            if (i == index) {
                ListNode<E> node = new ListNode<>(data, thisNode.prev, thisNode);
                // take last sentinel node into account
                thisNode.prev.next = node;
                thisNode.prev = node;
                size += 1;
            }
        }
    }

    public E remove (int index) throws IndexOutOfBoundsException {
        if (index >= size) throw new IndexOutOfBoundsException("Index is out of range");
        ListNode<E> thisNode = this.head;
        for (int i = 0; i <= index; i++) {
            thisNode = thisNode.next;
            if (i == index) {
                thisNode.prev.next = thisNode.next;
                thisNode.next.prev = thisNode.prev;
            }
        }
        size -= 1;
        return thisNode.data;
    }

    public E set (int index, E data) throws NullPointerException {
        if (data == null) throw new NullPointerException("Can't set a null value");
        E replacedData = null;
        // counting should start from first sentinel node
        ListNode<E> thisNode = this.head;
        for (int i = 0; i <= index; i++) {
            thisNode = thisNode.next;
            if (i == index) {
                replacedData = thisNode.data;
                thisNode.data = data;
            }
        }
        return replacedData;
    }

    public E get (int index) {
        if (index >= size) throw new IndexOutOfBoundsException("Index is out of range");
        ListNode<E> thisNode = this.head;
        for (int i = 0; i <= index; i++) {
            thisNode = thisNode.next;
            if (i == index)
                return thisNode.data;
        }
        return null;
    }

    public String toString () {
        ListNode<E> thisNode = this.head;
        String list = "";
        for (int i = 0; i < size; i++) {
            thisNode = thisNode.next;
            list += thisNode.data + " ";
        }
        return list;
    }
}
