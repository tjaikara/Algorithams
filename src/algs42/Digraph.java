// Exercise 4.2.3 (Solution published at http://algs4.cs.princeton.edu/)
package algs42;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import stdlib.*;
import algs13.Bag;
import algs13.Stack;

/**
 *  The <tt>Digraph</tt> class represents an directed graph of vertices
 *  named 0 through V-1.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of the neighbors incident to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/52directed">Section 5.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Digraph {
	private final int V;
	private int E;
	private final Bag<Integer>[] adj;

	/**
	 * Create an empty digraph with V vertices.
	 */
	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<>();
		}
	}

	/**
	 * Create a digraph from input stream.
	 */
	@SuppressWarnings("unchecked")
	public Digraph(In in) {
		try {
			this.V = in.readInt();
			if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
			adj = new Bag[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new Bag<>();
			}
			int E = in.readInt();
			if (E < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
			for (int i = 0; i < E; i++) {
				int v = in.readInt();
				int w = in.readInt();
				addEdge(v, w);
			}
		}
		catch (NoSuchElementException e) {
			throw new InputMismatchException("Invalid input format in Digraph constructor");
		}
	}

	/**
	 * Copy constructor.
	 */
	public Digraph(Digraph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<Integer> reverse = new Stack<>();
			for (int w : G.adj[v]) {
				reverse.push(w);
			}
			for (int w : reverse) {
				adj[v].add(w);
			}
		}
	}

	/**
	 * Return the number of vertices in the digraph.
	 */
	public int V() {
		return V;
	}

	/**
	 * Return the number of edges in the digraph.
	 */
	public int E() {
		return E;
	}

	/**
	 * Add the directed edge v->w to the digraph.
	 * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
	 */
	public void addEdge(int v, int w) {
		if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
		if (w < 0 || w >= V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V-1));
		adj[v].add(w);
		E++;
	}

	/**
	 * Return the list of vertices pointed to from vertex v as an Iterable.
	 * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
	 */
	public Iterable<Integer> adj(int v) {
		if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
		return adj[v];
	}

	/**
	 * Return the reverse of the digraph.
	 */
	public Digraph reverse() {
		Digraph R = new Digraph(V);
		for (int v = 0; v < V; v++) {
			for (int w : adj(v)) {
				R.addEdge(w, v);
			}
		}
		return R;
	}

	/**
	 * Return a string representation of the digraph.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(String.format("%d: ", v));
			for (int w : adj[v]) {
				s.append(String.format("%d ", w));
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	/**
	 * Save a graphviz representation of the graph.
	 * See <a href="http://www.graphviz.org/">graphviz.org</a>.
	 */
	public void toGraphviz(String filename) {
		GraphvizBuilder gb = new GraphvizBuilder ();
		for (int v = 0; v < V; v++) {
			gb.addNode (v);
			for (int w : adj[v])
				gb.addEdge (v, w);
		}
		gb.toFile (filename);
	}

	/**
	 * Test client.
	 */
	public static void main(String[] args) {
		//args = new String[] { "data/mediumDG.txt" };
		//args = new String[] { "data/tinyDG.txt" };
		//args = new String[] { "data/tinyDGeuler1.txt" };
		//args = new String[] { "data/tinyDGeuler2.txt" };
		//args = new String[] { "data/tinyDGeuler3.txt" };
		//args = new String[] { "data/tinyDGeuler4.txt" };
		//args = new String[] { "data/tinyDGeuler5.txt" };
		//args = new String[] { "data/tinyDGeuler6.txt" };
		//args = new String[] { "data/tinyDGex2.txt" };
		args = new String [] { "10", "10" };

		Digraph G;
		if (args.length == 1) {
			In in = new In(args[0]);
			G = new Digraph(in);
		} else {
			int V = Integer.parseInt (args[0]);
			int E = Integer.parseInt (args[1]);
			G = DigraphGenerator.simple(V, E);
		}
		StdOut.println(G);
		G.toGraphviz ("g.png");
	}

}
