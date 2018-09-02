package practice.MyLinkedList;

class ListNode<E> {
    ListNode<E> next;
    ListNode<E> prev;
    E data;

    public ListNode (E data) {
        this.data = data;
    }

    public ListNode (E data, ListNode<E> prev, ListNode<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
