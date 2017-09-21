package algs13;
import  stdlib.*;

/* ***********************************************************************
 *  Compilation:  javac Bag.java
 *  Execution:    java Bag < input.txt
 *
 *  A generic bag or multiset, implemented using a linked list.
 *
 *  % more tobe.txt
 *  to be or not to - be - - that - - - is
 *
 *  % java Bag < tobe.txt
 *  size of bag = 14
 *  is
 *  -
 *  -
 *  -
 *  that
 *  -
 *  -
 *  be
 *  -
 *  to
 *  not
 *  or
 *  be
 *  to
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>Bag</tt> class represents a bag (or multiset) of
 *  generic items. It supports insertion and iterating over the
 *  items in arbitrary order.
 *  <p>
 *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em>  operation
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Bag<T> implements Iterable<T> {
	private int N;         // number of elements in bag
	private Node<T> first;    // beginning of bag

	// helper linked list class
	private static class Node<T> {
		public Node() { }
		public T item;
		public Node<T> next;
	}

	/**
	 * Create an empty stack.
	 */
	public Bag() {
		first = null;
		N = 0;
	}

	/**
	 * Is the BAG empty?
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Return the number of items in the bag.
	 */
	public int size() {
		return N;
	}

	/**
	 * Add the item to the bag.
	 */
	public void add(T item) {
		Node<T> oldfirst = first;
		first = new Node<>();
		first.item = item;
		first.next = oldfirst;
		N++;
	}

	// check internal invariants
	protected static <T> boolean check(Bag<T> that) {
		int N = that.N;
		Bag.Node<T> first = that.first;
		if (N == 0) {
			if (first != null) return false;
		}
		else if (N == 1) {
			if (first == null)      return false;
			if (first.next != null) return false;
		}
		else {
			if (first.next == null) return false;
		}

		// check internal consistency of instance variable N
		int numberOfNodes = 0;
		for (Bag.Node<T> x = first; x != null; x = x.next) {
			numberOfNodes++;
		}
		if (numberOfNodes != N) return false;

		return true;
	}


	/**
	 * Return an iterator that iterates over the items in the bag.
	 */
	public Iterator<T> iterator()  {
		return new ListIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator implements Iterator<T> {
		private Node<T> current = first;

		public boolean hasNext()  { return current != null;                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			T item = current.item;
			current = current.next;
			return item;
		}
	}

	/**
	 * A test client.
	 */
	public static void main(String[] args) {
		StdIn.fromString ("to be or not to - be - - that - - - is");
		Bag<String> bag = new Bag<>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			bag.add(item);
		}

		StdOut.println("size of bag = " + bag.size());
		for (String s : bag) {
			StdOut.println(s);
		}
	}
}
