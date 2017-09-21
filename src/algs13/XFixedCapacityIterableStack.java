package algs13;

import stdlib.*;
import java.util.Iterator;

public class XFixedCapacityIterableStack<T> { //implements Iterable<T> {
	private final T[] a; // holds the items
	private int N; // number of items in stack

	@SuppressWarnings("unchecked")
	public XFixedCapacityIterableStack (int capacity) {
		this.a = (T[]) new Object[10]; // no generic array creation
		this.N = 0;
	}
	public int size ()        { return N; }
	public boolean isEmpty () { return (N == 0); }
	public void push (T item) {
		if (item == null) throw new IllegalArgumentException ();
		a[N] = item;
		N++;
	}
	public T pop () {
		N--;
		T result = a[N];
		a[N] = null;
		return result;
	}
	public Iterator<T> iterator () {
		return new ReverseArrayIterator ();
	}
	public class ReverseArrayIterator implements Iterator<T> {
		private int i = N - 1;
		public boolean hasNext () { return i >= 0; }
		public T next () {
			T result = a[i];
			i--;
			return result;
		}
	}
	public static void main (String[] args) {
		Trace.showBuiltInObjects (true);
		Trace.drawStepsOfMethod ("main");
		Trace.drawStepsOfMethod ("next");
		Trace.run ();
		
		XFixedCapacityIterableStack<Integer> s1 = new XFixedCapacityIterableStack<> (5);
		XFixedCapacityIterableStack<String> s2 = new XFixedCapacityIterableStack<> (3);
		s1.push (11);
		s1.push (21);
		s1.push (31);
		s2.push ("duck");
		s2.push ("goose");

//		StdOut.print ("What's on the stack: ");
//		for (Integer k : s1) {
//			StdOut.print (k + " ");
//		}
//		StdOut.println ();

		// Here is a more explicit version
		StdOut.print ("What's on the stack: ");
		{
			Iterator<Integer> it = s1.iterator ();
			while (it.hasNext ()) {
				Integer k = it.next ();
				StdOut.print (k + " ");
			}
		}
		StdOut.println ();
	}
}
