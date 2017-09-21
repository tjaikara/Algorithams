package algs13;

import stdlib.*;

public class XFixedCapacityStackOfStrings {
	private final String[] a; // holds the items
	private int N;            // number of items in stack
    // a[0]..a[N-1] are non null
	// a[N]..a[a.length-1] are null
	public XFixedCapacityStackOfStrings (int capacity) {
		this.a = new String[capacity];
		this.N = 0;
	}
	public int size ()        { return N; }
	public boolean isEmpty () { return (N == 0); }
	public void push (String item) {
		if (item == null) throw new IllegalArgumentException ();
		if (N >= a.length) throw new RuntimeException ("Stack full");
		a[N] = item;
		N++;
	}
	public String pop () {
		if (N <= 0) throw new RuntimeException ("Stack empty");
		N--;
		String result = a[N];
		a[N] = null;
		return result;
	}

	public static void main(String[] args) {
		//Trace.showObjectIdsRedundantly (true);
		Trace.showBuiltInObjects (true);
		Trace.drawStepsOfMethod ("main");
		Trace.run ();
		XFixedCapacityStackOfStrings stack = new XFixedCapacityStackOfStrings (7);
		stack.push ("a");
		stack.push ("b");
		stack.push ("c");
		stack.push ("d");
		while (!stack.isEmpty()) {
			StdOut.println (stack.pop ());
		}
	}
}
