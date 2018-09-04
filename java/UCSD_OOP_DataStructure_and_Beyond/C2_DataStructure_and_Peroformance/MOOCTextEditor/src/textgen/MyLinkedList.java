package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element)
	{
		// TODO: Implement this method
		add(size, element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException("Index is out of range");
		LLNode<E> thisNode = this.head;
		for (int i = 0; i <= index; i++) {
			thisNode = thisNode.next;
			if (i == index)
				return thisNode.data;
		}
		return null;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method
		if (element == null) throw new NullPointerException("Can't store a null value");
		if (index > size || index < 0) throw new IndexOutOfBoundsException("index is out of range");
		LLNode<E> thisNode = this.head;
		for (int i = 0; i <= index; i++) {
			thisNode = thisNode.next;
			if (i == index) {
				LLNode<E> node = new LLNode<>(element, thisNode.prev, thisNode);
				// take last sentinel node into account
				thisNode.prev.next = node;
				thisNode.prev = node;
				++size;
			}
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index >= size) throw new IndexOutOfBoundsException("Index is out of range");
		LLNode<E> thisNode = this.head;
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

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) throw new NullPointerException("Can't set a null value");
		E replacedData = null;
		// counting should start from first sentinel node
		LLNode<E> thisNode = this.head;
		for (int i = 0; i <= index; i++) {
			thisNode = thisNode.next;
			if (i == index) {
				replacedData = thisNode.data;
				thisNode.data = element;
			}
		}
		return replacedData;
	}

	public String toString () {
		LLNode<E> thisNode = this.head;
		String list = "";
		for (int i = 0; i < size; i++) {
			thisNode = thisNode.next;
			list += thisNode.data + " ";
		}
		return list;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
		this.data = e;
		this.prev = prev;
		this.next = next;
	}

}
