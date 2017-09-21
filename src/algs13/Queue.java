package algs13;
import  stdlib.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
/* ***********************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue < input.txt
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  A generic queue, implemented using a linked list.
 *
 *  % java Queue < tobe.txt
 *  to be or not to be (2 left on queue)
 *
 *************************************************************************/
/**
 *  The <tt>Queue</tt> class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the top item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  All queue operations except iteration are constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Queue<T> implements Iterable<T> {
	private int N;         // number of elements on queue
	private Node<T> first;    // beginning of queue
	private Node<T> last;     // end of queue

	// helper linked list class
	private static class Node<T> {
		public Node() { }
		public T item;
		public Node<T> next;
	}

	/**
	 * Create an empty queue.
	 */
	public Queue() {
		first = null;
		last  = null;
		N = 0;
	}

	/**
	 * Is the queue empty?
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Return the number of items in the queue.
	 */
	public int size() {
		return N;
	}

	/**
	 * Return the item least recently added to the queue.
	 * @throws java.util.NoSuchElementException if queue is empty.
	 */
	public T peek() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		return first.item;
	}

	/**
	 * Add the item to the queue.
	 */
	public void enqueue(T item) {
		Node<T> oldlast = last;
		last = new Node<>();
		last.item = item;
		last.next = null;
		if (isEmpty()) first = last;
		else           oldlast.next = last;
		N++;
	}

	/**
	 * Remove and return the item on the queue least recently added.
	 * @throws java.util.NoSuchElementException if queue is empty.
	 */
	public T dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		T item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) last = null;
		return item;
	}

	/**
	 * Return string representation.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (T item : this)
			s.append(item + " ");
		return s.toString();
	}

	// check internal invariants
	private static <T> boolean check(Queue<T> that) {
		int N = that.N;
		Queue.Node<T> first = that.first;
		Queue.Node<T> last = that.last;
		if (N == 0) {
			if (first != null) return false;
			if (last  != null) return false;
		}
		else if (N == 1) {
			if (first == null || last == null) return false;
			if (first != last)                 return false;
			if (first.next != null)            return false;
		}
		else {
			if (first == last)      return false;
			if (first.next == null) return false;
			if (last.next  != null) return false;

			// check internal consistency of instance variable N
			int numberOfNodes = 0;
			for (Queue.Node<T> x = first; x != null; x = x.next) {
				numberOfNodes++;
			}
			if (numberOfNodes != N) return false;

			// check internal consistency of instance variable last
			Queue.Node<T> lastNode = first;
			while (lastNode.next != null) {
				lastNode = lastNode.next;
			}
			if (last != lastNode) return false;
		}

		return true;
	}


	/**
	 * Return an iterator that iterates over the items on the queue in FIFO order.
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

	public void toGraphviz(String filename) {
		GraphvizBuilder gb = new GraphvizBuilder ();
		toGraphviz (gb, null, first);
		gb.toFile (filename, "rankdir=\"LR\"");
	}
	private void toGraphviz (GraphvizBuilder gb, Node<T> prev, Node<T> n) {
		if (n == null) { gb.addNullEdge (prev); return; }
		gb.addLabeledNode (n, n.item.toString ());
		if (prev != null) gb.addEdge (prev, n);
		toGraphviz (gb, n, n.next);
	}

	/**
	 * A test client.
	 */
	public static void main(String[] args) {
		StdIn.fromString ("to be or not to - be - - that - - - is");
		Queue<String> q = new Queue<>();
		int count = 0;
		q.toGraphviz ("queue" + count + ".png"); count++;
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) q.enqueue(item);
			else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
			q.toGraphviz ("queue" + count + ".png"); count++;
		}
		StdOut.println("(" + q.size() + " left on queue)");
	}
}
