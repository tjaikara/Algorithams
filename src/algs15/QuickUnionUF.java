// Exercise 1.5.7 (Solution published at http://algs4.cs.princeton.edu/)
package algs15;
import java.util.Arrays;
import stdlib.*;
/* **************************************************************************
 *  Compilation:  javac QuickUnionUF.java
 *  Execution:  java QuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Quick-union algorithm.
 *
 *  % java QuickUnionUF < largeUF.txt
 *  QuickUnionUF # components: 6 [8332.928000] // More than 2 hours
 *
 ****************************************************************************/

public class QuickUnionUF implements UF {
	private int[] id;    // id[i] = parent of i
	private int count;   // number of components

	// instantiate N isolated components 0 through N-1
	public QuickUnionUF(int N) {
		id = new int[N];
		count = N;
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}

	// return number of connected components
	public int count() {
		return count;
	}

	// return root of component corresponding to element p
	public int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	// are elements p and q in the same component?
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	// merge components containing p and q
	public void union(int p, int q) {
		int pid = find(p); // loser
		int qid = find(q); // champion
		if (pid == qid) return;
		id[pid] = qid;
		count--;
	}

	public String toString() { return Arrays.toString (id); }

	public static void main(String[] args) {
		StdIn.fromFile ("data/tinyUF.txt");
		//StdIn.fromFile ("data/mediumUF.txt");
		//StdIn.fromFile ("data/largeUF.txt");

		int N = StdIn.readInt();
		QuickUnionUF uf = new QuickUnionUF(N);

		// read in a sequence of pairs of integers (each in the range 0 to N-1),
		// calling find() for each pair: If the members of the pair are not already
		// call union() and print the pair.
		Stopwatch sw = new Stopwatch ();
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q)) continue;
			uf.union(p, q);
			StdOut.println(p + " " + q);
			GraphvizBuilder.ufToFile (uf.id);
		}
		StdOut.format("QuickUnionUF # components: %d [%f]\n", uf.count(), sw.elapsedTime ());
	}
}
